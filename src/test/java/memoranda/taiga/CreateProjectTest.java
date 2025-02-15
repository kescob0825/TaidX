package memoranda.taiga;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.modules.TaigaCreateProject;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateProjectTest {
    private static JSONArray mockCreateProjectJSONObjects;
    private static JSONObject emptyResponseBody;

    @Mock
    private OkHttpClient mockClient;
    @Mock
    private ObjectMapper mockMapper;

    @BeforeAll
    public static void setUp() throws IOException {
        mockCreateProjectJSONObjects = new JSONArray(new String(
                Files.readAllBytes(Paths.get("src/test/resources/mock_create_project_json_array.json"))));
        emptyResponseBody = new JSONObject();
    }

    @Test
    public void testCreateProjects0() throws IOException {
        String token = "Dummy token";
        JSONObject mockCreateProjectJSON = mockCreateProjectJSONObjects.getJSONObject(0);
        mockClient = mock(OkHttpClient.class);

        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        mockCreateProjectJSON.toString()
                ))
                .build();

        TaigaCreateProject createProject = new TaigaCreateProject(mockClient, mockMapper);
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(requestCaptor.capture())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        createProject.createProject(token, mockCreateProjectJSON);


        Request capturedRequest = requestCaptor.getValue();
        assertEquals("POST", capturedRequest.method());
        assertEquals(200, createProject.getLastResponseCode());
    }

    @Test
    public void testCreateProjects1() throws IOException {
        String token = "Dummy token";
        JSONObject mockCreateProjectJSON = mockCreateProjectJSONObjects.getJSONObject(1);
        mockClient = mock(OkHttpClient.class);

        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        mockCreateProjectJSON.toString()
                ))
                .build();

        TaigaCreateProject createProject = new TaigaCreateProject(mockClient, mockMapper);
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(requestCaptor.capture())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        createProject.createProject(token, mockCreateProjectJSON);


        Request capturedRequest = requestCaptor.getValue();
        assertEquals("POST", capturedRequest.method());
        assertEquals(200, createProject.getLastResponseCode());
    }

    @Test
    public void testCreateProjects2() throws IOException {
        String token = "Dummy token";
        JSONObject mockCreateProjectJSON = mockCreateProjectJSONObjects.getJSONObject(2);
        mockClient = mock(OkHttpClient.class);

        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(400)
                .message("COOKED")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        mockCreateProjectJSON.toString()
                ))
                .build();

        TaigaCreateProject createProject = new TaigaCreateProject(mockClient, mockMapper);
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(requestCaptor.capture())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        createProject.createProject(token, mockCreateProjectJSON);


        Request capturedRequest = requestCaptor.getValue();
        assertEquals("POST", capturedRequest.method());
        assertEquals(400, createProject.getLastResponseCode());
    }
}
