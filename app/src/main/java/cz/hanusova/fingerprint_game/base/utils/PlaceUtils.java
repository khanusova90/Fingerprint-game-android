package cz.hanusova.fingerprint_game.base.utils;

import cz.hanusova.fingerprint_game.model.Place;

/**
 * Created by khanusova on 17/06/2017.
 */

public final class PlaceUtils {
    private PlaceUtils() {
    }

    /**
     * Gets name of icon for given place
     *
     * @param place {@link Place} to find icon to
     * @return name of material icon in case that some material is present on place. Otherwise returns name of image of place type
     */
    public static String getIconName(Place place) {
        if (place == null) {
            return null;
        }
        return place.getMaterial() != null ? place.getMaterial().getIconName() : place.getPlaceType().getImgUrl();
    }
}
