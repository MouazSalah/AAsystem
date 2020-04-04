package com.example.aasystem.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.aasystem.user.model.UserCredential;
import com.google.gson.Gson;

public class SharedPrefMethods
{
    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SharedPrefMethods(Context con)
    {
        this.context = con;
        pref = con.getSharedPreferences("AASystem", Context.MODE_PRIVATE);
        editor = pref.edit();
    }


    public void saveUserData(UserCredential userData)
    {
        Gson gson = new Gson();
        String json = gson.toJson(userData); // myObject - instance of MyObject
        editor.putString("user_credential", json);
        editor.commit();
    }

    public UserCredential getUserData()
    {
        UserCredential userData = new UserCredential();

        Gson gson = new Gson();
        String json = pref.getString("user_credential", null);
        userData = gson.fromJson(json, UserCredential.class);

        return userData;
    }

    public void deleteUserData()
    {
        editor.remove("user_credential");
        editor.commit();
    }


}
