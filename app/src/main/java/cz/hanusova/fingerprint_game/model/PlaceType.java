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
     * Image URL (relative, see {@link cz.hanusova.fingerprint_game.utils.Constants#IMG_URL_BASE}
     */
    private String imgUrl;

    /**
     * Activity available in this type of place
     */
    private ActivityEnum activity;
    /*
     * Getters and setters
     */
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

    public ActivityEnum getActivity() {
        return activity;
    }

    public void setActivity(ActivityEnum activity) {
        this.activity = activity;
    }
}
