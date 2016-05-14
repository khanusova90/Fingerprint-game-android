package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;

/**
 * Created by khanusova on 11.5.2016.
 */
public class Activity implements Serializable{
    private Long idActivity;
    private String name;

    public Long getIdActivity() {
        return idActivity;
    }
    public void setIdActivity(Long idActivity) {
        this.idActivity = idActivity;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
