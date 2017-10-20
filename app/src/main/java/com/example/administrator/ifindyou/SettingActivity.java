package com.example.administrator.ifindyou;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017-10-18.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Button submitBtn, logoutBtn;
    private TextView userName, userStatus, userRank, userPosition, userUnit, userContent, userPhoneNumber;
    private Spinner layoutRank, layoutPosition, layoutUnit, layoutStatus;
    private SharedPreferences pref;
    private boolean isCompletedInput = false;
    DialogInterface mPopupDlg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        pref = getSharedPreferences("PrefIFindYou", MODE_PRIVATE);
        userName = (TextView) findViewById(R.id.text_name);
        userContent = (TextView) findViewById(R.id.text_intro);
        userPhoneNumber = (TextView) findViewById(R.id.text_phone_number);
        submitBtn = (Button) findViewById(R.id.submit);
        logoutBtn = (Button) findViewById(R.id.logout);

        userName.setText(pref.getString("Name", ""));
        userPhoneNumber.setText(pref.getString("PhoneNumber", ""));
        userContent.setText(pref.getString("Content", ""));

        layoutRank = (Spinner) findViewById(R.id.choose_rank);
        layoutPosition = (Spinner) findViewById(R.id.choose_position);
        layoutUnit = (Spinner) findViewById(R.id.choose_unit);
        layoutStatus = (Spinner) findViewById(R.id.choose_status);


        submitBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public void onClick(View v) {
        Intent intent;
        SharedPreferences.Editor editor = pref.edit();
        switch (v.getId()) {
            case R.id.submit:
                RequestParams params = new RequestParams();
                params.put("Name", userName.getText().toString());
                params.put("Rank", layoutRank.getSelectedItem().toString());
                params.put("Position", layoutRank.getSelectedItem().toString());
                params.put("Unit", layoutUnit.getSelectedItem().toString());
                params.put("Content", userContent.getText().toString());
                params.put("PhoneNumber", userPhoneNumber.getText().toString());
                params.put("Status", layoutStatus.getSelectedItem().toString());
                params.put("Id", pref.getString("User_Id", ""));

                editor.putString("Name", userName.getText().toString());
                editor.putString("Rank", layoutRank.getSelectedItem().toString());
                editor.putString("Position", layoutRank.getSelectedItem().toString());
                editor.putString("Unit", layoutUnit.getSelectedItem().toString());
                editor.putString("Content", userContent.getText().toString());
                editor.putString("PhoneNumber", userPhoneNumber.getText().toString());
                editor.putString("Status", layoutStatus.getSelectedItem().toString());
                editor.commit();

                checkInputCompleted();
                if (isCompletedInput) {
                    if (v == submitBtn) {
                        client.post(getResources().getString(R.string.url) + "update", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            }
                        });
                    }
                }
                else Toast.makeText(this,"빈곳없이 입력해주세요",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                editor.clear();
                editor.commit();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void checkInputCompleted() {
        if (!userName.getText().toString().equals("입력해주세요")
                && !userContent.getText().toString().equals("입력해주세요")
                && !userPhoneNumber.getText().toString().equals("입력해주세요")) {
            submitBtn.setBackgroundColor(this.getResources().getColor(R.color.base_color));
            isCompletedInput = true;
        } else {
            submitBtn.setBackgroundColor(this.getResources().getColor(R.color.Gray_B6));
            isCompletedInput = false;
        }
    }
}