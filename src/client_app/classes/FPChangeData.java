package client_app.classes;


import java.io.Serializable;

public class FPChangeData implements Serializable {
    private String nick;
    private String answer;
    private String newPassword;

    public String getNick() {
        return nick;
    }

    public String getAnswer() {
        return answer;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
