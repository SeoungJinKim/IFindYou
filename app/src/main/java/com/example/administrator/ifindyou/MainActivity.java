package com.example.administrator.ifindyou;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton iconSetting, iconNoti;
    private SharedPreferences pref;
    private ArrayList<User> userList;
    private ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        pref = getSharedPreferences("PrefIFindYou", Activity.MODE_PRIVATE);

        iconSetting = (ImageButton) findViewById(R.id.btn_nav_setting);
        iconSetting.setOnClickListener(this);
        iconNoti = (ImageButton) findViewById(R.id.btn_nav_notification);
        iconNoti.setOnClickListener(this);

        userListView = (ListView) findViewById(R.id.custom_list_listView);
        refreshData();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_nav_setting:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.btn_nav_notification:
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("User_Id");
                editor.commit();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    private void listView(String jsonData) {

        UserListView userDataList = new UserListView(getApplicationContext());
        userList = userDataList.getJsonData(jsonData);
        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.activity_user_list, userList);
        userListView.setAdapter(customAdapter);
    }

    private void refreshData() {
        RequestParams params = new RequestParams();

        params.put("Id", pref.getString("User_Id", ""));

        client.get("http://10.53.128.156:5013/loadStarData", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("data", "DataLoadSuccess " + responseBody + " statuscode" + statusCode);
                // SAVE SESSION_KEY - PREF

                // null 처리 해줄것
                if(statusCode == 201) {
                    String jsonData = new String(responseBody);

                    listView(jsonData);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("login_attempt", "attempt: " + statusCode);
            }
        });
    }
}