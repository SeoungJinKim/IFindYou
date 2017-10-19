package com.example.administrator.ifindyou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.nio.charset.Charset;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;

/**
 * Created by Administrator on 2017-10-18.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    private TextView textAnotherRegister;
    private Button signInButton;
    private AutoCompleteTextView mIdView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textAnotherRegister = (TextView) findViewById(R.id.another_regist);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        textAnotherRegister.setOnClickListener(this);
        signInButton.setOnClickListener(this);

        mIdView = (AutoCompleteTextView) findViewById(R.id.id);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                attemptLogin();
                break;
            case R.id.another_regist:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
        }
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    private void attemptLogin() {

        final String id = mIdView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(id)) {
            mIdView.setError(getString(R.string.error_field_required));
            focusView = mIdView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            RequestParams params = new RequestParams();
            params.put("Id", id);
            params.put("Password", password);
            Log.d("params", "params: " + id);

            client.get("http://10.53.128.156:5013/login", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("login", "LoginSuccess");
                    if (statusCode == 204) {
                        Toast.makeText(LoginActivity.this,"잘못된 정보를 입력하셨습니다.",Toast.LENGTH_SHORT).show();
                    }
                    // SAVE SESSION_KEY - PREF
                    else {
                        SharedPreferences prefs = getSharedPreferences("PrefIFindYou", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("User_Id", id);
                        editor.commit();

                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("login_attempt", "attempt: " + statusCode);
                }
            });
        }
    }

    public void onBackPressed() {
        super.finish();
        overridePendingTransition(R.anim.anim_exit_in, R.anim.anim_exit_out);
    }

    @Override
    public void finish() {
        super.finish();
    }
}