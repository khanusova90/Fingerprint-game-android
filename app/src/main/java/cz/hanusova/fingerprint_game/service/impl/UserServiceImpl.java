package cz.hanusova.fingerprint_game.service.impl;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.util.List;

import cz.hanusova.fingerprint_game.Preferences_;
import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Inventory;
import cz.hanusova.fingerprint_game.service.UserService;
import cz.hanusova.fingerprint_game.base.utils.Constants;

/**
 * Created by khanusova on 7.12.2016.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserServiceImpl implements UserService {
    private static final String TAG = "UserService";

    @Pref
    Preferences_ preferences;

    @Override
    public Inventory getWorkers() {
        AppUser user = getActualUser();
        List<Inventory> inventorySet = user.getInventory();
        for (Inventory inv : inventorySet) {
            if (inv.getMaterial().getName().equals(Constants.MATERIAL_WORKER)) {
                return inv;
            }
        }
        return null;
    }

    @Override
    public Inventory getInventory(String materialName) {
        AppUser user = getActualUser();
        List<Inventory> inventorySet = user.getInventory();
        for (Inventory inv : inventorySet) {
            if (inv.getMaterial().getName().equals(materialName)) {
                return inv;
            }
        }
        return null;
    }

    @Override
    public AppUser getActualUser() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(preferences.user().get(), AppUser.class);
        } catch (IOException e) {
            Log.e(TAG, "Error occurred while trying to get actual user", e);
            return null;
        }
    }


}
