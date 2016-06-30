package cz.hanusova.fingerprint_game.model;

import java.util.Date;

/**
 * Created by khanusova on 26.6.2016.
 */
public class UserActivity {

    private Long idUserActivity;

    private Activity activity;

    private Date startTime;

    private String materialUsed;

    private Float materialAmount;

    public Long getIdUserActivity() {
        return idUserActivity;
    }

    public void setIdUserActivity(Long idUserActivity) {
        this.idUserActivity = idUserActivity;
    }

    // public AppUser getAppUser() {
    // return appUser;
    // }
    //
    // public void setAppUser(AppUser appUser) {
    // this.appUser = appUser;
    // }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getMaterialUsed() {
        return materialUsed;
    }

    public void setMaterialUsed(String materialUsed) {
        this.materialUsed = materialUsed;
    }

    public Float getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(Float materialAmount) {
        this.materialAmount = materialAmount;
    }
}
