package memoranda.api.models;

public class IssuesData {

    private final String project_name;
    private final String subject;
    private final boolean is_closed;
    private String status;
    private String severity;
    private String priority;
    private String type;
    private final int project_id;
    private final int status_id;
    private final int severity_id;
    private final int priority_id;
    private final int type_id;

    public IssuesData(String project_name, String subject, String status
            , boolean is_closed, int project_id, int status_id, int severity_id
            , int priority_id, int type_id) {
        this.project_name = project_name;
        this.subject = subject;
        this.status = status;
        this.is_closed = is_closed;
        this.project_id = project_id;
        this.status_id = status_id;
        this.severity_id = severity_id;
        this.priority_id = priority_id;
        this.type_id = type_id;
    }

    public String getSubject() {
        return subject;
    }

    public boolean isClosed() {
        return is_closed;
    }

    public String getProjectName() {
        return project_name;
    }

    public int getProjectId() {
        return project_id;
    }

    public int getStatusId() {
        return status_id;
    }

    public int getSeverityId() {
        return severity_id;
    }

    public int getPriorityId() {
        return priority_id;
    }

    public int getTypeId() {
        return type_id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
