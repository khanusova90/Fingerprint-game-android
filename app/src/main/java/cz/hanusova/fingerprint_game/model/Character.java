package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;

/**
 * Created by Kachna on 4.7.2016.
 */
public class Character implements Serializable {

    private Long idCharacter;

    private int charisma = 0;

    private int power = 0;

    private int xp = 0;

    public Long getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(Long idCharacter) {
        this.idCharacter = idCharacter;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
