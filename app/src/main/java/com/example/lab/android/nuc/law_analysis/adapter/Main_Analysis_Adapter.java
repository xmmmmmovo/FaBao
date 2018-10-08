package com.example.lab.android.nuc.law_analysis.adapter;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.base.DataBean;

import java.util.List;


public class Main_Analysis_Adapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List<DataBean> dataBeans;
    private Context context;

    private View view1;



    public Main_Analysis_Adapter(Context context, List<DataBean> data){
        this.context = context;
        this.dataBeans  = data;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        view1 = LayoutInflater.from(context)
                .inflate(R.layout.cardview_analyis_item,parent,false);

        view1.setOnClickListener(Main_Analysis_Adapter.this);
        return new ItemHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position,List payloads) {

        ItemHolder itemHolder =(ItemHolder) holder;
        if (payloads.isEmpty()){
            itemHolder.itemView.setTag(position);

            itemHolder.demo_textview.setTag(position);

            DataBean dataBean = dataBeans.get(position);
            itemHolder.demo_textview.setText(dataBean.getDemo_textView());
        }


        itemHolder.demo_textview.setOnClickListener(Main_Analysis_Adapter.this);
        itemHolder.setIsRecyclable(false);
    }


    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public long getItemId(int position) {
        return position;
    }


    public class ItemHolder extends RecyclerView.ViewHolder {

        public TextView demo_textview;


        public ItemHolder(View view) {
            super(view);
            demo_textview = view.findViewById(R.id.textview_anli_item);

        }
    }


    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, int position);

    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (mOnItemClickListener != null) {
            switch (v.getId()) {

                case R.id.textview_anli_item:
                    mOnItemClickListener.onClick(v, position);
                    break;

                default:

                    break;
            }
        }
    }


}
