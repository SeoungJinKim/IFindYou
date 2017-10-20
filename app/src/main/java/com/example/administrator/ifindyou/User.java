package com.example.administrator.ifindyou;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-19.
 */

public class User {

    private User user;
    private int userNumber;
    private String id;
    private String name;
    private String rank;
    private String position;
    private String unit;
    private String content;
    private int phoneNumber;
    private String status;
    private String imgName;
    private Context context;

    public int getUserNumber() {
        return userNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public String getPosition() {
        return position;
    }

    public String getUnit() {
        return unit;
    }

    public String getContent() {
        return content;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getImgName() {
        return imgName;
    }

    public User(int userNumber, String id, String name, String rank, String position, String unit, String content, int phoneNumber, String status, String imgName) {
        this.userNumber = userNumber;
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.position = position;
        this.unit = unit;
        this.content = content;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.imgName = imgName;
    }
}
