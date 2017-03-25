/**
 *
 */
package cz.hanusova.fingerprint_game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;


/**
 * @author khanusova
 */
public class Item implements Serializable {

    private Long idItem;

    private String name;

    private ItemType itemType;

    private Integer level;

    private String imgUrl;

    @JsonIgnore
    private boolean selected = false;

    /**
     * @return the idItem
     */
    public Long getIdItem() {
        return idItem;
    }

    /**
     * @param idItem the idItem to set
     */
    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the itemType
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    /**
     * @return the level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return the imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl the imgUrl to set
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
