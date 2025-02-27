package memoranda.api.models;

public class IssuesData {

    String project_name;
    String subject;
    boolean is_closed;
    String status;
    int project_id;

    public IssuesData(String project_name, String subject, String status
            , boolean is_closed, int project_id) {
        this.project_name = project_name;
        this.subject = subject;
        this.status = status;
        this.is_closed = is_closed;
        this.project_id = project_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isClosed() {
        return is_closed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
}
