package cz.hanusova.fingerprint_game.model;

import java.util.Date;

/**
 * Created by khanusova on 26.6.2016.
 */
public class UserActivity {

    private Long idUserActivity;

    private ActivityEnum activity;

    private Material material;

    private Float materialAmount;

    private Date startTime;

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
}
