package org.rplbo.nilaimhs.nilaimahasiswa;

public class User {
    private String username;
    private String password;
    private boolean loggedin = false;
    public User(String username, String password, boolean loggedin) {
        this.username = username;
        this.password = password;
        this.loggedin = loggedin;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isLoggedin() {
        return loggedin;
    }
    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }
}
