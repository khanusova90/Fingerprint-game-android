package cz.hanusova.fingerprint_game.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by khanusova on 26.6.2016.
 */
public class UserActivity {

    private Long idUserActivity;

    private ActivityEnum activity;

    private Material material;

    private Float materialAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stopTime;

    private Place place;

    public Long getIdUserActivity() {
        return idUserActivity;
    }

    public void setIdUserActivity(Long idUserActivity) {
        this.idUserActivity = idUserActivity;
    }

    public ActivityEnum getActivity() {
        return activity;
    }

    public void setActivity(ActivityEnum activity) {
        this.activity = activity;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


    public Float getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(Float materialAmount) {
        this.materialAmount = materialAmount;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }
}
