package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by khanusova on 1.4.2016.
 */
public class Inventory implements Serializable{

    private Long idInventory;

    /**
     * {@link Material}
     */
    private Material material;

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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
