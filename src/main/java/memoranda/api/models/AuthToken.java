package memoranda.api.models;

public class AuthToken {
    private String token;

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public boolean isEmpty() {
        return token == null || token.isEmpty();
    }
}
