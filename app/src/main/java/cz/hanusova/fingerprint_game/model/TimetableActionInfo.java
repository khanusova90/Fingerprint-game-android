package cz.hanusova.fingerprint_game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Kristyna on 26/03/2017.
 */
public class TimetableActionInfo implements Serializable {

    /*  "roakIdno": 361630,
        "nazev": "Smart přístupy k tvorbě IS a aplikací",
        "katedra": "KIT",
        "predmet": "SMAP",
        "statut": "A",
        "ucitIdno": 246033,
        "ucitel": {
            "ucitIdno": 246033,
            "jmeno": "Jan",
            "prijmeni": "Dvořák",
            "titulPred": "Ing.",
            "platnost": "A",
            "zamestnanec": "A"
        },
        "rok": "2016",
        "budova": "J",
        "mistnost": "J23",
        "kapacitaMistnosti": 20,
        "planObsazeni": 20,
        "obsazeni": 12,
        "typAkce": "Cvičení",
        "typAkceZkr": "Cv",
        "semestr": "ZS",
        "platnost": "A",
        "den": "Pondělí",
        "denZkr": "Po",
        "hodinaOd": null,
        "hodinaDo": null,
        "hodinaSkutOd": {
            "value": "17:25"
        },
        "hodinaSkutDo": {
            "value": "19:00"
        },
        "tydenOd": 39,
        "tydenDo": 51,
        "tyden": "Jiný",
        "tydenZkr": "J",
        "grupIdno": null,
        "jeNadrazena": "N",
        "maNadrazenou": "N",
        "kontakt": null,
        "krouzky": null,
        "casovaRada": null,
        "datum": null,
        "datumOd": {
            "value": "26.9.2016"
        },
        "datumDo": {
            "value": "19.12.2016"
        },
        "druhAkce": "R",
        "vsichniUciteleUcitIdno": "246033",
        "vsichniUciteleJmenaTituly": "Ing. Jan Dvořák",
        "vsichniUcitelePrijmeni": "Dvořák",
        "referencedIdno": 361630,
        "poznamkaRozvrhare": null,
        "nekonaSe": null,
        "owner": "IZAKOIV1FS",
        "zakazaneAkce": null*/

    @JsonProperty("roakIdno")
    private Integer id;

    @JsonProperty("nazev")
    private String name;

    @JsonProperty("katedra")
    private String catedry;

    @JsonProperty("predmet")
    private String subject;

    @JsonProperty("statut")
    private String statut;

    @JsonProperty("ucitIdno")
    private String ucitIdno;

    @JsonProperty("ucitel")
    private List<StagUcitel> teacher;

    @JsonProperty("rok")
    private String year;

    @JsonProperty("budova")
    private String building;

    @JsonProperty("mistnost")
    private String room;

    @JsonProperty("kapacitaMistnosti")
    private Integer capacity;

    @JsonProperty("obsazeni")
    private Integer peopleCount;

    @JsonProperty("planObsazeni")
    private Integer plan;

    @JsonProperty("typAkce")
    private String typeAction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatedry() {
        return catedry;
    }

    public void setCatedry(String catedry) {
        this.catedry = catedry;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getUcitIdno() {
        return ucitIdno;
    }

    public void setUcitIdno(String ucitIdno) {
        this.ucitIdno = ucitIdno;
    }

    public List<StagUcitel> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<StagUcitel> teacher) {
        this.teacher = teacher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }
}
