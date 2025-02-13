package memoranda.api.modules;

import memoranda.api.models.ProjectData;
import memoranda.api.models.ProjectRolesData;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class TaigaInvite {
    private static final String INVITE_URL = "https://api.taiga.io/api/v1/memberships";
    private static final String BULK_INVITE_URL = "https://api.taiga.io/api/v1/memberships/bulk_create";
    private final OkHttpClient httpClient;
    private ProjectData projectData;
    private List<ProjectData> projectDataList;
    private ProjectRolesData projectRoleData;
    private List<ProjectRolesData> projectRolesDataList;
    private int lastResponseCode;

    public TaigaInvite(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void inviteMember(String token, JSONObject inviteBody) throws IOException {
        MediaType mediaType = MediaType.get("application/json");
        String id = "Bearer " + token;
        String jsonBody = inviteBody.toString();
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        Request request = new Request.Builder()
                .url(INVITE_URL)
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

    public void bulkInviteMembers(String token, JSONObject bulkInviteBody) throws IOException {
        MediaType mediaType = MediaType.get("application/json");
        String id = "Bearer " + token;
        String jsonBody = bulkInviteBody.toString();
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        Request request = new Request.Builder()
                .url(INVITE_URL)
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
}
