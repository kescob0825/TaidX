package memoranda.api.models;

import java.util.Date;

public class UserStoryNode {
    private String id;
    private int ref; //US#
    private String subject;
    private int totalPoints;
    private Date createdDate;
    private Date modifiedDate;
    private Date finishDate;
    private boolean isClosed;
    private String ownerID;
    private String ownerUserName;
    private int status;


}
