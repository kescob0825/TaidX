package memoranda.api.models;

import java.util.List;

public class UserStatsData {
    private List<String> roles;
    private int closedstories;
    private int numcontacts;
    private int numprojects;

    public UserStatsData(List<String> roles, int closedstories, int numcontacts, int numprojects) {
        this.roles = roles;
        this.closedstories = closedstories;
        this.numcontacts = numcontacts;
        this.numprojects = numprojects;
    }

    public List<String> getRoles() {
        return roles;
    }
    public int getClosedStories() {
        return closedstories;
    }
    public int getNumContacts() {
        return numcontacts;
    }
    public int getNumProjects() {
        return numprojects;
    }
}
