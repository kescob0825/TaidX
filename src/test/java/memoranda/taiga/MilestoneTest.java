package memoranda.taiga;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.modules.TaigaAuthenticate;
import memoranda.api.modules.TaigaMilestone;
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

public class MilestoneTest {
    private static JSONArray milestonesResponse;
    private static JSONObject singleMilestoneResponse;
    private static JSONObject emptyResponseBody;

    /**
     * Mock object for OkHttpClient.
     */
    @Mock
    private OkHttpClient mockClient;
    /**
     * Mock object for ObjectMapper.
     */
    @Mock
    private ObjectMapper mockMapper;

    /**
     * Set up method to initialize mock data and objects.
     * @throws IOException if an I/O error occurs
     */
    @BeforeAll
    public static void setUp() throws IOException {
        milestonesResponse = new JSONArray(new String(
                Files.readAllBytes(Paths.get("src/test/resources/milestone_data.json"))));
        singleMilestoneResponse = new JSONObject(new String(
                Files.readAllBytes(Paths.get("src/test/resources/single_milestone.json"))));
        emptyResponseBody = new JSONObject();
    }

    /**
     * Test case to verify getting the Milestones
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetMilestones() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/auth").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        milestonesResponse.toString()
                ))
                .build();

        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        TaigaMilestone milestone = new TaigaMilestone(mockClient, mockMapper);
        milestone.getMilestones(token, projectID);

        assertFalse(milestone.getSprintDataList().isEmpty());
        assertEquals(2, milestone.getSprintDataList().size());
        assertEquals("Sprint 2", milestone.getSprintDataList().get(0).getSprintName());
        assertEquals("Sprint 1", milestone.getSprintDataList().get(1).getSprintName());
        assertEquals(200, milestone.getLastResponseCode());
    }

    /**
     * Test case to verify getting the Milestones
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetMilestone() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/milestone").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        singleMilestoneResponse.toString()
                ))
                .build();

        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        TaigaMilestone milestone = new TaigaMilestone(mockClient, mockMapper);
        milestone.getMilestones(token, projectID);

        assertFalse(milestone.getSprintDataList().isEmpty());
        assertEquals(1, milestone.getSprintDataList().size());
        assertEquals("Sprint 2", milestone.getSprintDataList().get(0).getSprintName());
        assertEquals(200, milestone.getLastResponseCode());
    }
    /**
     * Test case to verify getting the Milestones fail
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetMilestoneFail() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(500)
                .message("COOKED")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/milestone").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        emptyResponseBody.toString()
                ))
                .build();

        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        TaigaMilestone milestone = new TaigaMilestone(mockClient, mockMapper);
        milestone.getMilestones(token, projectID);

        assertTrue(milestone.getSprintDataList().isEmpty());
        assertEquals(500, milestone.getLastResponseCode());
    }
}
