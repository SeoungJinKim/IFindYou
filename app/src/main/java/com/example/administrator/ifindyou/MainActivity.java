package com.example.administrator.ifindyou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private ImageButton iconSetting, iconNoti;
    private SharedPreferences pref;
    private ArrayList<User> userList;
    private ListView userListView;
    private EditText searchName;
    private Button searchButton;
    private Spinner chooseUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        pref = getSharedPreferences("PrefIFindYou", Activity.MODE_PRIVATE);

        iconSetting = (ImageButton) findViewById(R.id.btn_nav_setting);
        iconNoti = (ImageButton) findViewById(R.id.btn_nav_notification);
        searchName = (EditText) findViewById(R.id.search_name);
        searchButton = (Button) findViewById(R.id.search_button);
        chooseUnit = (Spinner) findViewById(R.id.choose_unit);

        iconSetting.setOnClickListener(this);
        iconNoti.setOnClickListener(this);
        searchName.setOnEditorActionListener(this);
        searchButton.setOnClickListener(this);

        userListView = (ListView) findViewById(android.R.id.list);
        refreshData(0);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
        if (actionId == EditorInfo.IME_ACTION_DONE && v.getId() == R.id.search_name) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(searchName.getWindowToken(), 0);
            searchName.clearFocus();
        }
        return true;
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
            case R.id.search_button:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchName.getWindowToken(), 0);
                searchName.clearFocus();
                refreshData(1);
                break;
        }
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    private void listView(String jsonData) {
        UserListView userDataList = new UserListView(getApplicationContext());
        CustomAdapter customAdapter;
        if(jsonData != null) {
            userList = userDataList.getJsonData(jsonData);
            customAdapter = new CustomAdapter(this, R.layout.activity_user_list, userList);
            userListView.setAdapter(customAdapter);
        }
        else
        {
            userListView.setEmptyView(findViewById(android.R.id.empty));
            customAdapter = null;
            userListView.setAdapter(customAdapter);
        }

    }

    private void refreshData(int check) {
        RequestParams params = new RequestParams();

        if (check == 0) {
            params.put("Id", pref.getString("User_Id", ""));

            client.get("http://10.53.128.156:5013/loadStarData", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("data", "DataLoadSuccess " + responseBody + " statuscode" + statusCode);
                    // SAVE SESSION_KEY - PREF

                    // null 처리 해줄것
                    if (statusCode == 201) {
                        String jsonData = new String(responseBody);

                        listView(jsonData);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("login_attempt", "attempt: " + statusCode);
                }
            });
        } else {
            params.put("Name", searchName.getText().toString());
            params.put("Unit", chooseUnit.getSelectedItem().toString());
            client.get("http://10.53.128.156:5013/loadSearchData", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("data", "DataLoadSuccess " + responseBody + " statuscode" + statusCode);
                    // SAVE SESSION_KEY - PREF

                    // null 처리 해줄것
                    if (statusCode == 201) {
                        String jsonData = new String(responseBody);

                        listView(jsonData);
                    }else listView(null);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("login_attempt", "attempt: " + statusCode);
                }
            });
        }
    }
}