package cz.hanusova.fingerprint_game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kristyna on 25/03/2017.
 */

public class StagTimetable implements Serializable {

    @JsonProperty("rozvrhovaAkce")
    private List<TimetableActionInfo> timetableActionInfos;

    public List<TimetableActionInfo> getTimetableActionInfos() {
        return timetableActionInfos;
    }

    public void setTimetableActionInfos(List<TimetableActionInfo> timetableActionInfos) {
        this.timetableActionInfos = timetableActionInfos;
    }
}
