package memoranda.api.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.Start;
import memoranda.api.TaigaClient;
import memoranda.api.models.ProjectData;
import memoranda.api.models.ProjectRolesData;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class TaigaProject {
    private static final String PROJECTS_URL = "https://api.taiga.io/api/v1/projects?member=";
    private static final String PROJECTS_ROLES_URL = "https://api.taiga.io/api/v1/roles?project=";
    private static final String ROLES_URL = "https://api.taiga.io/api/v1/roles?project=";
    private final OkHttpClient httpClient;
    private ProjectData projectData;
    private List<ProjectData> projectDataList;
    private ProjectRolesData projectRoleData;
    private List<ProjectRolesData> projectRolesDataList;
    private int lastResponseCode;

    public TaigaProject(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.projectDataList = new ArrayList<>();
        this.projectRolesDataList = new ArrayList<>();
    }

    public void getProjects(String token, int userID) {
        String id = "Bearer " + token;
        String uid = Integer.toString(userID);
        Request request = new Request.Builder()
                .url(PROJECTS_URL+uid)
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
                            jsonResponse.getJSONArray("members").toList().
                                    stream().mapToInt(j -> (int) j).toArray(),
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

    public void getProjectsRoles(String token, int projectID) {
        String id = "Bearer " + token;
        String projectIDStr = Integer.toString(projectID);
        Request request = new Request.Builder()
                .url(PROJECTS_ROLES_URL+projectIDStr)
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
                    projectRolesDataList = new ArrayList<>();
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
                projectRolesDataList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonResponse = jsonArray.getJSONObject(i);
                    projectRoleData = new ProjectRolesData(
                            jsonResponse.optInt("id"),
                            jsonResponse.optString("name"),
                            jsonResponse.optInt("project"),
                            jsonResponse.getJSONArray("permissions")
                                    .toList().stream().map(Object::toString).toList(),
                            jsonResponse.optInt("order"),
                            jsonResponse.optInt("members_count")
                    );
                    projectRolesDataList.add(projectRoleData);
                }
                TaigaClient  taigaClient = Start.getInjector().getInstance(TaigaClient.class);
                for (ProjectData projectData : taigaClient.getProjectsList()) {
                    if (projectData.getProjectId() == projectRoleData.getProject_id()) {
                        projectData.addProjectRolesList(projectRolesDataList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProjectRoles(String token, JSONObject newProjectBody) throws IOException {
        MediaType mediaType = MediaType.get("application/json");
        String id = "Bearer " + token;
        String jsonBody = newProjectBody.toString();
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        Request request = new Request.Builder()
                .url(ROLES_URL)
                .post(body)
                .addHeader("Authorization", id)
                .addHeader("Content-Type", "application/json")
                .build();
        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            lastResponseCode = response.code();
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                String project_name = jsonResponse.getString("name");
                JOptionPane.showMessageDialog(null,  "Role added Successfully.");
            }
            else {
                JOptionPane.showMessageDialog(null, "Failed to add role.");
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
