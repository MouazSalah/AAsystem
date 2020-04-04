package com.example.aasystem;

public class FingerPrintModel
{
    String date, id, name, attend, leave, first_check, second_check, status;
    int year, month, day;

    public FingerPrintModel() {
    }

    public FingerPrintModel(String date, String id, String name, String attend, String leave,
                            String first_check, String second_check, String status, int year, int month, int day) {
        this.date = date;
        this.id = id;
        this.name = name;
        this.attend = attend;
        this.leave = leave;
        this.first_check = first_check;
        this.second_check = second_check;
        this.status = status;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getFirst_check() {
        return first_check;
    }

    public void setFirst_check(String first_check) {
        this.first_check = first_check;
    }

    public String getSecond_check() {
        return second_check;
    }

    public void setSecond_check(String second_check) {
        this.second_check = second_check;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
