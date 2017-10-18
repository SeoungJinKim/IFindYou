package com.example.administrator.ifindyou;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.ifindyou.Dialog.RegisterDialog;

import java.util.HashMap;

/**
 * Created by Administrator on 2017-10-18.
 */

public class RegisterAdditionalActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

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
        registerBtn.setOnClickListener(this);
        layoutRank.setOnClickListener(this);

        textName.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent e) {
        if (actionId == EditorInfo.IME_ACTION_DONE && v.getId() == R.id.text_name) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textName.getWindowToken(), 0);
            textName.clearFocus();
        }
        else if(actionId == EditorInfo.IME_ACTION_DONE && v.getId() == R.id.text_intro) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textName.getWindowToken(), 0);
            textName.clearFocus();
        }
        else if(actionId == EditorInfo.IME_ACTION_DONE && v.getId() == R.id.text_phone_number) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textName.getWindowToken(), 0);
            textName.clearFocus();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == layoutRank) {
            RegisterDialog.getInstance().dialogRank(mPopupDlg, this, textRank);
        }
        if (isCompletedInput) {
            if (v == registerBtn) {
                Intent intent = new Intent(this, MainActivity.class);
                //HashMap field = (HashMap) postField(email, password);
                //intent.putExtra("Field", field);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                //connRegistCall(email, password);
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