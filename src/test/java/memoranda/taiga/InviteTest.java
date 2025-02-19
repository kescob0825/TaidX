package memoranda.taiga;

import com.fasterxml.jackson.databind.ObjectMapper;
import memoranda.api.modules.TaigaInvite;
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

public class InviteTest {
    private static JSONArray mockInviteData;
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
        mockInviteData = new JSONArray(new String(
                Files.readAllBytes(Paths.get("src/test/resources/invite_data.json"))));
        emptyResponseBody = new JSONObject();
    }

    /**
     * Test case for successful member invitation.
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void inviteMemberPass() throws IOException {
        String token = "Dummy token";
        JSONObject mockInviteJSON = mockInviteData.getJSONObject(0);
        mockClient = mock(OkHttpClient.class);

        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        mockInviteData.toString()
                ))
                .build();

        TaigaInvite inviteMember = new TaigaInvite(mockClient);
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(requestCaptor.capture())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        inviteMember.inviteMember(token, mockInviteJSON);


        Request capturedRequest = requestCaptor.getValue();
        assertEquals("POST", capturedRequest.method());
        assertEquals(200, inviteMember.getLastResponseCode());
    }

    /**
     * Test case for failed member invitation.
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void inviteMemberFail() throws IOException {
        String token = "Dummy token";
        JSONObject mockInviteJSON = mockInviteData.getJSONObject(1);
        mockClient = mock(OkHttpClient.class);

        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(400)
                .message("COOKED")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        mockInviteData.toString()
                ))
                .build();

        TaigaInvite inviteMember = new TaigaInvite(mockClient);
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(requestCaptor.capture())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        inviteMember.inviteMember(token, mockInviteJSON);

        Request capturedRequest = requestCaptor.getValue();
        assertEquals("POST", capturedRequest.method());
        assertEquals(400, inviteMember.getLastResponseCode());
    }

    /**
     * Test case for successful members invitation.
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void inviteMembersPass() throws IOException {
        String token = "Dummy token";
        JSONObject mockInviteJSON = mockInviteData.getJSONObject(1);
        mockClient = mock(OkHttpClient.class);

        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(200)
                .message("OK")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        mockInviteData.toString()
                ))
                .build();

        TaigaInvite inviteMember = new TaigaInvite(mockClient);
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(requestCaptor.capture())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        inviteMember.inviteMember(token, mockInviteJSON);

        Request capturedRequest = requestCaptor.getValue();
        assertEquals("POST", capturedRequest.method());
        assertEquals(200, inviteMember.getLastResponseCode());
    }

    /**
     * Test case for failed members invitation.
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void inviteMembersFail() throws IOException {
        String token = "Dummy token";
        JSONObject mockInviteJSON = mockInviteData.getJSONObject(1);
        mockClient = mock(OkHttpClient.class);

        Call mockCall = mock(Call.class);

        Response mockResponse = new Response.Builder()
                .code(400)
                .message("COOKED")
                .protocol(Protocol.HTTP_1_1)
                .request(new Request.Builder().url("https://api.taiga.io/api/v1/").build())
                .body(ResponseBody.create(
                        MediaType.get("application/json"),
                        mockInviteData.toString()
                ))
                .build();

        TaigaInvite inviteMember = new TaigaInvite(mockClient);
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockClient.newCall(requestCaptor.capture())).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        inviteMember.inviteMember(token, mockInviteJSON);

        Request capturedRequest = requestCaptor.getValue();
        assertEquals("POST", capturedRequest.method());
        assertEquals(400, inviteMember.getLastResponseCode());
    }
}
