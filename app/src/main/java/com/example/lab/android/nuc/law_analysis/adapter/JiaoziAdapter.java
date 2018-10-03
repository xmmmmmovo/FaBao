package com.example.lab.android.nuc.law_analysis.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.base.Jiaozi;
import com.example.lab.android.nuc.law_analysis.util.views.JzvdStdShowShareButtonAfterFullscreen;

import java.util.ArrayList;

import cn.jzvd.Jzvd;

public class JiaoziAdapter extends RecyclerView.Adapter<JiaoziAdapter.ViewHolder>  {

    private ArrayList<Jiaozi> mJiaozis;
    private Context mContext;

    public JiaoziAdapter(ArrayList<Jiaozi> arrayList, Context context){
        this.mJiaozis = arrayList;
        this.mContext = context;
    }
    @NonNull
    @Override
    public JiaoziAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.fragment_law_video1_item,parent,false);
        final ViewHolder holder = new ViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull JiaoziAdapter.ViewHolder holder, int position) {
        Jiaozi jiaozi = mJiaozis.get( position );
        holder.title.setText( jiaozi.getTitle() );
        holder.mJzvdStdShowShareButtonAfterFullscreen.setUp( jiaozi.getUrl(),jiaozi.getTitle(), Jzvd.SCREEN_WINDOW_NORMAL);
        Glide.with( mContext ).load( jiaozi.getUrl() ).into( holder.mJzvdStdShowShareButtonAfterFullscreen.thumbImageView );
    }

    @Override
    public int getItemCount() {
        return mJiaozis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        JzvdStdShowShareButtonAfterFullscreen mJzvdStdShowShareButtonAfterFullscreen;
        public ViewHolder(View itemView) {
            super( itemView );
            title = (TextView) itemView.findViewById( R.id.video_text );
            mJzvdStdShowShareButtonAfterFullscreen = (JzvdStdShowShareButtonAfterFullscreen)
                    itemView.findViewById( R.id.custom_videoplayer_standard_with_share_button );
        }
    }
}
