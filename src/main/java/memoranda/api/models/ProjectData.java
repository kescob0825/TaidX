package memoranda.api.models;

public class ProjectData {
    private String name;
    private String description;
    private String dateCreated;
    private String projectOwner;
    private int[] members;
    private int totalActivity;
    private int projectID;
    private String slug;

    public ProjectData(String name, String description, String dateCreated, String projectOwner, int[] members, int totalActivity, int projectID, String slug) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.projectOwner = projectOwner;
        this.members = members;
        this.totalActivity = totalActivity;
        this.projectID = projectID;
        this.slug = slug;
    }


}
