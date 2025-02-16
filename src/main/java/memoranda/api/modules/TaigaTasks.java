package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.models.TaskNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaigaTasks {
    private static final String USER_STORY_URL = "https://api.taiga.io/api/v1/tasks?project=";
    private final OkHttpClient httpClient;
    private TaskNode taskNode;
    private List<TaskNode> taskNodes;
    private Map<Integer, List<TaskNode>> taskNodeMap;
    private int lastResponseCode;
    private boolean isClosed = false;

    public TaigaTasks(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.taskNodes = new ArrayList<>();
    }

    public void getTasks(String token, int uid) throws IOException {
        String id = "Bearer " + token;
        String uidStr = Integer.toString(uid)+"&status__is_closed="+isClosed;
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
                    taskNodes = new ArrayList<>();
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
                    JSONObject assignedToExtraInfoObj = new JSONObject();
                    if (!jsonResponse.isNull("assigned_to_extra_info")){
                        assignedToExtraInfoObj = jsonResponse.getJSONObject("assigned_to_extra_info");
                    }
                    else{
                        assignedToExtraInfoObj.put("id", 0);
                        assignedToExtraInfoObj.put("username", "default");
                    }
                    JSONObject statusObj = jsonResponse.getJSONObject("status_extra_info");
                    JSONObject projectInfoObj = jsonResponse.getJSONObject("project_extra_info");
                    JSONObject userStoryInfoObj = jsonResponse.getJSONObject("user_story_extra_info");

                    //Create a new UserProfile object
                    taskNode = new TaskNode(
                            jsonResponse.optInt("ref"),
                            jsonResponse.optString("subject"),
                            jsonResponse.optInt("id"),
                            jsonResponse.optInt("project"),
                            projectInfoObj.optString("name"),
                            jsonResponse.optInt("milestone"),
                            jsonResponse.optString("milestone_slug", "null"),
                            userStoryInfoObj.optInt("id", 0),
                            userStoryInfoObj.optString("subject", "null"),
                            userStoryInfoObj.optInt("ref", 0),
                            assignedToExtraInfoObj.optString("username", "null"),
                            assignedToExtraInfoObj.optInt("id", 0),
                            statusObj.optString("name", "null")
                    );
                    taskNodes.add(taskNode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<TaskNode> getTaskNodes() {
        return taskNodes;
    }
    public int getLastResponseCode() {
        return lastResponseCode;
    }
    public void getAllTasks(String token, int uid) throws IOException {
        getTasks(token, uid);
        this.isClosed = true;
        getTasks(token, uid);
    }
    public void clearTaskNodes() {
        taskNodes.clear();
    }
}
