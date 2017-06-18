package cz.hanusova.fingerprint_game.base.service;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Inventory;

/**
 * Created by khanusova on 7.12.2016.
 * <p>
 * Service for handling {@link AppUser}
 */
public interface UserService {

    /**
     * Gets workers from user's inventory. If it does not find any, returns <code>null</code>
     *
     * @return {@link Inventory} with workers
     */
    Inventory getWorkers();

    /**
     * Gets inventory from user's inventory list. If it does not find any, returns <code>null</code>
     *
     * @param materialName Name of material in inventory
     * @return {@link Inventory} with given material name or null
     */
    Inventory getInventory(String materialName);

    /**
     * @return Currently logged in {@link AppUser}
     */
    AppUser getActualUser();

    void updateUser(AppUser user);
}
