package reditt.dto;

public class AuthenticationResponse {

    private String token;
    private String login;

    public AuthenticationResponse() { }

    public AuthenticationResponse(String token, String login) {
        this.token = token;
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
