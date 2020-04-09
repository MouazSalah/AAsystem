package com.example.aasystem.user.model;

public class UserAcountInfo
{
// هذه الدالة لوضع معلومات اليوسر في الداتا بيس
    public UserAcountInfo()
    {

    }

    public String  key, e_mail, user_Id, user_name, phone_Num,passwd,figerPrint,stringdate;

    public UserAcountInfo(String key, String e_mail, String user_Id, String user_name, String phone_Num, String passwd, String figerPrint, String stringdate) {
        this.key = key;
        this.e_mail = e_mail;
        this.user_Id = user_Id;
        this.user_name = user_name;
        this.phone_Num = phone_Num;
        this.passwd = passwd;
        this.figerPrint = figerPrint;
        this.stringdate = stringdate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFigerPrint() {
        return figerPrint;
    }

    public void setFigerPrint(String figerPrint) {
        this.figerPrint = figerPrint;
    }

    public String getStringdate() {
        return stringdate;
    }

    public void setStringdate(String stringdate) {
        this.stringdate = stringdate;
    }

    public String getPhone_Num() {
        return phone_Num;
    }

    public void setPhone_Num(String phone_Num) {
        this.phone_Num = phone_Num;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }
}
