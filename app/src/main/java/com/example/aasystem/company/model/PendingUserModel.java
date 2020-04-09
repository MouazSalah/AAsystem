package com.example.aasystem.company.model;

public class PendingUserModel
{
    String key, name, email, password;

    public PendingUserModel() {
    }

    public PendingUserModel(String key, String name, String email, String password) {
        this.key = key;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
