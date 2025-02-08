package memoranda.api;

import com.google.inject.Inject;
import memoranda.Start;
import memoranda.api.models.*;
import memoranda.api.modules.*;
import memoranda.util.TaigaJsonData;
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaigaClient {
    private static final String BASE_URL = "https://api.taiga.io/api/v1/";
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final TaigaAuthenticate authenticator;
    private final TaigaProject projects;
    private final TaigaMilestone taigaSprintModule;
    private final TaigaUserStory userStory;
    private final TaigaTasks tasks;
    private final TaigaJsonData jsonData;
    protected int lastResponseCode;
    public static Map<Integer, List<UserStoryNode>> allUserStories = new HashMap<>();
    public static Map<Integer, List<TaskNode>> allTasks = new HashMap<>();
    public static Map<Integer, List<MilestoneData>> milestoneNodes = new HashMap<>();

    @Inject
    public TaigaClient() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();

        //initiate modules
        this.authenticator = new TaigaAuthenticate(httpClient, objectMapper);
        this.projects = new TaigaProject(httpClient, objectMapper);
        this.userStory = new TaigaUserStory(httpClient, objectMapper);
        this.tasks = new TaigaTasks(httpClient, objectMapper);
        this.taigaSprintModule = new TaigaMilestone(httpClient, objectMapper);
        try{
            jsonData = Start.getInjector().getInstance(TaigaJsonData.class);
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
    }

    public void initLoadProjectAndUserStoryData() throws IOException {
        projects.getProjects(this.authenticator.getAuthToken(), this.authenticator.getUserProfile().getUid());
        this.authenticator.getUserProfile().addProjects(projects.getProjectsData());

        for (ProjectData project : this.authenticator.getUserProfile().getProjectsList()) {
            userStory.getUserStories(this.authenticator.getAuthToken(), project.getProjectId());
            List<UserStoryNode> userStoryNodes = userStory.getUserStoryNodes();
            if (userStoryNodes == null || userStoryNodes.isEmpty()) {
                continue;
            }
            project.addProjectUserStoryList(userStoryNodes);
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
                    if (story.isClosed()) {
                        sprint.setNumUserStoriesComplete(sprint.getNumUserStoriesComplete() + 1);
                    }else{
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
        }
        jsonData.saveAllConfigs();
    }

    public void loadDataOnOpen() throws IOException {
        UserProfile userData = jsonData.getUserData(UserProfile.class);
        this.authenticator.setUserProfile(userData);

        Map<Integer, List<ProjectData>> projectsData = new HashMap<>();
        Map<Integer, List<UserStoryNode>> userStoryData = new HashMap<>();
        Map<Integer, List<TaskNode>> tasksData = new HashMap<>();
        Map<Integer, List<MilestoneData>> milestoneData = new HashMap<>();

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
                        if (story.isClosed()) {
                            sprint.setNumUserStoriesComplete(sprint.getNumUserStoriesComplete() + 1);
                        } else {
                            sprint.setNumUserStoriesNotComplete(sprint.getNumUserStoriesNotComplete() + 1);
                        }
                    }
                }
            }
            milestoneData.put(project.getProjectId(), new ArrayList<>(sprintDataList));
        }

    }

    public boolean isClientLoggedIn() throws IOException {
        return authenticator.getAuthAndRefreshToken().isLoggedIn();
    }
    /**
     * Retrieves the projects for the authenticated user.
     */
    public List<ProjectData> getProjectsList() throws IOException {
        return projects.getProjectsData();
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

    public UserProfile getUserProfile() throws IOException {
        return authenticator.getUserProfile();
    }

}
