package memoranda.api.models;

import java.util.Arrays;
import java.util.List;

public class ProjectRolesData {
    private int role_id;
    private String role_name;
    private int project_id;
    private int order;
    private int members_count;
    private List<String> permissions;
    private List<List<String>> permissionsCreate;
    public static final List<String> view_permission = Arrays.asList( "view_issues", "view_milestones", "view_project", "add_task", "modify_task", "delete_task",
            "view_tasks", "view_us", "view_wiki_pages", "view_wiki_links", "view_epics", "comment_epic", "comment_us",
            "comment_task", "comment_issue", "comment_wiki_page");
    public static final List<String> edit_permission = Arrays.asList( "add_issue",
            "modify_issue", "delete_issue", "view_issues", "add_milestone", "modify_milestone",
            "delete_milestone", "view_milestones", "view_project", "add_task", "modify_task", "delete_task",
            "view_tasks", "add_us", "modify_us", "delete_us", "view_us", "add_wiki_page", "modify_wiki_page",
            "delete_wiki_page", "view_wiki_pages", "add_wiki_link", "delete_wiki_link", "view_wiki_links",
            "view_epics", "add_epic", "modify_epic", "delete_epic", "comment_epic", "comment_us",
            "comment_task", "comment_issue", "comment_wiki_page");

    public ProjectRolesData(int role_id, String role_name, int project_id, List<String> permissions, int order, int members_count) {
        this.role_id = role_id;
        this.role_name = role_name;
        this.project_id = project_id;
        this.permissions = permissions;
        this.members_count = members_count;
        this.order = order;
    }

    public int get_role_id() {
        return role_id;
    }
    public String get_role_name() {
        return role_name;
    }
    public int getProject_id() {
        return project_id;
    }
    public List<String> getPermissions() {
        return permissions;
    }
    public int getMembers_count() {
        return members_count;
    }

}
