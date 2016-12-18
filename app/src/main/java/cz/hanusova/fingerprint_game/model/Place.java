package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by khanusova on 27.4.2016.
 *
 * Class representing place in the game
 */
public class Place implements Serializable{

    private Long idPlace;

    /**
     * Code
     */
    private String code;

    /**
     * Name
     */
    private String name;

    /**
     * Place description
     */
    private String description;

    /**
     * Type of place
     */
    private PlaceType placeType;

    /**
     * Floor
     */
    private Integer floor;

    /**
     * X coordinates
     */
    private Integer xCoord;

    /**
     * Y coordinates
     */
    private Integer yCoord;

    /*
     * Getters and setters
     */
    public Long getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(Long idPlace) {
        this.idPlace = idPlace;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getxCoord() {
        return xCoord;
    }

    public void setxCoord(Integer xCoord) {
        this.xCoord = xCoord;
    }

    public Integer getyCoord() {
        return yCoord;
    }

    public void setyCoord(Integer yCoord) {
        this.yCoord = yCoord;
    }
}
