package cz.hanusova.fingerprint_game.model;

/**
 * Created by khanusova on 1.7.2017.
 */

public class Ranking {

    private int ranking;
    private String username;
    private int xp;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
