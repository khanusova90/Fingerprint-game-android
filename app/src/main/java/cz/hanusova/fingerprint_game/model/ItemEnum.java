package cz.hanusova.fingerprint_game.model;

/**
 * Possible item types
 *
 * Created by Kristyna on 28/03/2017.
 */

public enum ItemEnum {


    AXE("AXE"), PICK("PICK"), LADDER("LADDER");

    private String name;

    private ItemEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }


}
