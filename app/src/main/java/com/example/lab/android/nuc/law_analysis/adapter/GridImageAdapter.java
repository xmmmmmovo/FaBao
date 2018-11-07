package com.example.lab.android.nuc.law_analysis.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.lab.android.nuc.law_analysis.R;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<LocalMedia> list = new ArrayList<>(  );
    private int selectMax = 9;
    private Context mContext;

    /**
     * 点添加图片跳转
     * @return
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener{
        void onAddPicClick();
    }

    public GridImageAdapter(Context context,onAddPicClickListener mOnAddPicClickListener){
        mContext = context;
        mInflater = LayoutInflater.from( context );
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    public void setSelectMax(int selectMax){
        this.selectMax = selectMax;
    }

    public void setList(List<LocalMedia> list){
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        LinearLayout ll_del;
        TextView tv_duration;

        public ViewHolder(View itemView) {
            super( itemView );
            mImg = (ImageView) itemView.findViewById( R.id.fiv );
            ll_del = (LinearLayout) itemView.findViewById( R.id.ll_del );
            tv_duration = (TextView) itemView.findViewById( R.id.tv_duration );
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate( R.layout.gv_filter_image,parent,false );
        final ViewHolder viewHolder = new ViewHolder( view );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GridImageAdapter.ViewHolder holder, int position) {
        //少于八张则继续添加图标
        if (getItemViewType( position ) == TYPE_CAMERA){
            holder.mImg.setImageResource( R.drawable.addimg_1x );
            holder.mImg.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick();
                }
            } );
            holder.ll_del.setVisibility( View.INVISIBLE );
        }else {
            holder.ll_del.setVisibility( View.VISIBLE );
            holder.ll_del.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = holder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致
                    if (index != RecyclerView.NO_POSITION){
                        list.remove( index );
                        notifyItemRemoved( index );
                        notifyItemChanged( index,list.size());
                    }
                }
            } );
            LocalMedia media = list.get( position );
            int mimeType = media.getMimeType();
            String path = "";
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
            //图片
            if (media.isCompressed()){
                Log.e( "image result:",new File(media.getCompressPath()).length() / 1024 + "k" );
                Log.e( "压缩地址：",media.getCompressPath() );
            }

            Log.e( "原图地址：",media.getPath() );
            int pictureType = PictureMimeType.isPictureType( media.getPictureType());
            if (media.isCut()){
                Log.e( "裁剪地址：",media.getCutPath() );
            }
            long duration = media.getDuration();
            holder.tv_duration.setVisibility( pictureType == PictureConfig.TYPE_VIDEO
                    ? View.VISIBLE : View.GONE);
            if (mimeType == PictureMimeType.ofAudio()){
                holder.tv_duration.setVisibility( View.VISIBLE);
                Drawable drawable = ContextCompat.getDrawable( mContext,R.drawable.picture_audio );
                StringUtils.modifyTextViewDrawable( holder.tv_duration,drawable,0 );
            }else {
                Drawable drawable = ContextCompat.getDrawable( mContext,R.drawable.video_icon );
                StringUtils.modifyTextViewDrawable( holder.tv_duration,drawable,0 );
            }
            holder.tv_duration.setText( DateUtils.timeParse( duration ));
            if (mimeType == PictureMimeType.ofAudio()){
                holder.mImg.setImageResource( R.drawable.audio_placeholder );
            }else {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder( R.color.color_f6 )
                        .diskCacheStrategy( DiskCacheStrategy.ALL );
                Glide.with( holder.itemView.getContext() )
                        .load( path )
                        .apply( options )
                        .into( holder.mImg );
            }

            //itemView的点击事件
            if ( mItemClickListener != null){
                holder.itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = holder.getAdapterPosition();
                        mItemClickListener.onItemClick( adapterPosition,v );
                    }
                } );
            }
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax){
            return list.size() + 1;
        }else {
            return list.size();
        }
    }

    //判断是否点击了点一个拍照按钮
    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem( position )){
            return TYPE_CAMERA;
        }else {
            return TYPE_PICTURE;
        }
    }


    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}

