package com.example.administrator.ifindyou;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-19.
 */

public class UserListView {
    private Context context;

    public UserListView(Context context) {
        this.context = context;
    }

    public ArrayList<User> getJsonData(String jsonData) {

        ArrayList<User> userList = new ArrayList<User>();

        int userNumber;
        String id;
        String name;
        String rank;
        String position;
        String unit;
        String content;
        int phoneNumber;
        String status;
        String imgName;

        FileDownloader fileDownloader = new FileDownloader(context);

        try {
            JSONArray jArr = new JSONArray(jsonData);

            for (int i = 0; i < jArr.length(); ++i) {
                JSONObject jObj = jArr.getJSONObject(i);

                userNumber = jObj.getInt("UserNumber");
                id = jObj.getString("Id");
                name = jObj.getString("Name");
                rank = jObj.getString("Rank");
                position = jObj.getString("Position");
                unit = jObj.getString("Unit");
                content = jObj.getString("Content");
                phoneNumber = jObj.getInt("PhoneNumber");
                status = jObj.getString("Status");
                imgName = jObj.getString("ImgName");

                userList.add(new User(userNumber, id, name, rank, position, unit, content, phoneNumber, status, imgName));
                fileDownloader.downFile(context.getResources().getString(R.string.url)+"image/" + imgName, imgName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userList;
    }
}
