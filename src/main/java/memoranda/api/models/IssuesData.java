package memoranda.api.models;

public class IssuesData {

    String subject;
    boolean is_closed;
    String status;
    String assigned_to;
    int project;

    public IssuesData(String subject, String status, boolean is_closed,
                      String assigned_to, int project) {
        this.subject = subject;
        this.status = status;
        this.is_closed = is_closed;
        this.assigned_to = assigned_to;
        this.project = project;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isIs_closed() {
        return is_closed;
    }

    public void setIs_closed(boolean is_closed) {
        this.is_closed = is_closed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(String assigned_to) {
        this.assigned_to = assigned_to;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }
}
