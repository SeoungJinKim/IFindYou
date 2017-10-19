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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvAdditionalRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvAdditionalRegister = (TextView) findViewById(R.id.register_button);
        tvAdditionalRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.register_button:
                Intent intent = new Intent(this, RegisterAdditionalActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
        }
    }
}