package collection;

import java.io.Serializable;

public class UserToken implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String login;
    private final String password;

    public UserToken(String login, String hashPassword){
        this.login = login;
        this.password = hashPassword;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
