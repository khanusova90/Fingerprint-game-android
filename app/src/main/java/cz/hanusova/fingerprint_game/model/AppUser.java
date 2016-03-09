package cz.hanusova.fingerprint_game.model;

/**
 * Created by hanuska1 on 9.3.2016.
 */
public class AppUser {

    private Long idUser;

    private String username;
    private String stagname;
    private String password;

    //TODO: userRoles, inventory

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

    public String getStagname() {
        return stagname;
    }

    public void setStagname(String stagname) {
        this.stagname = stagname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
