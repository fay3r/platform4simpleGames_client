package client_app.classes.dtoPlatform;

import java.io.Serializable;

public class AccountScoresDto implements Serializable {
    public String nick;
    public Integer tttGamesWon;
    public Integer shipsGamesWon;
    public Integer tttGamesLost;
    public Integer shipsGamesLost;
    public Integer rpsGamesWon;
    public Integer rpsGamesLost;

    public AccountScoresDto(String nick, Integer tttGamesWon, Integer shipsGamesWon, Integer tttGamesLost, Integer shipsGamesLost, Integer rpsGamesWon, Integer rpsGamesLost) {
        this.nick = nick;
        this.tttGamesWon = tttGamesWon;
        this.shipsGamesWon = shipsGamesWon;
        this.tttGamesLost = tttGamesLost;
        this.shipsGamesLost = shipsGamesLost;
        this.rpsGamesWon = rpsGamesWon;
        this.rpsGamesLost = rpsGamesLost;
    }
}


