package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.models.ProjectData;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaigaProject {
    private static final String PROJECTS_URL = "https://api.taiga.io/api/v1/projects?member=";
    private final OkHttpClient httpClient;
    private ProjectData projectData;
    private List<ProjectData> projectDataList = new ArrayList<>();;
    private int lastResponseCode;

    public TaigaProject(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
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
                JSONArray jsonArray = new JSONArray(responseBody);
                projectDataList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonResponse = jsonArray.getJSONObject(i);
                    JSONObject ownerObj = jsonResponse.getJSONObject("owner");
                    projectData = new ProjectData(
                            jsonResponse.getString("name"),
                            jsonResponse.getString("description"),
                            jsonResponse.getString("created_date"),
                            ownerObj.getString("username"),
                            jsonResponse.getJSONArray("members").toList().stream().mapToInt(j -> (int) j).toArray(),
                            jsonResponse.getInt("total_activity"),
                            jsonResponse.getInt("id"),
                            jsonResponse.getString("slug")
                    );
                    projectDataList.add(projectData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProjectData> getProjectData() {
        return projectDataList;
    }

    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
