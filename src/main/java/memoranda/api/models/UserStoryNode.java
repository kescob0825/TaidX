package memoranda.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStoryNode {
    private int id;
    private int ref; //US#
    private String subject;
    private int totalPoints;
    private String createdDate;
    private String modifiedDate;
    private String finishDate;
    private boolean isClosed;
    private int ownerID;
    private String ownerUserName;
    private String status;
    private int projectID;
    private String projectName;
    private String mileStoneName;

    public UserStoryNode(int id, int ref, String subject,
                         int totalPoints, String createdDate,
                         String modifiedDate, String finishDate,
                         boolean isClosed, int ownerID,
                         String ownerUserName, String status,
                         int projectID, String projectName,
                         String mileStoneName) {
        this.id = id;
        this.ref = ref;
        this.subject = subject;
        this.totalPoints = totalPoints;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.finishDate = finishDate;
        this.isClosed = isClosed;
        this.ownerID = ownerID;
        this.ownerUserName = ownerUserName;
        this.status = status;
        this.projectID = projectID;
        this.projectName = projectName;
        this.mileStoneName = mileStoneName;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRef() {
        return ref;
    }
    public void setRef(int ref) {
        this.ref = ref;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public int getTotalPoints() {
        return totalPoints;
    }
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
    public String getCreatedDate() {
        if(createdDate == null) {
            return "null";
        }
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public String getModifiedDate() {
        if(modifiedDate == null) {
            return "null";
        }
        return modifiedDate;
    }
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    public String getFinishDate() {
        if(finishDate == null) {
            return "null";
        }
        return finishDate;
    }
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
    public boolean isClosed() {
        return isClosed;
    }
    public void setClosed(boolean closed) {
        isClosed = closed;
    }
    public int getOwnerID() {
        return ownerID;
    }
    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
    public String getOwnerUserName() {
        return ownerUserName;
    }
    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getProjectID() {
        return projectID;
    }
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getMileStoneName() {
        return mileStoneName;
    }
    public void setMileStoneName(String mileStoneName) {
        this.mileStoneName = mileStoneName;
    }
    @Override
    public String toString() {
        return "UserStory{\n" +
                " \tid=" + getId() +
                ",\n \tref=" + getRef() +
                ",\n \tsubject='" + getSubject() + "'" +
                ",\n \ttotalPoints=" + getTotalPoints() +
                ",\n \tcreatedDate=" + getCreatedDate() +
                ",\n \tmodifiedDate=" + getModifiedDate() +
                ",\n \tfinishDate=" + getFinishDate() +
                ",\n \tisClosed=" + isClosed() +
                ",\n \townerID=" + getOwnerID() +
                ",\n \townerUserName='" + getOwnerUserName() + "'" +
                ",\n \tstatus=" + getStatus() +
                ",\n \tprojectID=" + getProjectID() +
                ",\n \tprojectName='" + getProjectName() + "'" +
                ",\n \tmileStoneName='" + getMileStoneName() + "'" +
                "\n}\n";
    }


}
