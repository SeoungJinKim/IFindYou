package com.example.administrator.ifindyou.Dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ifindyou.R;
import com.example.administrator.ifindyou.RegisterAdditionalActivity;

/**
 * Created by Administrator on 2017-10-18.
 */

public class RegisterDialog {
    private volatile static RegisterDialog single;
    RecyclerView recyclerView;
    RegisterDialogAdapter adapter;

    public static RegisterDialog getInstance() {
        if (single == null) {
            synchronized(RegisterDialog.class) {
                if (single == null) {
                    single = new RegisterDialog();
                }
            }
        }
        return single;
    }

    public void dialogRank(DialogInterface mPopupDlg, final RegisterAdditionalActivity activity , final TextView tv) {
        final View linear = (LinearLayout) View.inflate(activity, R.layout.dialog_register, null);
        ImageView closeBtn = (ImageView) linear.findViewById(R.id.close_btn);

        recyclerView = (RecyclerView) linear.findViewById(R.id.register_dialog_recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(linear);
        mPopupDlg = builder.show();
        final DialogInterface mPopupDlg2 = mPopupDlg;

        adapter = new RegisterDialogAdapter(new RegisterDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                dismissDlg(mPopupDlg2);
                setTextWithColor(tv, adapter.mDataset.get(position)+"kg", activity);

            }
        }, activity.getApplicationContext(), "weight");

        adapter.clear();
        for (int i=20; i<200; i++)
            adapter.addData(i+"");

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDlg(mPopupDlg2);
            }
        });
    }

    private void dismissDlg(DialogInterface mPopupDlg) {
        mPopupDlg.dismiss();
    }

    private void setTextWithColor(final TextView tv, String str, final RegisterAdditionalActivity activity) {
        tv.setText(str);
        tv.setTextColor(Color.parseColor("#000000"));
        activity.checkInputCompleted();
    }
}
