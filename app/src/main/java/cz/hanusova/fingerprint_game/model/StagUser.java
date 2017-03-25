package cz.hanusova.fingerprint_game.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kristyna on 25/03/2017.
 */
public class StagUser implements Serializable {

    @JsonProperty("osCislo")
    private List<String> userNumbers;

    public List<String> getUserNumbers() {
        return userNumbers;
    }

    public void setUserNumbers(List<String> userNumbers) {
        this.userNumbers = userNumbers;
    }

}
