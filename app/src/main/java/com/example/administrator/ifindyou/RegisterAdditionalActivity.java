package com.example.administrator.ifindyou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ifindyou.Dialog.RegisterDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017-10-18.
 */

public class RegisterAdditionalActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private View registerAdditionalView;
    private LinearLayout registerForm;
    private EditText textName, textIntro, textPhone;
    private TextView textRank, textPosition, textUnit, textStatus;
    private LinearLayout layoutRank, layoutPosition, layoutUnit, layoutStatus;
    private Button registerBtn;
    private boolean isCompletedInput = false;
    DialogInterface mPopupDlg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_additionalinfo);

        registerForm = (LinearLayout) findViewById(R.id.register_additional);
        registerAdditionalView = (View) findViewById(R.id.view_register_additional);
        textName = (EditText) findViewById(R.id.text_name);
        textIntro = (EditText) findViewById(R.id.text_intro);
        textPhone = (EditText) findViewById(R.id.text_phone_number);
        textRank = (TextView) findViewById(R.id.text_rank);
        textPosition = (TextView) findViewById(R.id.text_position);
        textUnit = (TextView) findViewById(R.id.text_unit);
        textStatus = (TextView) findViewById(R.id.text_status);
        registerBtn = (Button) findViewById(R.id.register_button);
        layoutRank = (LinearLayout) findViewById(R.id.layout_rank);
        layoutPosition = (LinearLayout) findViewById(R.id.layout_position);
        layoutUnit = (LinearLayout) findViewById(R.id.layout_unit);
        layoutStatus = (LinearLayout) findViewById(R.id.layout_status);


        registerForm.setOnClickListener(this);
        registerAdditionalView.setOnClickListener(this);

        registerBtn.setOnClickListener(this);
        layoutRank.setOnClickListener(this);
        layoutPosition.setOnClickListener(this);
        layoutUnit.setOnClickListener(this);
        layoutStatus.setOnClickListener(this);

        textName.setOnEditorActionListener(this);
        textIntro.setOnEditorActionListener(this);
        textPhone.setOnEditorActionListener(this);
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
        if (actionId == EditorInfo.IME_ACTION_DONE && v.getId() == R.id.text_name) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textName.getWindowToken(), 0);
            textName.clearFocus();
        } else if (actionId == EditorInfo.IME_ACTION_DONE && v.getId() == R.id.text_intro) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textName.getWindowToken(), 0);
            textIntro.clearFocus();
        } else if (actionId == EditorInfo.IME_ACTION_DONE && v.getId() == R.id.text_phone_number) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textName.getWindowToken(), 0);
            textPhone.clearFocus();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textName.getWindowToken(), 0);
        v.clearFocus();
        if (v == layoutRank) {
            RegisterDialog.getInstance().dialogRank(mPopupDlg, this, textRank);
        } else if (v == layoutPosition) {
            RegisterDialog.getInstance().dialogPosition(mPopupDlg, this, textPosition);
        } else if (v == layoutUnit) {
            RegisterDialog.getInstance().dialogUnit(mPopupDlg, this, textUnit);
        } else if (v == layoutStatus) {
            RegisterDialog.getInstance().dialogStatus(mPopupDlg, this, textStatus);
        }
        if (isCompletedInput) {
            if (v == registerBtn) {

                String id = getIntent().getExtras().getString("id");
                String password = getIntent().getExtras().getString("password");

                RequestParams params = new RequestParams();
                params.put("Id", id);
                params.put("Password", password);
                params.put("Name", textName.getText().toString());
                params.put("Rank", textRank.getText().toString());
                params.put("Position", textPosition.getText().toString());
                params.put("Unit", textUnit.getText().toString());
                params.put("Content", textIntro.getText().toString());
                params.put("PhoneNumber", textPhone.getText().toString());
                params.put("Status", textStatus.getText().toString());
                params.put("ImgName", "temp.jpg");

                client.post(getResources().getString(R.string.url) + "signup", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        final Intent intent = new Intent(RegisterAdditionalActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        }
    }

    public void checkInputCompleted() {
        if (!textName.getText().toString().equals("입력해주세요")
                && !textRank.getText().toString().equals("입력해주세요")
                && !textPosition.getText().toString().equals("입력해주세요")
                && !textUnit.getText().toString().equals("입력해주세요")
                && !textIntro.getText().toString().equals("입력해주세요")
                && !textPhone.getText().toString().equals("입력해주세요")
                && !textStatus.getText().toString().equals("입력해주세요")) {
            registerBtn.setBackgroundColor(this.getResources().getColor(R.color.base_color));
            isCompletedInput = true;
        } else {
            registerBtn.setBackgroundColor(this.getResources().getColor(R.color.Gray_B6));
            isCompletedInput = false;
        }
    }
}