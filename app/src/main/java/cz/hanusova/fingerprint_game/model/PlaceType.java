package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by khanusova on 11.5.2016.
 */
public class PlaceType implements Serializable{

    private Long idPlaceType;

    /**
     * Name of place type
     */
    private String placeType;

    /**
     * Description
     */
    private String description;

    /**
     * Image URL (relative, see {@link cz.hanusova.fingerprint_game.utils.Constants#IMG_URL_BASE}
     */
    private String imgUrl;

    /**
     * List of {@link Activity} that are possible to do on this type of place
     */
    private List<Activity> activities;

    /*
     * Getters and setters
     */
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getIdPlaceType() {
        return idPlaceType;
    }
    public void setIdPlaceType(Long idPlaceType) {
        this.idPlaceType = idPlaceType;
    }
    public String getPlaceType() {
        return placeType;
    }
    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
