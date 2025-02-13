package memoranda.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;


public class UserProfile {
    private List<String> roles;
    private String username;
    private String fullName;
    private String email;
    private String lang;
    private String timezone;
    private String bio;
    private int uid;
    private List<ProjectData> projectsList;

    public UserProfile() {
        this.roles = new ArrayList<>();
        this.projectsList = new ArrayList<>();
    }
    public UserProfile(String fullName, String username, String email,
                       String lang, String timezone, String bio, List<String> roles, int uid) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.lang = lang;
        this.timezone = timezone;
        this.bio = bio;
        this.roles = roles;
        this.uid = uid;
        this.projectsList = new ArrayList<>();
    }
    public List<String> getRoles() {
        return roles;
    }
    public String getUsername() {
        return username;
    }
    public String getFullName() {
        return fullName;
    }
    public String getEmail() {
        return email;
    }
    public String getLang() {
        return lang;
    }
    public String getTimezone() {
        return timezone;
    }
    public String getBio() {
        return bio;
    }
    public int getUid() {
        return uid;
    }
    public List<ProjectData> getProjectsList() {
        return projectsList;
    }
    public void addProjects(List<ProjectData> projects) {
        projectsList.addAll(projects);
    }
}
