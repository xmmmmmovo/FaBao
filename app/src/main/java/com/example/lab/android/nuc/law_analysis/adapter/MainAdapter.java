package com.example.lab.android.nuc.law_analysis.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.base.Law;
import com.example.lab.android.nuc.law_analysis.news.activity.NewsDetailActivity;
import com.example.lab.android.nuc.law_analysis.news.util.WebUtil;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private Context mContext;

    private List<Law> mLawList;

    public MainAdapter(List<Law> laws){
        mLawList = laws;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from( mContext ).inflate( R.layout.law_item,parent,false);
        final ViewHolder holder = new ViewHolder( view );
        holder.caedView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Law law = mLawList.get( position );
                WebUtil.openWeb(mContext, law.getLaw_name(), law.getLaw_uri());

            }
        } );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Law law = mLawList.get( position );
        holder.lawName.setText(law.getLaw_name());
        Glide.with( mContext ).load( law.getLaw_image() ).into( holder.mImageView );
    }

    @Override
    public int getItemCount() {
        return mLawList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView caedView;
        ImageView  mImageView;
        TextView lawName;

        public ViewHolder(View itemView) {
            super( itemView );
            caedView = (CardView) itemView;
            mImageView = (ImageView) itemView.findViewById( R.id.law_image );
            lawName = (TextView) itemView.findViewById( R.id.law_name );
        }
    }
}
