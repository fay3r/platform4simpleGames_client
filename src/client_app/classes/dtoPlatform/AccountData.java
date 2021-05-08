package client_app.classes.dtoPlatform;

import java.io.Serializable;


public class AccountData implements Serializable {
    private String nick;
    private String password;
    private String question;
    private String answer;
    public Integer tttGamesWon;
    public Integer tttGamesLost;
    public Integer shipsGamesWon;
    public Integer shipsGamesLost;
    public Integer rpsGamesWon;
    public Integer rpsGamesLost;

    //rejestracja
    public AccountData(String nick, String password, String question, String answer) {
        this.nick = nick;
        this.password = password;
        this.question = question;
        this.answer = answer;
        this.tttGamesWon = 0;
        this.tttGamesLost = 0;
        this.shipsGamesWon = 0;
        this.shipsGamesLost = 0;
        this.rpsGamesLost=0;
        this.rpsGamesWon=0;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "AccountData{" +
                "nick='" + nick + '\'' +
                ", password='" + password + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", tttGamesWon=" + tttGamesWon +
                ", tttGamesLost=" + tttGamesLost +
                ", shipsGamesWon=" + shipsGamesWon +
                ", shipsGamesLost=" + shipsGamesLost +
                ", rpsGamesWon=" + rpsGamesWon +
                ", rpsGamesLost=" + rpsGamesLost +
                '}';
    }
}
