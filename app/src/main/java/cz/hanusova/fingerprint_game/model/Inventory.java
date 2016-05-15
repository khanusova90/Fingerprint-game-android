package cz.hanusova.fingerprint_game.model;

import java.math.BigDecimal;

/**
 * Created by khanusova on 1.4.2016.
 */
public class Inventory {

    private Long idInventory;

    /**
     * Owner of the inventory
     */
    private AppUser user;

    /**
     * {@link Material}
     */
    private String material;

    /**
     * Amount of the material stored in inventory
     */
    private BigDecimal amount;

    public Long getIdInventory() {
        return idInventory;
    }

    public void setIdInventory(Long idInventory) {
        this.idInventory = idInventory;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
