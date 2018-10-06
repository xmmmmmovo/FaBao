package com.example.lab.android.nuc.law_analysis.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.base.DataBean;

import java.util.List;


public class Main_Analysis_Adapter extends RecyclerView.Adapter {
    
    private List<DataBean> dataBeans;
    private Context context;
    
    private View view;
    
     public Main_Analysis_Adapter(Context context, List<DataBean> data){
         this.context = context;
         this.dataBeans  = data;
     }
    
    
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       view = LayoutInflater.from(context)
               .inflate(R.layout.cardview_analyis_item,parent,false);
       
      final  ItemHolder  holder= new ItemHolder(view);
       holder.item.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               for (int i = 0 ; i < dataBeans.size() ; i++)
               Toast.makeText(context, "你点击了"+getItemId(i), Toast.LENGTH_SHORT).show();
           }
       });
       return new ItemHolder(view);
     }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
         ItemHolder itemHolder =(ItemHolder) holder;
         itemHolder.demo_textview.setTag(position);
         DataBean dataBean = dataBeans.get(position);
         itemHolder.demo_textview.setText(dataBean.getDemo_textView());



    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public long getItemId(int position) {
        return position;
    }


    private class ItemHolder extends RecyclerView.ViewHolder {

         private TextView demo_textview;
         private View item;



        public ItemHolder(View view) {
            super(view);
            demo_textview = view.findViewById(R.id.textview_anli_item);
            item = view;
        }
    }
}
