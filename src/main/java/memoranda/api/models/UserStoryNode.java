package memoranda.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStoryNode {
    private int user_story_id;
    private int ref_number; // US#
    private String user_story_subject;
    private int total_user_story_points;
    private String created_date;
    private String modified_date;
    private String finish_date;
    private boolean is_closed;
    private int owner_id;
    private String owner_user_name;
    private String status;
    private int project_id;
    private String project_name;
    private String milestone_name;
    private String milestone_slug;
    private int milestone_id;
    private List<TaskNode> user_story_task_list;

    public UserStoryNode(int ref_number, int user_story_id, String user_story_subject,
                         int total_user_story_points, String created_date,
                         String modified_date, String finish_date,
                         boolean is_closed, int owner_id,
                         String owner_user_name, String status,
                         int project_id, String project_name,
                         String milestone_name, int milestone_id, String milestone_slug) {
        this.user_story_id = user_story_id;
        this.ref_number = ref_number;
        this.user_story_subject = user_story_subject;
        this.total_user_story_points = total_user_story_points;
        this.created_date = created_date;
        this.modified_date = modified_date;
        this.finish_date = finish_date;
        this.is_closed = is_closed;
        this.owner_id = owner_id;
        this.owner_user_name = owner_user_name;
        this.status = status;
        this.project_id = project_id;
        this.project_name = project_name;
        this.milestone_name = milestone_name;
        this.milestone_slug = milestone_slug;
        this.milestone_id = milestone_id;
        this.user_story_task_list = new ArrayList<>();
    }

    public int getUserStoryId() {
        return user_story_id;
    }

    public void setUserStoryId(int user_story_id) {
        this.user_story_id = user_story_id;
    }

    public int getRefNumber() {
        return ref_number;
    }

    public void setRefNumber(int ref_number) {
        this.ref_number = ref_number;
    }

    public String getUserStorySubject() {
        return user_story_subject;
    }

    public void setUserStorySubject(String user_story_subject) {
        this.user_story_subject = user_story_subject;
    }

    public int getTotalPoints() {
        return total_user_story_points;
    }

    public void setTotalPoints(int total_user_story_points) {
        this.total_user_story_points = total_user_story_points;
    }

    public String getCreatedDate() {
        return (created_date == null) ? "null" : created_date;
    }

    public void setCreatedDate(String created_date) {
        this.created_date = created_date;
    }

    public String getModifiedDate() {
        return (modified_date == null) ? "null" : modified_date;
    }

    public void setModifiedDate(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getFinishDate() {
        return (finish_date == null) ? "null" : finish_date;
    }

    public void setFinishDate(String finish_date) {
        this.finish_date = finish_date;
    }

    public boolean isClosed() {
        return is_closed;
    }

    public void setClosed(boolean is_closed) {
        this.is_closed = is_closed;
    }

    public int getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwnerUserName() {
        return owner_user_name;
    }

    public void setOwnerUserName(String owner_user_name) {
        this.owner_user_name = owner_user_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProjectId() {
        return project_id;
    }

    public void setProjectId(int project_id) {
        this.project_id = project_id;
    }

    public String getProjectName() {
        return project_name;
    }

    public void setProjectName(String project_name) {
        this.project_name = project_name;
    }

    public String getMilestoneName() {
        return milestone_name;
    }

    public void setMilestoneName(String milestone_name) {
        this.milestone_name = milestone_name;
    }

    public String getMilestoneSlug() {
        return milestone_slug;
    }

    public void setMilestoneSlug(String milestone_slug) {
        this.milestone_slug = milestone_slug;
    }
    public int getMilestoneId() {
        return milestone_id;
    }
    public void setMilestoneId(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public List<TaskNode> getUserStoryTaskList() {
        return user_story_task_list;
    }

    public void setUserStoryTaskList(List<TaskNode> user_story_task_list) {
        this.user_story_task_list = user_story_task_list;
    }

    public void addTask(TaskNode task) {
        this.user_story_task_list.add(task);
    }
}
