package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User of the application
 * <p>
 * Created by hanuska1 on 9.3.2016.
 */
public class AppUser implements Serializable {

    private Long idUser;

    private String username;

    private String password;

    @Deprecated
    private String stagname;

    private Character character;

    private List<Inventory> inventory = new ArrayList<>();

    private List<Role> roles = new ArrayList<>();

    private List<UserActivity> activities = new ArrayList<>();

    private List<Place> places = new ArrayList<>();

    private List<Item> items = new ArrayList<>();

    private int placeProgress;
    private int level;
    private int levelProgress;


    //FIXME: to neni metoda uzivatele!
    public List<Place> getPlacesByFloor(int currentFloor) {
        // TODO: maybe fix this shit with a filter
        List<Place> places = new ArrayList<>();
        for (Place p : this.getPlaces()) {
            if (p.getFloor() == currentFloor) {
                places.add(p);
            }
        }
        return places.size() > 0 ? places : null;
    }

    /*
     * Getters and setters
     */
    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Deprecated
    public String getStagname() {
        return stagname;
    }

    @Deprecated
    public void setStagname(String stagname) {
        this.stagname = stagname;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<UserActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<UserActivity> activities) {
        this.activities = activities;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getPlaceProgress() {
        return placeProgress;
    }

    public void setPlaceProgress(int placeProgress) {
        this.placeProgress = placeProgress;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevelProgress() {
        return levelProgress;
    }

    public void setLevelProgress(int levelProgress) {
        this.levelProgress = levelProgress;
    }
}
