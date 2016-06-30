package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * User of the application
 *
 * Created by hanuska1 on 9.3.2016.
 */
public class AppUser implements Serializable{

    private Long idUser;

    /**
     * Username - should be unique
     */
    private String username;

    /**
     * Username in STAG
     */
    private String stagname;

    /**
     * Password
     */
    private String password;

    /**
     * User's inventory
     */
    private Set<Inventory> inventory = new HashSet<>();

    /**
     * Set of user's roles
     */
    private Set<String> userRoles = new HashSet<>();

    private Set<UserActivity> activities = new HashSet<>();

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

    public String getStagname() {
        return stagname;
    }

    public void setStagname(String stagname) {
        this.stagname = stagname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(Set<Inventory> inventory) {
        this.inventory = inventory;
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<UserActivity> getActivities() {
        return activities;
    }

    public void setActivities(Set<UserActivity> activities) {
        this.activities = activities;
    }
}
