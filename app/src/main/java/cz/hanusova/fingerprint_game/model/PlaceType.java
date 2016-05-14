package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by khanusova on 11.5.2016.
 */
public class PlaceType implements Serializable{

    private Long idPlaceType;
    private String placeType;
    private String description;
    private String imgUrl;
    private List<Activity> activities;

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
