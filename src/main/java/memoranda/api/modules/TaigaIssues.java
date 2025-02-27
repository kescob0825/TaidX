package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.models.IssuesData;
import memoranda.api.models.UserStoryNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaigaIssues {
    private static final String USER_STORY_URL = "https://api.taiga.io/api/v1/issues?project=";
    private final OkHttpClient httpClient;
    private IssuesData projectIssue;
    private List<IssuesData> projectIssueList;
    private int lastResponseCode;

    public TaigaIssues(OkHttpClient httpClient) {
        this.httpClient = httpClient;
        this.projectIssueList = new ArrayList<>();
    }

    public void getProjectIssues(String token, int uid) {
        String id = "Bearer " + token;
        String uidStr = Integer.toString(uid);
        Request request = new Request.Builder()
                .url(USER_STORY_URL+uidStr)
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
                    JSONObject ownerObj = new JSONObject();
                    if (!jsonResponse.isNull("owner_extra_info")) {
                        ownerObj = jsonResponse.getJSONObject("owner_extra_info");;
                    }
                    JSONObject statusObj = jsonResponse.getJSONObject("status_extra_info");
                    JSONObject projectInfoObj = jsonResponse.getJSONObject("project_extra_info");

                    //Create a new UserProfile object
                    projectIssue = new IssuesData(
                            jsonResponse.optString("subject")
                            /*jsonResponse.optInt("id"),
                            jsonResponse.optString("subject"),
                            jsonResponse.optInt("total_points", 0),
                            jsonResponse.optString("created_date"),
                            jsonResponse.optString("modified_date"),
                            jsonResponse.optString("finish_date", "null"),
                            statusObj.optBoolean("is_closed"),
                            ownerObj.optInt("id"),
                            ownerObj.optString("username"),
                            statusObj.optString("name"),
                            jsonResponse.getInt("project"),
                            projectInfoObj.optString("name"),
                            jsonResponse.optString("milestone_name", "product_backlog"),
                            jsonResponse.optInt("milestone", 0),
                            jsonResponse.optString("milestone_slug", "null")*/
                    );
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

    public List<IssuesData> getProjectIssueList() {
        return projectIssueList;
    }

    public void clearProjectIssueList() {
        projectIssueList.clear();
    }

    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
