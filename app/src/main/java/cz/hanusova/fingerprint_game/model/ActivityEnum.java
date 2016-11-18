package cz.hanusova.fingerprint_game.model;

/**
 * Created by khanusova on 8.11.2016.
 */
public enum ActivityEnum {

    MINE("MINE");

    private String name;

    private ActivityEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

//    public String getKey() {
//        return EnumTranslator.getMessageKey(this);
//    }
}
