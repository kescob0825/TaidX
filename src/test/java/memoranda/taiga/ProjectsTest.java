package memoranda.taiga;


import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.TaigaClient;
import memoranda.api.modules.TaigaAuthenticate;
import memoranda.api.modules.TaigaProject;
import okhttp3.*;
        import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
public class ProjectsTest {

    private static JSONArray projectsResponse;
    private static JSONObject singleProjectResponse;
    private static JSONObject emptyResponseBody;
    private static JSONArray projectRolesResponse;

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
        projectsResponse = new JSONArray(new String(
                Files.readAllBytes(Paths.get("src/test/resources/project_data.json"))));
        singleProjectResponse = new JSONObject(new String(
                Files.readAllBytes(Paths.get("src/test/resources/single_project.json"))));
        projectRolesResponse = new JSONArray(new String(
                Files.readAllBytes(Paths.get("src/test/resources/get_roles_for_project.json"))));
        emptyResponseBody = new JSONObject();
    }

    @Test
    public void testGetProjectsPass() throws IOException {
        String token = "Dummy token";
        int uid = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/projects").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        projectsResponse.toString()
                ))
                .build();

        TaigaProject  taigaProject = new TaigaProject(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaProject.getProjects(token, uid);

        assertFalse(taigaProject.getProjectsData().isEmpty());
        assertEquals(200, taigaProject.getLastResponseCode());

    }

    @Test
    public void testGetProjectsFail() throws IOException {
        String token = "Dummy token";
        int uid = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(400)
                .message("COOKED")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/projects").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        emptyResponseBody.toString()
                ))
                .build();

        TaigaProject  taigaProject = new TaigaProject(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaProject.getProjects(token, uid);

        assertTrue(taigaProject.getProjectsData().isEmpty());
        assertEquals(400, taigaProject.getLastResponseCode());

    }

    @Test
    public void testGetProjectsSinglePass() throws IOException {
        String token = "Dummy token";
        int uid = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/projects").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        projectRolesResponse.toString()
                ))
                .build();

        TaigaProject  taigaProject = new TaigaProject(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaProject.getProjectsRoles(token, uid);

        assertFalse(taigaProject.getProjectsRolesData().isEmpty());
        assertEquals(200, taigaProject.getLastResponseCode());

    }
    @Test
    public void testGetProjectsSingleFail() throws IOException {
        String token = "Dummy token";
        int uid = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(501)
                .message("COOKED")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/projects").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        singleProjectResponse.toString()
                ))
                .build();

        TaigaProject  taigaProject = new TaigaProject(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaProject.getProjects(token, uid);

        assertTrue(taigaProject.getProjectsData().isEmpty());
        assertEquals(501, taigaProject.getLastResponseCode());

    }

    @Test
    public void testGetProjectsRolesPass() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/roles?project=").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        projectRolesResponse.toString()
                ))
                .build();

        TaigaProject  taigaProject = new TaigaProject(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaProject.getProjectsRoles(token, projectID);

        assertFalse(taigaProject.getProjectsRolesData().isEmpty());
        assertEquals(200, taigaProject.getLastResponseCode());

    }
    @Test
    public void testGetProjectsRolesFail() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(501)
                .message("COOKED")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/roles?project=").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        projectRolesResponse.toString()
                ))
                .build();

        TaigaProject  taigaProject = new TaigaProject(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaProject.getProjectsRoles(token, projectID);

        assertTrue(taigaProject.getProjectsRolesData().isEmpty());
        assertEquals(501, taigaProject.getLastResponseCode());

    }

    @Test
    public void testSetProjectsRolesPass() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/roles?project=").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        emptyResponseBody.toString()
                ))
                .build();

        TaigaProject  taigaProject = new TaigaProject(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaProject.getProjectsRoles(token, projectID);

        assertEquals(200, taigaProject.getLastResponseCode());
    }

    @Test
    public void testSetProjectsRolesFail() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(500)
                .message("COOKED")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/roles?project=").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        emptyResponseBody.toString()
                ))
                .build();

        TaigaProject  taigaProject = new TaigaProject(mockClient, mockMapper);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        taigaProject.getProjectsRoles(token, projectID);

        assertEquals(500, taigaProject.getLastResponseCode());
    }
}