package cz.hanusova.fingerprint_game.scene.scan.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cz.hanusova.fingerprint_game.scene.scan.event.UserMovedEvent;

/**
 * Created by khanusova on 16/07/2017.
 */

public class ActivityRecognitionIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ActivityRecognitionIntentService() {
        super("ActivityRecognitionIS");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities() );
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> activities){
        for (DetectedActivity activity : activities) {
            System.out.println("ACTVITIY: " + activity.getType());
            System.out.println("CONFIDENCE: " + activity.getConfidence());
            if (activity.getConfidence() > 75) {
                if (activity.getType() != DetectedActivity.STILL) {
                    EventBus.getDefault().post(new UserMovedEvent());
//                    return;
                } else {
                //TODO: stop scan
                }
            }
        }
        System.out.println("=================================================");
    }
}
