package memoranda.api.models;

public class AuthAndRefreshToken {
    private String authToken;
    private String refreshToken;
    public static boolean isLoggedIn = false;

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

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void logout() {
        System.out.println("User is logged in: " + isLoggedIn);
        isLoggedIn = false;
        authToken = null;
        refreshToken = null;
        System.out.println("Logging Out");
        System.out.println("User is logged out: " + !isLoggedIn);
    }

    public boolean isAuthEmpty() {
        return authToken == null || authToken.isEmpty();
    }
    public boolean isRefreshEmpty() {
        return authToken == null || refreshToken.isEmpty();
    }



}
