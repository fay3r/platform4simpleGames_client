package client_app.classes.dtoPlatform;

import java.io.Serializable;

public class ChangePasswordDto implements Serializable {
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

    public ChangePasswordDto(String nick, String password, String newPassword) {
        this.nick = nick;
        this.password = password;
        this.newPassword = newPassword;
    }
}
