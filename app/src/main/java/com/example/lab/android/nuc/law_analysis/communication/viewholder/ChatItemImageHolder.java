package com.example.lab.android.nuc.law_analysis.communication.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.law_analysis.communication.activity.ImageDisplayActivity;
import com.example.lab.android.nuc.law_analysis.communication.bean.ChatMessageBean;
import com.example.lab.android.nuc.law_analysis.communication.utils.Constants;
import com.example.lab.android.nuc.law_analysis.communication.utils.PhotoUtils;
import com.example.lab.android.nuc.law_analysis.R;

import io.github.leibnik.chatimageview.ChatImageView;


public class ChatItemImageHolder extends ChatItemHolder {

    protected ChatImageView contentView;

    public ChatItemImageHolder(Context context, ViewGroup root, boolean isLeft) {
        super(context, root, isLeft);
    }

    @Override
    public void initView() {
        super.initView();
        if (isLeft) {
//            contentView.setBackgroundResource(R.drawable.rc_ic_bubble_left);
            conventLayout.addView(View.inflate(getContext(), R.layout.chat_item_image_layout, null));
        } else {
//            contentView.setBackgroundResource(R.drawable.rc_ic_bubble_right);
            conventLayout.addView(View.inflate(getContext(), R.layout.chat_item_right_image_layout, null));
        }
        contentView = (ChatImageView) itemView.findViewById(R.id.chat_item_image_view);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImageDisplayActivity.class);
                intent.putExtra( Constants.IMAGE_LOCAL_PATH, message.getImageLocal());
                intent.putExtra(Constants.IMAGE_URL, message.getImageUrl());
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void bindData(Object o) {
        super.bindData(o);
//        contentView.setImageResource(0);
        ChatMessageBean message = (ChatMessageBean) o;
        if (message != null) {
            String localFilePath = message.getImageLocal();
//            if (!TextUtils.isEmpty(localFilePath)) {
//                ImageLoader.getInstance().displayImage("file://" + localFilePath, contentView);
//            } else {
            PhotoUtils.displayImageCacheElseNetwork(contentView, localFilePath, message.getImageUrl());
//            }
        }
    }
}