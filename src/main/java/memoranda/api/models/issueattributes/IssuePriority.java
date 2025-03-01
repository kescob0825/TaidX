package memoranda.api.models.issueattributes;

public class IssuePriority implements IssueAttribute {

    private final int id;
    private final String name;
    private final int order;

    public IssuePriority(int id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getOrder() {
        return this.order;
    }
}