package memoranda.api.models;

public class TaskNode {
    private int task_id;
    private String task_subject;
    private int task_ref;
    private int project_id;
    private String project_name;
    private String milestone_name;
    private String milestone_slug;
    private int user_story_id;
    private String user_story_subject;
    private int user_story_ref;
    private String owner_name;
    private int task_owner_id;
    private String task_status;

    public TaskNode(int task_ref, String task_subject, int task_id, int project_id, String project_name,
                    String milestone_name, String milestone_slug, int user_story_id,
                    String user_story_subject, int user_story_ref, String owner_name,
                    int task_owner_id, String task_status) {
        this.task_ref = task_ref;
        this.task_id = task_id;
        this.task_subject = task_subject;
        this.project_id = project_id;
        this.project_name = project_name;
        this.milestone_name = milestone_name;
        this.milestone_slug = milestone_slug;
        this.user_story_id = user_story_id;
        this.user_story_subject = user_story_subject;
        this.user_story_ref = user_story_ref;
        this.owner_name = owner_name;
        this.task_owner_id = task_owner_id;
        this.task_status = task_status;
    }

    public int getTaskId() {
        return task_id;
    }

    public String getTaskSubject() {
        return task_subject;
    }

    public int getTaskRef() {
        return task_ref;
    }

    public int getProjectId() {
        return project_id;
    }

    public String getProjectName() {
        return project_name;
    }

    public String getMilestoneName() {
        return milestone_name;
    }

    public String getMilestoneSlug() {
        return milestone_slug;
    }

    public int getUserStoryId() {
        return user_story_id;
    }

    public String getUserStorySubject() {
        return user_story_subject;
    }

    public int getUserStoryRef() {
        return user_story_ref;
    }

    public String getOwnerName() {
        return owner_name;
    }

    public int getTaskOwnerId() {
        return task_owner_id;
    }

    public String getTaskStatus() {
        return task_status;
    }

    public void setTaskId(int task_id) {
        this.task_id = task_id;
    }

    public void setTaskSubject(String task_subject) {
        this.task_subject = task_subject;
    }

    public void setTaskRef(int task_ref) {
        this.task_ref = task_ref;
    }

    public void setProjectId(int project_id) {
        this.project_id = project_id;
    }

    public void setProjectName(String project_name) {
        this.project_name = project_name;
    }

    public void setMilestoneName(String milestone_name) {
        this.milestone_name = milestone_name;
    }

    public void setMilestoneSlug(String milestone_slug) {
        this.milestone_slug = milestone_slug;
    }

    public void setUserStoryId(int user_story_id) {
        this.user_story_id = user_story_id;
    }

    public void setUserStorySubject(String user_story_subject) {
        this.user_story_subject = user_story_subject;
    }

    public void setUserStoryRef(int user_story_ref) {
        this.user_story_ref = user_story_ref;
    }

    public void setOwnerName(String owner_name) {
        this.owner_name = owner_name;
    }

    public void setTaskOwnerId(int task_owner_id) {
        this.task_owner_id = task_owner_id;
    }

    public void setTaskStatus(String task_status) {
        this.task_status = task_status;
    }
}
