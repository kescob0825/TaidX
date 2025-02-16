package memoranda.taiga;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.modules.TaigaUserStory;
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

public class UserStoryTest {
    private static JSONArray userStoriesResponse;
    private static JSONObject singleUserStoryResponse;
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
        userStoriesResponse = new JSONArray(new String(
                Files.readAllBytes(Paths.get("src/test/resources/user_story_data.json"))));
        singleUserStoryResponse = new JSONObject(new String(
                Files.readAllBytes(Paths.get("src/test/resources/single_user_story.json"))));
        emptyResponseBody = new JSONObject();
    }

    @Test
    public void userStoriesTest() throws IOException {
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
                        userStoriesResponse.toString()
                ))
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        TaigaUserStory  userStory = new TaigaUserStory(mockClient, mockMapper);
        userStory.getUserStories(token, projectID);

        assertFalse(userStory.getUserStoryNodes().isEmpty());
        assertEquals(2, userStory.getUserStoryNodes().size());
        assertEquals(200, userStory.getLastResponseCode());
    }

    @Test
    public void userStoryTest() throws IOException {
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
                        singleUserStoryResponse.toString()
                ))
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        TaigaUserStory  userStory = new TaigaUserStory(mockClient, mockMapper);
        userStory.getUserStories(token, projectID);

        assertFalse(userStory.getUserStoryNodes().isEmpty());
        assertEquals(1, userStory.getUserStoryNodes().size());
        assertEquals(200, userStory.getLastResponseCode());
    }

    @Test
    public void userStoryTestFail() throws IOException {
        String token = "Dummy token";
        int projectID = 1;
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(500)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/auth").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        singleUserStoryResponse.toString()
                ))
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        TaigaUserStory  userStory = new TaigaUserStory(mockClient, mockMapper);
        userStory.getUserStories(token, projectID);

        assertTrue(userStory.getUserStoryNodes().isEmpty());
        assertEquals(500, userStory.getLastResponseCode());
    }
}
