package client_app.classes;

import java.io.Serializable;

public class LogginData implements Serializable {
    private String nick;
    private String password;

    public LogginData(String Nick, String Password) {
        this.nick=Nick;
        this.password=Password;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }
}
