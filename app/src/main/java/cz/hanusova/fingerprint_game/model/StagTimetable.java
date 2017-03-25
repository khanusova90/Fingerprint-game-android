package cz.hanusova.fingerprint_game.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Kristyna on 25/03/2017.
 */

public class StagTimetable {

    @JsonProperty("rozvrhovaAkce")
    private List<String> timetable;

    public List<String> getUserNumbers() {
        return timetable;
    }

    public void setUserNumbers(List<String> userNumbers) {
        this.timetable = userNumbers;
    }
}
