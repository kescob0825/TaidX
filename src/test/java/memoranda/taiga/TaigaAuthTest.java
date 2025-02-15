package memoranda.taiga;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.modules.TaigaAuthenticate;
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
public class TaigaAuthTest {
    private static JSONObject authResponse;
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
        authResponse = new JSONObject(new String(
                Files.readAllBytes(Paths.get("src/test/resources/auth.json"))));
        emptyResponseBody = new JSONObject();

    }

    @Test
    public void testAuthenticate() throws IOException {
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
                        authResponse.toString()
                ))
                .build();

        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        TaigaAuthenticate taigaAuth = new TaigaAuthenticate(mockClient, mockMapper);
        taigaAuth.authenticate("username", "password");

        assertEquals("TestAuthToken", taigaAuth.getAuthToken());
        assertEquals("TestRefreshToken", taigaAuth.getRefreshToken());
        assertEquals(200, taigaAuth.getLastResponseCode());
        assertEquals("somebody@asu.edu", taigaAuth.getUserProfile().getEmail());
        assertEquals(123456, taigaAuth.getUserProfile().getUid());
        assertEquals("Test", taigaAuth.getUserProfile().getUsername());

        mockResponse = new Response.Builder()
                .code(200)
                .message("Übermensch")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://SuperUltraTester9000.io").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        authResponse.toString()
                ))
                .build();

        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);

        taigaAuth.refreshAuth();
        assertEquals("TestAuthToken", taigaAuth.getAuthToken());
        assertEquals("TestRefreshToken", taigaAuth.getRefreshToken());
        assertEquals(200, taigaAuth.getLastResponseCode());
    }

    @Test
    public void testAuthFailure() throws IOException {
        mockClient = mock(OkHttpClient.class);
        mockMapper = mock(ObjectMapper.class);
        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(500)
                .message("COOKED")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://SuperUltraTester9000.io").build())
                .body(ResponseBody.create(
                                MediaType.get("application/json"),
                                emptyResponseBody.toString()
                ))
                .build();

        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        TaigaAuthenticate taigaAuth = new TaigaAuthenticate(mockClient, mockMapper);
        taigaAuth.authenticate("username", "password");
        assertThrows(IOException.class, () ->  taigaAuth.getAuthToken());
        assertThrows(IOException.class, () ->  taigaAuth.getRefreshToken());
        assertEquals(500, taigaAuth.getLastResponseCode());
    }

}
