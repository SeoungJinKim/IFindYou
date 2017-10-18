package com.example.administrator.ifindyou.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ifindyou.R;
import com.example.administrator.ifindyou.RegisterAdditionalActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-10-18.
 */

public class RegisterDialogAdapter extends RecyclerView.Adapter<RegisterDialogAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 0;

    public Context context;
    private OnItemClickListener mOnItemClickListener;
    public ArrayList<String> mDataset = new ArrayList<>();

    String type;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public RegisterDialogAdapter(OnItemClickListener onItemClickListener, Context mContext, String mType) {
        mOnItemClickListener = onItemClickListener;
        context = mContext;
        type = mType;
        mDataset.clear();
    }

    public void addData(String item) {
        mDataset.add(item);
    }

    public void clear() {
        mDataset.clear();
    }

    @Override
    public RegisterDialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_register_dialog_item, parent, false);
            return new ItemViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(holder instanceof ItemViewHolder) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            if (type.equals("rank"))
                itemViewHolder.tv.setText(mDataset.get(position)+"cm");
            else if (type.equals("weight"))
                itemViewHolder.tv.setText(mDataset.get(position)+"kg");
            else if (type.equals("age"))
                itemViewHolder.tv.setText(mDataset.get(position)+"세");
        }
    }

    @Override
    public int getItemViewType (int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }



    /*
        ViewHolder
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View container;

        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView;
        }
    }

    public class ItemViewHolder extends ViewHolder {
        public TextView tv;

        public ItemViewHolder(View v) {
            super(v);
            tv = (TextView) v.findViewById(R.id.tv);
        }
    }
}
