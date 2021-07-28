package payload.response;

public class LogInResponse {
    private  String username;
    private  String accessToken;

    public LogInResponse(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }
}
