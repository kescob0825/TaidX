package memoranda.api.models;

public class UserProfile {
    private String roles;
    private String username;
    private String fullName;
    private String email;
    private String lang;
    private String timezone;
    private String bio;
    private int uid;

    public UserProfile(String fullName, String username, String email,
                       String lang, String timezone, String bio, String roles, int uid) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.lang = lang;
        this.timezone = timezone;
        this.bio = bio;
        this.roles = roles;
        this.uid = uid;
    }
    public String getRoles() {
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
}
