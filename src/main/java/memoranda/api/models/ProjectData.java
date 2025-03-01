package memoranda.api.models;

import memoranda.api.models.issueattributes.IssuePriority;
import memoranda.api.models.issueattributes.IssueSeverity;
import memoranda.api.models.issueattributes.IssueStatus;
import memoranda.api.models.issueattributes.IssueType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectData {
    private String project_name;
    private String project_description;
    private String date_created;
    private String project_owner;
    private int[] member_ids;
    private int total_activity;
    private int project_id;
    private String project_slug;
    private List<UserStoryNode> project_user_story_list;
    private List<TaskNode> project_task_list;
    private List<MilestoneData> project_sprint_list;
    private boolean is_private;
    private List<ProjectRolesData> project_roles_list;
    private List<IssuesData> project_issues_list;
    private List<IssuePriority> issue_priority_list;
    private List<IssueSeverity> issue_severity_list;
    private List<IssueStatus> issue_status_list;
    private List<IssueType> issue_type_list;

    public ProjectData(String project_name, String project_description, String date_created,
                       String project_owner, int[] member_ids, int total_activity,
                       int project_id, boolean is_private, String project_slug) {
        this.project_name = project_name;
        this.project_description = project_description;
        this.date_created = date_created;
        this.project_owner = project_owner;
        this.member_ids = member_ids;
        this.total_activity = total_activity;
        this.project_id = project_id;
        this.project_slug = project_slug;
        this.is_private = is_private;
        this.project_user_story_list = new ArrayList<>();
        this.project_task_list = new ArrayList<>();
        this.project_sprint_list = new ArrayList<>();
        this.project_roles_list = new ArrayList<>();
        this.project_issues_list = new ArrayList<>();
        this.issue_priority_list = new ArrayList<>();
        this.issue_severity_list = new ArrayList<>();
        this.issue_status_list = new ArrayList<>();
        this.issue_type_list = new ArrayList<>();
    }

    public String getProjectName() {
        return project_name;
    }

    public String getProjectDescription() {
        return project_description;
    }

    public String getDateCreated() {
        return date_created;
    }

    public String getProjectOwner() {
        return project_owner;
    }

    public int[] getMemberIds() {
        return member_ids;
    }

    public int getTotalActivity() {
        return total_activity;
    }

    public String getProjectSlug() {
        return project_slug;
    }

    public int getProjectId() {
        return project_id;
    }

    public List<UserStoryNode> getProjectUserStoryList() {
        return project_user_story_list;
    }

    public void addProjectUserStory(UserStoryNode user_story_node) {
        this.project_user_story_list.add(user_story_node);
    }
    public void addProjectIssues(List<IssuesData> issuesDataList) {
        this.project_issues_list.addAll(issuesDataList);
    }
    public List<IssuesData> getProjectIssuesList() {
        return project_issues_list;
    }

    public void addIssuesPriority(List<IssuePriority> issuesPriorityList) {
        this.issue_priority_list.addAll(issuesPriorityList);
    }
    public List<IssuePriority> getIssuePriorityList() {
        return issue_priority_list;
    }

    public void addIssueSeverity(List<IssueSeverity> issuesSeverityList) {
        this.issue_severity_list.addAll(issuesSeverityList);
    }
    public List<IssueSeverity> getIssueSeverityList() {
        return this.issue_severity_list;
    }

    public void addIssueStatus(List<IssueStatus> issuesStatusList) {
        this.issue_status_list.addAll(issuesStatusList);
    }
    public List<IssueStatus> getIssueStatusList() {
        return issue_status_list;
    }

    public void addIssueType(List<IssueType> issuesTypeList) {
        this.issue_type_list.addAll(issuesTypeList);
    }
    public List<IssueType> getIssueTypeList() {
        return this.issue_type_list;
    }





    public void addProjectUserStoryList(List<UserStoryNode> user_story_node_list) {
        this.project_user_story_list.addAll(user_story_node_list);
    }
    public void addProjectTasks(List<TaskNode> task_node_list) {
        this.project_task_list.addAll(task_node_list);
    }
    public List<TaskNode> getProjectTaskList() {
        return project_task_list;
    }
    public void addProjectSprints(List<MilestoneData> milestoneDataList) {
        this.project_sprint_list.addAll(milestoneDataList);
    }
    public List<MilestoneData> getProjectSprints() {
        return project_sprint_list;
    }
    public boolean isPrivate() {
        return is_private;
    }
    public List<ProjectRolesData> getProjectRolesList() {
        return project_roles_list;
    }
    public void addProjectRolesList(List<ProjectRolesData> projectRolesDataList) {
        this.project_roles_list.addAll(projectRolesDataList);
    }
}
