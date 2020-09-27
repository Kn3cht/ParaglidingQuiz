package com.example.paraglidingquiz.Model;

public class User {
    private String username;
    private String password;
    private String error;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        this.error = username.equals("") ? "Benutzername leer" : password.equals("") ? "Passwort leer" : "";
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
