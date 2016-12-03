package cz.hanusova.fingerprint_game.model;

/**
 * Created by khanusova on 29.4.2016.
 */
public class Material {

    private Long idMaterial;

    /**
     * Name of the material
     */
    private String name;

    /**
     * Default amount of material for new users
     */
    private Integer defaultAmount;

    public Long getIdMaterial() {
        return idMaterial;
    }

    /*
     * Getters and setters
     */
    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(Integer defaultAmount) {
        this.defaultAmount = defaultAmount;
    }
}
