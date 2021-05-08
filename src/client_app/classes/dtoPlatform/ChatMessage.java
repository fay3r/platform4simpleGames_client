package client_app.classes.dtoPlatform;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private String nick;
    private String text;
    private String data;

    public ChatMessage(String nick, String text, String data) {
        this.nick = nick;
        this.text = text;
        this.data = data;
    }

    @Override
    public String toString() {
        return ""+ nick + "\t" + data + "\n" + text + "\n";
    }
}
