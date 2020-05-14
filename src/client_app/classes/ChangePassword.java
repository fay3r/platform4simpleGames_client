package client_app.classes;

import java.io.Serializable;

public class ChangePassword implements Serializable {
    private String nick;
    private String password;
    private String newPassword;

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
