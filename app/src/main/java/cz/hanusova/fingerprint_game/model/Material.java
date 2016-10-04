package cz.hanusova.fingerprint_game.model;

/**
 * Created by khanusova on 29.4.2016.
 */
public enum Material {

    GOLD("GOLD"), FOOD("FOOD"), WOOD("WOOD"), STONE("STONE"), WORKER("WORKER");

    private String name;

    private Material(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
