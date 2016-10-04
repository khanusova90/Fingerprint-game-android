package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;

/**
 * Class represents an activity that can be done on some place
 *
 * Created by khanusova on 11.5.2016.
 */
public class Activity implements Serializable {

    private Long idActivity;
    private String name;
    private Material material;

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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
