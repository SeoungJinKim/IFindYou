package com.example.administrator.ifindyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-10-18.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textAnotherRegister;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textAnotherRegister = (TextView) findViewById(R.id.another_regist);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        textAnotherRegister.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.another_regist:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.sign_in_button:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                finish();
                break;
        }
    }

    public void onBackPressed() {
        super.finish();
        overridePendingTransition(R.anim.anim_exit_in, R.anim.anim_exit_out);
    }
}