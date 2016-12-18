package cz.hanusova.fingerprint_game.service;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Inventory;

/**
 * Created by khanusova on 7.12.2016.
 *
 * Service for handling {@link AppUser}
 */
public interface UserService {

    /**
     * Gets workers from user's inventory. If it does not find any, returns <code>null</code>
     * @return {@link Inventory} with workers
     */
    Inventory getWorkers();

    /**
     *
     * @return Currently logged in {@link AppUser}
     */
    AppUser getActualUser();
}
