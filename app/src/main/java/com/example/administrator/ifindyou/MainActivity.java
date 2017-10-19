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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, AdapterView.OnItemClickListener, CustomAdapter.ListBtnClickListener {

    private RequestParams params;
    private ImageButton iconSetting, iconNoti;
    private SharedPreferences pref;
    private ArrayList<User> userList;
    private ListView userListView;
    private EditText searchName;
    private Button searchButton;
    private Spinner chooseUnit;
    private String userId;
    private ImageButton starBtn;
    private CustomAdapter customAdapter = null;
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        pref = getSharedPreferences("PrefIFindYou", Activity.MODE_PRIVATE);
        userId = pref.getString("User_Id", "");
        iconSetting = (ImageButton) findViewById(R.id.btn_nav_setting);
        iconNoti = (ImageButton) findViewById(R.id.btn_nav_notification);
        searchName = (EditText) findViewById(R.id.search_name);
        searchButton = (Button) findViewById(R.id.search_button);
        chooseUnit = (Spinner) findViewById(R.id.choose_unit);
        starBtn = (ImageButton) findViewById(R.id.btn_star);

        iconSetting.setOnClickListener(this);
        iconNoti.setOnClickListener(this);
        searchName.setOnEditorActionListener(this);
        searchButton.setOnClickListener(this);
        starBtn.setOnClickListener(this);

        userListView = (ListView) findViewById(R.id.main_list_view);
        refreshData(0);
    }

    @Override
    public void onListBtnClick(int position) {
        params = new RequestParams();
        params.put("Id", userId);
        params.put("StarId", userList.get(position).getId());
        Log.d("여기","");
        client.post(getResources().getString(R.string.url) + "starManage", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("data", "DataLoadSuccess " + responseBody + " statuscode" + statusCode);

                // null 처리 해줄것
                if (statusCode == 201) {
                    Toast.makeText(MainActivity.this, "즐겨찾기 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else if (statusCode == 410) {
                    Toast.makeText(MainActivity.this, "즐겨찾기 해제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("login_attempt", "attempt: " + statusCode);
            }
        });
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
            case R.id.btn_star:
                refreshData(0);
                break;
        }
    }

    private void listView(String jsonData) {
        UserListView userDataList = new UserListView(getApplicationContext());
        if (jsonData != null) {
            userList = userDataList.getJsonData(jsonData);
            customAdapter = new CustomAdapter(this, R.layout.activity_user_list, userList, this);
            userListView.setAdapter(customAdapter);
            userListView.setOnItemClickListener(this);
        } else {
            userListView.setEmptyView(findViewById(android.R.id.empty));
            customAdapter = null;
            userListView.setAdapter(customAdapter);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //intent.putExtra("ArticleNumber", articleList.get(position).getArticleNumber() + "");
    }

    private void refreshData(int check) {

        if (check == 0) {
            // load star data - star app and click icon star
            params = new RequestParams();
            params.put("Id", userId);

            client.get(getResources().getString(R.string.url) + "loadStarData", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("data", "DataLoadSuccess " + responseBody + " statuscode" + statusCode);

                    // null 처리 해줄것
                    if (statusCode == 201) {
                        String jsonData = new String(responseBody);

                        listView(jsonData);
                    } else listView(null);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("login_attempt", "attempt: " + statusCode);
                }
            });
        } else if (check == 1) {
            // searchData
            params = new RequestParams();
            params.put("Name", searchName.getText().toString());
            params.put("Unit", chooseUnit.getSelectedItem().toString());
            params.put("Id", userId);
            client.get(getResources().getString(R.string.url) + "loadSearchData", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("data", "DataLoadSuccess " + responseBody + " statuscode" + statusCode);
                    // SAVE SESSION_KEY - PREF

                    // null 처리 해줄것
                    if (statusCode == 201) {
                        String jsonData = new String(responseBody);

                        listView(jsonData);
                    } else listView(null);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("login_attempt", "attempt: " + statusCode);
                }
            });
        }
    }
}