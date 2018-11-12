package fall2018.csc2017.gamecentre;

import java.io.Serializable;

public class User implements Serializable {

    /**
     * Username of the user.
     */
    private String username;

    /**
     * Password of the user.
     */
    private String password;

    /**
     * Initializes new instance of user.
     *
     * @param user username of the user
     * @param pass password of the user
     */
    User(String user, String pass) {
        this.username = user;
        this.password = pass;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Checks if input password is the correct password.
     *
     * @param password the input password
     * @return boolean whether the password is correct
     */
    boolean authenticate(String password) {
        return password.equals(this.password);
    }

    @Override
    public String toString() {
        return "username= " + username;
    }
}
