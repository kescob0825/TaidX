package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.models.ProjectData;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.*;

public class TaigaProject {
    private static final String PROJECTS_URL = "https://api.taiga.io/api/v1/projects?member=";
    private final OkHttpClient httpClient;
    private ProjectData projectData;
    private List<ProjectData> projectDataList;
    private int lastResponseCode;

    public TaigaProject(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.projectDataList = new ArrayList<>();
    }

    public void getProjects(String token, int uid) {
        String id = "Bearer " + token;
        String uidStr = Integer.toString(uid);
        Request request = new Request.Builder()
                .url(PROJECTS_URL+uidStr)
                .get()
                .addHeader("Authorization", id)
                .addHeader("Content-Type", "application/json")
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            lastResponseCode = response.code();
            System.out.println("Response code: " + lastResponseCode);
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                if (responseBody.isEmpty()) {
                    projectDataList = new ArrayList<>();
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
                projectDataList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonResponse = jsonArray.getJSONObject(i);
                    JSONObject ownerObj = jsonResponse.getJSONObject("owner");
                    projectData = new ProjectData(
                            jsonResponse.optString("name"),
                            jsonResponse.optString("description"),
                            jsonResponse.optString("created_date"),
                            ownerObj.optString("username"),
                            jsonResponse.getJSONArray("members").toList().stream().mapToInt(j -> (int) j).toArray(),
                            jsonResponse.optInt("total_activity"),
                            jsonResponse.optInt("id"),
                            jsonResponse.optBoolean("is_private"),
                            jsonResponse.optString("slug")
                    );
                    projectDataList.add(projectData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProjectData> getProjectsData() {
        return projectDataList;
    }

    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
