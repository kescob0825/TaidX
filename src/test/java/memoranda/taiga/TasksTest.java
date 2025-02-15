package memoranda.taiga;


import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.modules.TaigaProject;
import memoranda.api.modules.TaigaTasks;
import okhttp3.*;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.json.JSONObject;
import org.json.JSONArray;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * Test class for TaigaClient
 * This class contains test methods for the TaigaClient class
 */
public class TasksTest {

    private static JSONArray tasksOpenResponse;
    private static JSONObject singleOpenTaskResponse;
    private static JSONArray tasksClosedResponse;
    private static JSONObject emptyResponseBody;
    @Mock
    private OkHttpClient mockClient;
    @Mock
    private ObjectMapper mockMapper;

    /**
     * Set up method for the test class
     * This method is executed before each test method
     * It sets the username and password for the TaigaClient instance
     */
    @BeforeAll
    public static void setUp() throws IOException {
        tasksOpenResponse = new JSONArray(new String(
                Files.readAllBytes(Paths.get("src/test/resources/tasks_open.json"))));
        singleOpenTaskResponse = new JSONObject(new String(
                Files.readAllBytes(Paths.get("src/test/resources/single_task.json"))));
        tasksClosedResponse = new JSONArray(new String(
                Files.readAllBytes(Paths.get("src/test/resources/tasks_closed.json"))));
        emptyResponseBody = new JSONObject();
    }

    @Test
    public void testGetOpenTasksPass() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/tasks").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        tasksOpenResponse.toString()
                ))
                .build();

        TaigaTasks taigaTasks = new TaigaTasks(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaTasks.getTasks(token, projectID);

        assertFalse(taigaTasks.getTaskNodes().isEmpty());
        assertEquals(200, taigaTasks.getLastResponseCode());
    }

    @Test
    public void testGetClosedTasksPass() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/tasks").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        tasksClosedResponse.toString()
                ))
                .build();

        TaigaTasks taigaTasks = new TaigaTasks(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaTasks.getTasks(token, projectID);

        assertFalse(taigaTasks.getTaskNodes().isEmpty());
        assertEquals(200, taigaTasks.getLastResponseCode());
    }

    @Test
    public void testGetTasksFail() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(500)
                .message("COOKED")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/tasks").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        emptyResponseBody.toString()
                ))
                .build();

        TaigaTasks taigaTasks = new TaigaTasks(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaTasks.getTasks(token, projectID);

        assertTrue(taigaTasks.getTaskNodes().isEmpty());
        assertEquals(500, taigaTasks.getLastResponseCode());
    }

    @Test
    public void testGetSingleTaskPass() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/tasks").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        singleOpenTaskResponse.toString()
                ))
                .build();

        TaigaTasks taigaTasks = new TaigaTasks(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaTasks.getTasks(token, projectID);

        assertFalse(taigaTasks.getTaskNodes().isEmpty());
        assertEquals(200, taigaTasks.getLastResponseCode());
    }
}
