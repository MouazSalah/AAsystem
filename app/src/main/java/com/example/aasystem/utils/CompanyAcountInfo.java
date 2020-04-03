package com.example.aasystem.utils;

import com.google.firebase.database.DataSnapshot;

public class CompanyAcountInfo {

    public CompanyAcountInfo() {

    }

    public String  email,password,CompanyName,Phone,Username,lat,lon,From,To,OnTime;


    public CompanyAcountInfo(String email, String password,String CompanyName,String Phone,String Username,String lat,String lon,String From,String To,String OnTime) {
        this.email = email;
        this.password = password;
        this.CompanyName = CompanyName;
        this.Phone = Phone;
        this.Username = Username;
        this.lat = lat;
        this.lon = lon;
        this.From = From;
        this.To = To;
        this.OnTime = OnTime;
    }

  

// هذا الكلاس لوضع البيانات في الداتا بيس


}


