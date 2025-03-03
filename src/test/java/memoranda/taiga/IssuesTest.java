package memoranda.taiga;
import memoranda.api.modules.TaigaCreateIssue;
import memoranda.api.modules.TaigaIssues;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IssuesTest {

    private static JSONArray issuesResponse;
    private static JSONObject attributesResponse;
    private static JSONArray createIssueJSONObjects;
    @Mock
    private OkHttpClient mockClient;

    @BeforeAll
    public static void setUp() throws IOException {
        issuesResponse = new JSONArray(new String(
                Files.readAllBytes(Paths.get("src/test/resources/issues.json"))));
        attributesResponse = new JSONObject(new String(
                Files.readAllBytes(Paths.get("src/test/resources/issue_attributes.json"))));
        createIssueJSONObjects = new JSONArray(new String(
                Files.readAllBytes(Paths.get("src/test/resources/issue_creation.json"))));
    }

    @Test
    public void isssuesTest() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/auth").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        attributesResponse.toString()
                ))
                .build();

        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        TaigaIssues issues = new TaigaIssues(mockClient);
        issues.getIssueAttributes(token, projectID);
        mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/auth").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        issuesResponse.toString()
                ))
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        issues.getProjectIssues(token, projectID);

        assertFalse(issues.getProjectIssueList().isEmpty());
        assertEquals(5, issues.getProjectIssueList().size());
        assertFalse(issues.getIssuePriorityList().isEmpty());
        assertEquals(3, issues.getIssuePriorityList().size());
        assertFalse(issues.getIssueSeverityList().isEmpty());
        assertEquals(5, issues.getIssueSeverityList().size());
        assertFalse(issues.getIssueStatusList().isEmpty());
        assertEquals(7, issues.getIssueStatusList().size());
        assertFalse(issues.getIssueTypeList().isEmpty());
        assertEquals(3, issues.getIssueTypeList().size());
        assertEquals(200, issues.getLastResponseCode());
    }

    @Test
    public void testCreateProjects() throws IOException {
        String token = "Dummy token";
        JSONObject mockCreateIssueJSON = createIssueJSONObjects.getJSONObject(0);
        mockClient = mock(OkHttpClient.class);

        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        mockCreateIssueJSON.toString()
                ))
                .build();

        TaigaCreateIssue createIssue = new TaigaCreateIssue(mockClient);
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(requestCaptor.capture())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        createIssue.createIssue(token, mockCreateIssueJSON);


        Request capturedRequest = requestCaptor.getValue();
        assertEquals("POST", capturedRequest.method());
        assertEquals(200, createIssue.getLastResponseCode());
    }

    @Test
    public void testCreateEmptyProjects() throws IOException {
        String token = "Dummy token";
        JSONObject mockCreateIssueJSON = createIssueJSONObjects.getJSONObject(1);
        mockClient = mock(OkHttpClient.class);

        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(400)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/issues").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        mockCreateIssueJSON.toString()
                ))
                .build();

        TaigaCreateIssue createIssue = new TaigaCreateIssue(mockClient);
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(requestCaptor.capture())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        createIssue.createIssue(token, mockCreateIssueJSON);


        Request capturedRequest = requestCaptor.getValue();
        assertEquals("POST", capturedRequest.method());
        assertEquals(400, createIssue.getLastResponseCode());
    }
}
