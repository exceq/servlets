package models;

public class UserProfile {
    private final String login;
    private final String password;
    private final String email;

    public UserProfile(String login, String pass, String email) {
        this.login = login;
        this.password = pass;
        this.email = email;
    }

    public UserProfile(String login) {
        this.login = login;
        this.password = login;
        this.email = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
