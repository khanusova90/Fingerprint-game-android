package cz.hanusova.fingerprint_game.model;

/**
 * Created by khanusova on 29.11.2016.
 */
@Deprecated
public enum MaterialEnum {

    GOLD("GOLD"), FOOD("FOOD"), WOOD("WOOD"), STONE("STONE"), WORKER("WORKER");

    private String name;

    private MaterialEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
