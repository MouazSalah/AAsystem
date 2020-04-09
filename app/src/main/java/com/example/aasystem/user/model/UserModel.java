package com.example.aasystem.user.model;

public class UserModel
{
    String name, attendAt, leftat, check;

    public UserModel() {
    }

    public UserModel(String name, String attendAt, String leftat, String check) {
        this.name = name;
        this.attendAt = attendAt;
        this.leftat = leftat;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttendAt() {
        return attendAt;
    }

    public void setAttendAt(String attendAt) {
        this.attendAt = attendAt;
    }

    public String getLeftat() {
        return leftat;
    }

    public void setLeftat(String leftat) {
        this.leftat = leftat;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}
