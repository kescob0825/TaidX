package memoranda.api;

import com.google.inject.Inject;
import memoranda.Start;
import memoranda.api.models.*;
import memoranda.api.modules.*;
import memoranda.util.TaigaJsonData;
import memoranda.util.subscriber.Publisher;
import memoranda.util.subscriber.Subscriber;
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaigaClient implements Publisher {
    private static final String BASE_URL = "https://api.taiga.io/api/v1/";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    protected final TaigaAuthenticate authenticator;
    protected final TaigaUserStats stats;
    protected final TaigaProject projects;
    protected final TaigaMilestone taigaSprintModule;
    protected final TaigaUserStory userStory;
    protected final TaigaTasks tasks;
    protected final TaigaCreateProject createProject;
    protected final TaigaInvite taigaInvite;
    private final TaigaJsonData jsonData;
    private final TaigaIssues issues;
    protected int lastResponseCode;

    //Use for searching data
    public static Map<Integer, List<UserStoryNode>> allUserStories = new HashMap<>();
    public static Map<Integer, List<TaskNode>> allTasks = new HashMap<>();
    public static Map<Integer, List<MilestoneData>> milestoneNodes = new HashMap<>();

    public static Map<Integer, List<ProjectData>> projectsData = new HashMap<>();
    public static Map<Integer, List<UserStoryNode>> userStoryData = new HashMap<>();
    public static Map<Integer, List<TaskNode>> tasksData = new HashMap<>();
    public static Map<Integer, List<MilestoneData>> milestoneData = new HashMap<>();
    private List<Subscriber> subscribers;
    SwingWorker <Void, Void> worker;
    SwingWorker <Void, Void> loginWorker;

    @Inject
    public TaigaClient() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();

        //initiate modules
        this.authenticator = new TaigaAuthenticate(httpClient, objectMapper);
        this.stats = new TaigaUserStats(httpClient, objectMapper);
        this.projects = new TaigaProject(httpClient, objectMapper);
        this.userStory = new TaigaUserStory(httpClient, objectMapper);
        this.tasks = new TaigaTasks(httpClient, objectMapper);
        this.taigaSprintModule = new TaigaMilestone(httpClient, objectMapper);
        this.createProject = new TaigaCreateProject(httpClient, objectMapper);
        this.taigaInvite = new TaigaInvite(httpClient);
        this.issues = new TaigaIssues(httpClient);

        this.subscribers = new ArrayList<>();
        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(30000); //wait 30 seconds for the changes to post on Taiga then pull them and update
                initLoadProjectAndUserStoryData();
                loadDataOnOpen();
                notifySubscribers();
                return null;
            }
        };
        loginWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Thread.sleep(1000); //wait 1 seconds for the changes to post on Taiga then pull them and update
                notifySubscribers();
                return null;
            }
        };
        try{
            this.jsonData = Start.getInjector().getInstance(TaigaJsonData.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Authenticates the client with the provided username and password.
     * @param username the username
     * @param password the password
     * @throws IOException if an I/O error occurs during the authentication process
     */
    public void authenticateClient(String username, String password) throws IOException {
        authenticator.authenticate(username, password);
        jsonData.setUserData(authenticator.getUserProfile());
        setLastResponseCode(authenticator.getLastResponseCode());
        initLoadProjectAndUserStoryData();
        initUserStatsData();
        loadDataOnOpen();
        loginWorker.execute();
    }

    public void initLoadProjectAndUserStoryData() throws IOException {
        //Ensure it is empty or the same tasks can be loaded in.
        this.authenticator.getUserProfile().clearProjects();
        this.projects.getProjects(this.authenticator.getAuthToken(), this.authenticator.getUserProfile().getUid());
        this.authenticator.getUserProfile().addProjects(projects.getProjectsData());
        for (ProjectData project : this.authenticator.getUserProfile().getProjectsList()) {
            this.userStory.getUserStories(this.authenticator.getAuthToken(), project.getProjectId());
            this.projects.getProjectsRoles(this.authenticator.getAuthToken(), project.getProjectId());
            this.issues.getProjectIssues(this.authenticator.getAuthToken(), project.getProjectId());
            List<UserStoryNode> userStoryNodes = userStory.getUserStoryNodes();
            if (userStoryNodes == null || userStoryNodes.isEmpty()) {
                continue;
            }
            project.addProjectUserStoryList(userStoryNodes);
            List<IssuesData> projectIssues = issues.getProjectIssueList();
            project.addProjectIssues(projectIssues);
            allUserStories.put(userStory.getUserStoryNodes().get(0).getProjectId(), new ArrayList<>(userStoryNodes));

            tasks.getAllTasks(this.authenticator.getAuthToken(), project.getProjectId());
            List<TaskNode> taskNodes = tasks.getTaskNodes();
            if (taskNodes == null || taskNodes.isEmpty()) {
                continue;
            }
            project.addProjectTasks(taskNodes);
            allTasks.put(tasks.getTaskNodes().get(0).getProjectId(), new ArrayList<>(taskNodes));

            this.taigaSprintModule.getMilestones(this.authenticator.getAuthToken(), project.getProjectId());
            List<MilestoneData> sprintDataList = taigaSprintModule.getSprintDataList();
            if (sprintDataList == null || sprintDataList.isEmpty()) {
                continue;
            }
            project.addProjectSprints(sprintDataList);

            for (MilestoneData sprint : sprintDataList) {
                for (UserStoryNode story : project.getProjectUserStoryList()) {
                    if (story.getMilestoneId() == sprint.getMilestone_id()) {
                        sprint.getUserStories().add(story);
                    }
                    if (story.isClosed() && story.getMilestoneId() == sprint.getMilestone_id()) {
                        sprint.setNumUserStoriesComplete(sprint.getNumUserStoriesComplete() + 1);
                    }
                    else if (!story.isClosed() && story.getMilestoneId() == sprint.getMilestone_id()){
                        sprint.setNumUserStoriesNotComplete(sprint.getNumUserStoriesNotComplete() + 1);
                    }
                    for (TaskNode task : tasks.getTaskNodes()) {
                        if (task.getUserStoryId() == story.getUserStoryId()) {
                            story.addTask(task);
                        }
                    }
                }
            }
            milestoneNodes.put(project.getProjectId(), new ArrayList<>(sprintDataList));
            sprintDataList.clear();
            userStory.clearUserStoryNodes();
            tasks.clearTaskNodes();
            issues.clearProjectIssueList();
        }
        jsonData.saveAllConfigs();
    }

    public void loadDataOnOpen() throws IOException {
        UserProfile userData = jsonData.getUserData(UserProfile.class);
        this.authenticator.setUserProfile(userData);
        for (ProjectData project : userData.getProjectsList()) {
            projectsData.put(project.getProjectId(), new ArrayList<>(List.of(project)));
            List<UserStoryNode> userStoryNodes = project.getProjectUserStoryList();
            userStoryData.put(project.getProjectId(), new ArrayList<>(userStoryNodes));
            List<TaskNode> taskNodes = project.getProjectTaskList();
            tasksData.put(project.getProjectId(), new ArrayList<>(taskNodes));
            List<MilestoneData> sprintDataList = project.getProjectSprints();
            for (MilestoneData sprint : sprintDataList) {
                for (UserStoryNode story : userStoryNodes) {
                    if (story.getMilestoneId() == sprint.getMilestone_id()) {
                        sprint.getUserStories().add(story);
                        if (story.isClosed() && story.getMilestoneId() == sprint.getMilestone_id()) {
                            sprint.setNumUserStoriesComplete(sprint.getNumUserStoriesComplete() + 1);
                        }
                        else if (!story.isClosed() && story.getMilestoneId() == sprint.getMilestone_id()){
                            sprint.setNumUserStoriesNotComplete(sprint.getNumUserStoriesNotComplete() + 1);
                        }
                    }
                }
            }
            milestoneData.put(project.getProjectId(), new ArrayList<>(sprintDataList));
        }
    }

    public void setProjectRolesClient(JSONObject setRolesData) throws IOException, InterruptedException {
        if (!this.isClientLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Login to create a project.");
            return;
        }
        this.projects.setProjectRoles(this.authenticator.getAuthToken(), setRolesData);
        if (projects.getLastResponseCode() <= 201){
            worker.execute();
        }
    }

    public void sendBulkInvite(JSONObject bulkInviteData) throws IOException {
        if (!this.isClientLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Login to create a project.");
            return;
        }
        this.taigaInvite.bulkInviteMembers(this.authenticator.getAuthToken(), bulkInviteData);
    }

    public void sendInvite(JSONObject inviteData) throws IOException {
        if (!this.isClientLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Login to create a project.");
            return;
        }
        this.taigaInvite.inviteMember(this.authenticator.getAuthToken(), inviteData);
    }

    public void createNewProject(JSONObject newProjectDataBody) throws IOException, InterruptedException {
        if (!this.isClientLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Login to create a project.");
            return;
        }
        createProject.createProject(this.authenticator.getAuthToken(), newProjectDataBody);
        if (createProject.getLastResponseCode() <= 201){
            //Background thread
            worker.execute();
        }
    }
    public boolean isClientLoggedIn() throws IOException {
        return AuthAndRefreshToken.isLoggedIn;
    }
    /**
     * Retrieves the projects for the authenticated user.
     */
    public List<ProjectData> getProjectsList() {
        if (this.authenticator.getUserProfile() == null) {
            return new ArrayList<>();
        }
        return this.authenticator.getUserProfile().getProjectsList();
    }

    public void initUserStatsData() throws IOException {
        stats.getUserStats(this.authenticator.getAuthToken(), this.authenticator.getUserProfile().getUid());
    }

    /**
     * Retrieves the user stats for the authenticated user.
     */
    public UserStatsData getUserStatsData() {
        return stats.getUserStatsData();
    }

    /**
     * Retrieves the user stories for the authenticated user.
     * @throws IOException if an I/O error occurs during the user story retrieval process
     */
    public List<UserStoryNode> getUserStories() throws IOException {
        return userStory.getUserStoryNodes();
    }
    /**
     * Refreshes the authentication token and refresh token.
     * @throws IOException if an I/O error occurs during the refresh process
     */
    public void refreshAuthClient() throws IOException {
        authenticator.refreshAuth();
        setLastResponseCode(authenticator.getLastResponseCode());
    }

    /**
     * Returns the authentication token.
     * @return the authentication token
     * @throws IOException if an I/O error occurs during the token retrieval process
     */
    public String getAuthToken() throws IOException {
        return authenticator.getAuthAndRefreshToken().getAuthToken();
    }

    public void logoutTaiga()  {
        try{
            authenticator.getAuthAndRefreshToken().logout();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Returns the refresh token.
     * @return the refresh token
     * @throws IOException if an I/O error occurs during the token retrieval process
     */
    public String getRefreshToken() throws IOException {
        return authenticator.getAuthAndRefreshToken().getRefreshToken();
    }

    /**
     * Returns the last response code.
     */
    public void setLastResponseCode(int lastResponseCode) {
        this.lastResponseCode = lastResponseCode;
    }

    /**
     * Returns the last response code.
     * @return the last response code
     */
    public int getLastResponseCode() {
        return lastResponseCode;
    }

    public UserProfile getUserProfile() {
        return authenticator.getUserProfile();
    }

    @Override
    public void register(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unregister(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    // TODO pushes update after successful API call
    @Override
    public void notifySubscribers() throws IOException {
        for (Subscriber subscriber : subscribers) {
            subscriber.update();
        }
    }
}
