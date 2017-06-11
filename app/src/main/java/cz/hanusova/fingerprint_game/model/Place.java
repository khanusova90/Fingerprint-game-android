package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;

/**
 * Created by khanusova on 27.4.2016.
 * <p>
 * Class representing place in the game
 */
public class Place implements Serializable {

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
     * Material that could be found on the place
     */
    private Material material;

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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Place place = (Place) o;

        if (idPlace != null ? !idPlace.equals(place.idPlace) : place.idPlace != null) return false;
        return code != null ? code.equals(place.code) : place.code == null;

    }

    @Override
    public int hashCode() {
        int result = idPlace != null ? idPlace.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
