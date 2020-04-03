package com.example.aasystem.user;

public class UserAcountInfo {
// هذه الدالة لوضع معلومات اليوسر في الداتا بيس
    public UserAcountInfo()
    {

    }

    public String e_mail, user_Id, user_name, phone_Num,passwd,figerPrint,stringdate;

    public UserAcountInfo(String e_mail,String user_Id,String user_name,String phone_Num,String passwd ,String figerPrint,String stringdate)
    {
        this.e_mail = e_mail;
        this.user_Id = user_Id;
        this.user_name = user_name;
        this.phone_Num = phone_Num;
        this.passwd = passwd;
        this.figerPrint = figerPrint;
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
