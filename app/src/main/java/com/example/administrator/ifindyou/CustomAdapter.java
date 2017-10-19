package com.example.administrator.ifindyou;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-19.
 */

public class CustomAdapter extends ArrayAdapter<User> implements View.OnClickListener {
    private Context context;
    private int layoutResourceId;
    private ArrayList<User> userData;

    public interface ListBtnClickListener {
        void onListBtnClick(int starId);
    }

    // 생성자로부터 전달된 ListBtnClickListener  저장.
    private ListBtnClickListener listBtnClickListener;


    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.

    public CustomAdapter(Context context, int layoutResourceId, ArrayList<User> listData, ListBtnClickListener clickListener) {
        super(context, layoutResourceId, listData);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.userData = listData;
        this.listBtnClickListener = clickListener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        TextView tvName = (TextView) row.findViewById(R.id.user_name);
        TextView tvRank = (TextView) row.findViewById(R.id.user_rank);
        TextView tvUnit = (TextView) row.findViewById(R.id.user_unit);
        TextView tvPosition = (TextView) row.findViewById(R.id.user_position);
        TextView tvStatus = (TextView) row.findViewById(R.id.user_status);
        ImageButton ibStar = (ImageButton) row.findViewById(R.id.icon_star);

        tvName.setText(userData.get(position).getName());
        tvRank.setText(userData.get(position).getRank());
        tvUnit.setText(userData.get(position).getUnit());
        tvPosition.setText(userData.get(position).getPosition());
        tvStatus.setText(userData.get(position).getStatus());

        ImageView imageView = (ImageView) row.findViewById(R.id.custom_row_imageView1);

        ibStar.setTag(position);
        ibStar.setOnClickListener(this);

        String img_path = context.getFilesDir().getPath() + "/" + userData.get(position).getImgName();
        File img_load_path = new File(img_path);

        if (img_load_path.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(img_path);
            imageView.setImageBitmap(bitmap);
        }

        return row;
    }

    public void onClick(View v) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag());
        }
    }
}