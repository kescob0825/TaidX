package memoranda.api.models;

import java.util.ArrayList;
import java.util.List;

public class MilestoneData {
    private int milestone_id;
    private String sprint_name;
    private int project_id;
    private int num_user_stories;
    private int num_user_stories_complete; //Set after using a for each loop
    private int num_user_stories_not_complete; //Set after using a for each loop
    private int total_points_complete; //calc from stats
    private int total_points_not_complete; //calc from stats
    private int total_sprint_points; //calc from stats
    private String sprint_est_start;
    private String sprint_est_finish;
    private boolean milestone_closed;
    private List<UserStoryNode> userStories;

    public MilestoneData(int milestone_id, String sprint_name, int project_id, int num_user_stories,
                         String sprint_est_start, String sprint_est_finish, int total_points,
                         int total_points_not_complete, int closed_points, boolean milestone_closed,
                         List<UserStoryNode> userStories) {
        this.milestone_id = milestone_id;
        this.sprint_name = sprint_name;
        this.project_id = project_id;
        this.num_user_stories = num_user_stories;
        this.num_user_stories_complete = 0;
        this.num_user_stories_not_complete = 0;
        this.total_points_complete = closed_points;
        this.total_points_not_complete = total_points_not_complete;
        this.total_sprint_points = total_points;
        this.sprint_est_start = sprint_est_start;
        this.sprint_est_finish = sprint_est_finish;
        this.milestone_closed = milestone_closed;
        this.userStories = new ArrayList<>();
    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public String getSprintName() {
        return sprint_name;
    }

    public void setSprintName(String sprint_name) {
        this.sprint_name = sprint_name;
    }

    public int getNumUserStories() {
        return num_user_stories;
    }

    public void setNumUserStories(int num_user_stories) {
        this.num_user_stories = num_user_stories;
    }

    public int getNumUserStoriesComplete() {
        return num_user_stories_complete;
    }

    public void setNumUserStoriesComplete(int num_user_stories_complete) {
        this.num_user_stories_complete = num_user_stories_complete;
    }

    public int getNumUserStoriesNotComplete() {
        return num_user_stories_not_complete;
    }

    public void setNumUserStoriesNotComplete(int num_user_stories_not_complete) {
        this.num_user_stories_not_complete = num_user_stories_not_complete;
    }

    public int getTotal_points_complete() {
        return total_points_complete;
    }

    public void setTotal_points_complete(int total_points_complete) {
        this.total_points_complete = total_points_complete;
    }

    public int getTotalPointsNotCompleted() {
        return total_points_not_complete;
    }

    public void setTotalPointsNotCompleted(int total_points_not_complete) {
        this.total_points_not_complete = total_points_not_complete;
    }

    public int getTotal_sprint_points() {
        return total_sprint_points;
    }

    public void setTotal_sprint_points(int total_sprint_points) {
        this.total_sprint_points = total_sprint_points;
    }

    public String getSprintEstStart() {
        return sprint_est_start;
    }

    public void setSprintEstStart(String sprint_est_start) {
        this.sprint_est_start = sprint_est_start;
    }

    public String getSprintEstFinish() {
        return sprint_est_finish;
    }

    public void setSprintEstFinish(String sprint_est_finish) {
        this.sprint_est_finish = sprint_est_finish;
    }
    public boolean isMilestoneClosed() {
        return milestone_closed;
    }
    public void setMilestoneClosed(boolean milestone_closed) {
        this.milestone_closed = milestone_closed;
    }
    public List<UserStoryNode> getUserStories() {
        return userStories;
    }
    public void setUserStories(List<UserStoryNode> userStories) {
        this.userStories = userStories;
    }
    public int getProjectId() {
        return this.project_id;
    }
    public void setProjectId(int project_id) {
        this.project_id = project_id;
    }

}
