package cz.hanusova.fingerprint_game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Kristyna on 26/03/2017.
 */

public class StagUcitel implements Serializable{

    @JsonProperty("ucitIdno")
    private String ucitIdno;

    @JsonProperty("ucjmenoitIdno")
    private String ucjmenoitIdno;

    @JsonProperty("prijmeni")
    private String prijmeni;

    @JsonProperty("titulPred")
    private String titulPred;

    @JsonProperty("platnost")
    private String platnost;

    @JsonProperty("zamestnanec")
    private String zamestnanec;

    public String getUcitIdno() {
        return ucitIdno;
    }

    public void setUcitIdno(String ucitIdno) {
        this.ucitIdno = ucitIdno;
    }

    public String getUcjmenoitIdno() {
        return ucjmenoitIdno;
    }

    public void setUcjmenoitIdno(String ucjmenoitIdno) {
        this.ucjmenoitIdno = ucjmenoitIdno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getTitulPred() {
        return titulPred;
    }

    public void setTitulPred(String titulPred) {
        this.titulPred = titulPred;
    }

    public String getPlatnost() {
        return platnost;
    }

    public void setPlatnost(String platnost) {
        this.platnost = platnost;
    }

    public String getZamestnanec() {
        return zamestnanec;
    }

    public void setZamestnanec(String zamestnanec) {
        this.zamestnanec = zamestnanec;
    }
}
