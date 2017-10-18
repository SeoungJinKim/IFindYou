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
    TextView title;

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
        title = (TextView) linear.findViewById(R.id.dialog_text);
        title.setText(activity.getString(R.string.rank) + "을 선택하여 주십시오");

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
                setTextWithColor(tv, adapter.mDataset.get(position), activity);
            }
        }, activity.getApplicationContext(), "rank");

        adapter.clear();
        adapter.addData(activity.getString(R.string.four_star)+"");
        adapter.addData(activity.getString(R.string.before_four_star)+"");
        adapter.addData(activity.getString(R.string.three_star)+"");
        adapter.addData(activity.getString(R.string.before_three_star)+"");
        adapter.addData(activity.getString(R.string.two_star)+"");
        adapter.addData(activity.getString(R.string.before_two_star)+"");
        adapter.addData(activity.getString(R.string.one_star)+"");
        adapter.addData(activity.getString(R.string.before_one_star)+"");
        adapter.addData(activity.getString(R.string.three_bam)+"");
        adapter.addData(activity.getString(R.string.before_three_bam)+"");
        adapter.addData(activity.getString(R.string.two_bam)+"");
        adapter.addData(activity.getString(R.string.before_two_bam)+"");
        adapter.addData(activity.getString(R.string.one_bam)+"");
        adapter.addData(activity.getString(R.string.before_one_bam)+"");
        adapter.addData(activity.getString(R.string.three_dia)+"");
        adapter.addData(activity.getString(R.string.before_three_dia)+"");
        adapter.addData(activity.getString(R.string.two_dia)+"");
        adapter.addData(activity.getString(R.string.before_two_dia)+"");
        adapter.addData(activity.getString(R.string.one_dia)+"");
        adapter.addData(activity.getString(R.string.yellow_dia)+"");
        adapter.addData(activity.getString(R.string.four_staff)+"");
        adapter.addData(activity.getString(R.string.before_four_staff)+"");
        adapter.addData(activity.getString(R.string.three_staff)+"");
        adapter.addData(activity.getString(R.string.before_three_staff)+"");
        adapter.addData(activity.getString(R.string.two_staff)+"");
        adapter.addData(activity.getString(R.string.one_staff)+"");

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
