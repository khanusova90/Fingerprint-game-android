package cz.hanusova.fingerprint_game.model.fingerprint;

import java.io.Serializable;

/**
 * Created by khanusova on 10.1.2017.
 */
public class BleScan implements Serializable{

    //FIXME: REFACTOR NEEDED!
    int rssi;
    String uuid = "";
    int major, minor;
    String address = "";
    long time;

    public BleScan() {
    }

    @Override
    public String toString() {
        return "{" +
                "\"rssi\":" + rssi +
                ",\"uuid\":" + "\"" + uuid + "\"" +
                ",\"major\":" + major +
                ",\"minor\":" + minor +
                ",\"address\":" + "\"" + address + "\"" +
                ",\"time\":" + time +
                "}";
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getRssi() {
        return rssi;
    }

    public String getUuid() {
        return uuid;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public String getAddress() {
        return address;
    }

    public long getTime() {
        return time;
    }
}
