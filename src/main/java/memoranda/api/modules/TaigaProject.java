package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.models.ProjectData;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class TaigaProject {
    private static final String PROJECTS_URL = "https://api.taiga.io/api/v1/projects?member=";
    private final OkHttpClient httpClient;
    private ProjectData projectData;

    private int lastResponseCode;

    public TaigaProject(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
    }

    public void getProjects(int uid) {
        MediaType mediaType = MediaType.get("application/json");
        JSONObject jsonObject = new JSONObject();
        String jsonBody = jsonObject.toString();
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        String id = "Bearer " + uid;
        Request request = new Request.Builder()
                .url(PROJECTS_URL)
                .post(body)
                .addHeader("Authorization", id)
                .addHeader("Content-Type", "application/json")
                .build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            lastResponseCode = response.code();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                int projectID = jsonResponse.getInt("id");
                String name = jsonResponse.getString("name");
                String description = jsonResponse.getString("description");
                String slug = jsonResponse.getString("slug");
                String dateCreated = jsonResponse.getString("created_date");
                String projectOwner = jsonResponse.getString("project_owner");
                int[] members = jsonResponse.getJSONArray("members").toList().stream().mapToInt(i -> (int) i).toArray();
                int totalActivity = jsonResponse.getInt("total_activity");
                projectData = new ProjectData(name, description, dateCreated, projectOwner, members, totalActivity, projectID, slug);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProjectData getProjectData() {
        return projectData;
    }
    public int getLastResponseCode() {
        return lastResponseCode;
    }
}
