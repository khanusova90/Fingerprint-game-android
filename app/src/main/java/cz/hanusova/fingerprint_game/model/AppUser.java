package cz.hanusova.fingerprint_game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User of the application
 *
 * Created by hanuska1 on 9.3.2016.
 */
public class AppUser implements Serializable{

    private Long idUser;

    private String username;

    private String password;

    private String stagname;

    private Character character;

    private List<Inventory> inventory = new ArrayList<>();

    private List<Role> roles = new ArrayList<>();

    private List<UserActivity> activities = new ArrayList<>();

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

    public String getStagname() {
        return stagname;
    }

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

}
