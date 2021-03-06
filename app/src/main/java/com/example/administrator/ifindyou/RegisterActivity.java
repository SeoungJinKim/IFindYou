package com.example.administrator.ifindyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-10-18.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvAdditionalRegister;
    private EditText etId,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvAdditionalRegister = (TextView) findViewById(R.id.register_button);
        etId = (EditText) findViewById(R.id.id);
        etPassword = (EditText) findViewById(R.id.password);
        tvAdditionalRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.register_button:
                Intent intent = new Intent(this, RegisterAdditionalActivity.class);
                intent.putExtra("id",etId.getText().toString());
                intent.putExtra("password",etPassword.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
        }
    }
}