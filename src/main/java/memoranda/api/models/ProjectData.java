package memoranda.api.models;

import java.util.Arrays;

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
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getDateCreated() {
        return dateCreated;
    }
    public String getProjectOwner() {
        return projectOwner;
    }
    public int[] getMembers() {
        return members;
    }
    public int getTotalActivity() {
        return totalActivity;
    }
    public String getSlug() {
        return slug;
    }
    public int getProjectID() {
        return projectID;
    }
    @Override
    public String toString() {
        return "ProjectData{\n" +
                " \tname='" + getName() + "'" +
                ",\n \tdescription='" + getDescription() + "'" +
                ",\n \tdateCreated='" + getDateCreated() + "'" +
                ",\n \tprojectOwner='" + getProjectOwner() + "'" +
                ",\n \tmembers=" + Arrays.toString(getMembers()) +
                ",\n \ttotalActivity=" + getTotalActivity() +
                ",\n \tslug='" + getSlug() + "'" +
                ",\n \tprojectID=" + getProjectID() +
                "\n}\n";
    }
}
