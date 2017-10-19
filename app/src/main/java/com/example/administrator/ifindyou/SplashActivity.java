package com.example.administrator.ifindyou;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Administrator on 2017-10-18.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent;
        if(loadId() == true)
            intent = new Intent(this,MainActivity.class);
        else
            intent = new Intent(this, LoginActivity.class);

        startActivity(intent);

        finish();
    }

    private boolean loadId(){
        SharedPreferences pref = getSharedPreferences("PrefIFindYou", Activity.MODE_PRIVATE);
        String temp = pref.getString("User_Id","");
        if(!temp.equals("")) return true;
        else return false;
    }
}