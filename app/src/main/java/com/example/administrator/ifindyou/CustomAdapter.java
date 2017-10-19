package com.example.administrator.ifindyou;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-19.
 */

public class CustomAdapter extends ArrayAdapter<User> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<User> userData;

    public CustomAdapter(Context context, int layoutResourceId, ArrayList<User> listData) {
        super(context, layoutResourceId, listData);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.userData = listData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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


        tvName.setText(userData.get(position).getName());
        tvRank.setText(userData.get(position).getRank());
        tvUnit.setText(userData.get(position).getUnit());
        tvPosition.setText(userData.get(position).getPosition());
        tvStatus.setText(userData.get(position).getStatus());

        ImageView imageView = (ImageView) row.findViewById(R.id.custom_row_imageView1);

        String img_path = context.getFilesDir().getPath() + "/" + userData.get(position).getImgName();
        File img_load_path = new File(img_path);

        if(img_load_path.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(img_path);
            imageView.setImageBitmap(bitmap);
        }

        return row;
    }
}