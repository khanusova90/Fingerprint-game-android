package cz.hanusova.fingerprint_game.model;

/**
 * Created by khanusova on 29.4.2016.
 */
public class Resource {

    private Long idResources;

    /**
     * X coordinates
     */
    private Integer x;

    /**
     * Y coordinates
     */
    private Integer y;

    /**
     * Place with resource
     */
    private Place place;

    /**
     * {@link Material} available here
     */
    private Material material;

    /*
     * Getters and setters
     */
    public Long getIdResources() {
        return idResources;
    }

    public void setIdResources(Long idResources) {
        this.idResources = idResources;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
