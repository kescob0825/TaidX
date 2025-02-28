package memoranda.api.modules;

import memoranda.api.models.IssuesData;
import memoranda.api.models.issueattributes.IssuePriority;
import memoranda.api.models.issueattributes.IssueSeverity;
import memoranda.api.models.issueattributes.IssueStatus;
import memoranda.api.models.issueattributes.IssueType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class TaigaIssues {
    private static final String ISSUES_URL = "https://api.taiga.io/api/v1/issues?project=";
    private static final String ISSUES_ATTRIBUTES_URL =
            "https://api.taiga.io/api/v1/issues/filters_data?project=";
    private final OkHttpClient httpClient;
    private IssuesData projectIssue;
    private List<IssuesData> projectIssueList;
    private List<IssuePriority> priorities;
    private List<IssueSeverity> severities;
    private List<IssueStatus> statuses;
    private List<IssueType> types;
    private int lastResponseCode;

    public TaigaIssues(OkHttpClient httpClient) {
        this.httpClient = httpClient;
        this.projectIssueList = new ArrayList<>();
    }

    public void getProjectIssues(String token, int uid) {
        String id = "Bearer " + token;
        String uidStr = Integer.toString(uid);
        Request request = new Request.Builder()
                .url(ISSUES_URL+uidStr)
                .get()
                .addHeader("Authorization", id)
                .addHeader("Content-Type", "application/json")
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            lastResponseCode = response.code();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                if (responseBody.isEmpty()) {
                    projectIssueList = new ArrayList<>();
                    return;
                }
                Object json = new JSONTokener(responseBody).nextValue();
                JSONArray jsonArray;
                if (json instanceof JSONObject) {
                    jsonArray = new JSONArray();
                    jsonArray.put(json);
                } else if (json instanceof JSONArray) {
                    jsonArray = (JSONArray) json;
                } else {
                    throw new IllegalStateException("Unexpected response type: " + json.getClass());
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonResponse = jsonArray.getJSONObject(i);
                    JSONObject projectInfoObj = jsonResponse.getJSONObject("project_extra_info");
                    JSONObject statusObj = jsonResponse.getJSONObject("status_extra_info");
                    //Create a new UserProfile object
                    projectIssue = new IssuesData(
                            projectInfoObj.optString("name"),
                            jsonResponse.optString("subject"),
                            statusObj.optString("name"),
                            jsonResponse.optBoolean("is_closed"),
                            jsonResponse.optInt("project"),
                            jsonResponse.optInt("status"),
                            jsonResponse.optInt("severity"),
                            jsonResponse.optInt("priority"),
                            jsonResponse.optInt("type")
                    );
                    projectIssue.setPriority(setPriorityName(jsonResponse.optInt("priority")));
                    projectIssue.setSeverity(setSeverityName(jsonResponse.optInt("severity")));
                    projectIssue.setType(setTypeName(jsonResponse.optInt("type")));
                    projectIssueList.add(projectIssue);
                }
            }
            else {
                System.out.println("Get User Stories Failed. Response code: " + lastResponseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getIssueAttributes(String token, int uid) {
        String id = "Bearer " + token;
        String uidStr = Integer.toString(uid);
        Request request = new Request.Builder()
                .url(ISSUES_ATTRIBUTES_URL+uidStr)
                .get()
                .addHeader("Authorization", id)
                .addHeader("Content-Type", "application/json")
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            lastResponseCode = response.code();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                priorities = new ArrayList<>();
                severities = new ArrayList<>();
                statuses = new ArrayList<>();
                types = new ArrayList<>();
                if (responseBody.isEmpty()) {
                    return;
                }
                JSONObject jsonResponse = new JSONObject(responseBody);

                JSONArray priorityArr = jsonResponse.getJSONArray("priorities");
                for (int i = 0; i < priorityArr.length(); i++) {
                    JSONObject object = priorityArr.getJSONObject(i);
                    IssuePriority priority = new IssuePriority(object.optInt("id"),
                            object.optString("name"), object.optInt("order"));
                    priorities.add(priority);
                }

                JSONArray severityArr = jsonResponse.getJSONArray("severities");
                for (int i = 0; i < severityArr.length(); i++) {
                    JSONObject object = severityArr.getJSONObject(i);
                    IssueSeverity severity = new IssueSeverity(object.optInt("id"),
                            object.optString("name"), object.optInt("order"));
                    severities.add(severity);
                }

                JSONArray statusArr = jsonResponse.getJSONArray("statuses");
                for (int i = 0; i < statusArr.length(); i++) {
                    JSONObject object = statusArr.getJSONObject(i);
                    IssueStatus status = new IssueStatus(object.optInt("id"),
                            object.optString("name"), object.optInt("order"));
                    statuses.add(status);
                }

                JSONArray typeArr = jsonResponse.getJSONArray("types");
                for (int i = 0; i < typeArr.length(); i++) {
                    JSONObject object = typeArr.getJSONObject(i);
                    IssueType type = new IssueType(object.optInt("id"),
                            object.optString("name"), object.optInt("order"));
                    types.add(type);
                }
            }
            else {
                System.out.println("Get User Stories Failed. Response code: " + lastResponseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<IssuesData> getProjectIssueList() {
        return projectIssueList;
    }

    public List<IssuePriority> getIssuePriorityList() {
        return this.priorities;
    }

    public List<IssueSeverity> getIssueSeverityList() {
        return this.severities;
    }

    public List<IssueStatus> getIssueStatusList() {
        return this.statuses;
    }

    public List<IssueType> getIssueTypeList() {
        return this.types;
    }

    public void clearProjectIssueList() {
        projectIssueList.clear();
    }

    public int getLastResponseCode() {
        return lastResponseCode;
    }

    private String setPriorityName(int priorityID) {

        for (IssuePriority priority: this.priorities) {
            if (priority.getID() == priorityID) {
                return priority.getName();
            }
        }
        return null;
    }

    private String setSeverityName(int severityID) {

        for (IssueSeverity severity: this.severities) {
            if (severity.getID() == severityID) {
                return severity.getName();
            }
        }
        return null;
    }

    private String setTypeName(int typeID) {

        for (IssueType type: this.types) {
            if (type.getID() == typeID) {
                return type.getName();
            }
        }
        return null;
    }

}
