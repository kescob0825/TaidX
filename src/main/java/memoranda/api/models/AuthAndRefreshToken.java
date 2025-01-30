package memoranda.api.models;

public class AuthAndRefreshToken {
    private String authToken;
    private String refreshToken;

    public AuthAndRefreshToken(String authToken, String refreshToken) {
        this.authToken = authToken;
        this.refreshToken = refreshToken;
    }

    public String getAuthToken() {
        return authToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isAuthEmpty() {
        return authToken == null || authToken.isEmpty();
    }
    public boolean isRefreshEmpty() {
        return authToken == null || refreshToken.isEmpty();
    }



}
