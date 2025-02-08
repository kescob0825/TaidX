package memoranda.api.models;

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

}
