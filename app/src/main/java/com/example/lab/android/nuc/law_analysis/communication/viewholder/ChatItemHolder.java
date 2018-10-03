package com.example.lab.android.nuc.law_analysis.communication.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.law_analysis.communication.bean.ChatConst;
import com.example.lab.android.nuc.law_analysis.communication.bean.ChatMessageBean;
import com.example.lab.android.nuc.law_analysis.communication.utils.Utils;
import com.example.lab.android.nuc.law_analysis.R;


public class ChatItemHolder extends CommonViewHolder {
    protected boolean isLeft;

    protected ImageView avatarView;
    protected TextView timeView;
    protected TextView nameView;
    protected LinearLayout conventLayout;
    protected FrameLayout statusLayout;
    protected ProgressBar progressBar;
    protected TextView statusView;
    protected ImageView errorView;
    protected ChatMessageBean message;

    public ChatItemHolder(Context context, ViewGroup root, boolean isLeft) {
        super(context, root, isLeft ? R.layout.chat_item_left_layout : R.layout.chat_item_right_layout);
        this.isLeft = isLeft;
        initView();
    }

    public void initView() {
        if (isLeft) {
            avatarView = (ImageView) itemView.findViewById(R.id.chat_left_iv_avatar);
            timeView = (TextView) itemView.findViewById(R.id.chat_left_tv_time);
            nameView = (TextView) itemView.findViewById(R.id.chat_left_tv_name);
            conventLayout = (LinearLayout) itemView.findViewById(R.id.chat_left_layout_content);
            statusLayout = (FrameLayout) itemView.findViewById(R.id.chat_left_layout_status);
            statusView = (TextView) itemView.findViewById(R.id.chat_left_tv_status);
            progressBar = (ProgressBar) itemView.findViewById(R.id.chat_left_progressbar);
            errorView = (ImageView) itemView.findViewById(R.id.chat_left_tv_error);
        } else {
            avatarView = (ImageView) itemView.findViewById(R.id.chat_right_iv_avatar);
            timeView = (TextView) itemView.findViewById( R.id.chat_right_tv_time);
            nameView = (TextView) itemView.findViewById(R.id.chat_right_tv_name);
            conventLayout = (LinearLayout) itemView.findViewById(R.id.chat_right_layout_content);
            statusLayout = (FrameLayout) itemView.findViewById(R.id.chat_right_layout_status);
            progressBar = (ProgressBar) itemView.findViewById(R.id.chat_right_progressbar);
            errorView = (ImageView) itemView.findViewById(R.id.chat_right_tv_error);
            statusView = (TextView) itemView.findViewById(R.id.chat_right_tv_status);
        }
    }

    @Override
    public void bindData(Object o) {
        message = (ChatMessageBean) o;
        timeView.setText( Utils.millisecsToDateString(message.getTime()));

        String userId = message.getUserId();
        nameView.setText(message.getUserName());
        if (isLeft) {
            Glide.with( getContext() ).load( ChatConst.RESPONSE_HEAD_IMAGE ).into( avatarView );
        } else {
            avatarView.setImageResource(R.drawable.icon);
        }
        switch (message.getSendState()) {
            case ChatConst.SENDING:
                statusLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                break;
            case ChatConst.COMPLETED:
                statusLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                break;
            case ChatConst.SENDERROR:
                statusLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void showTimeView(boolean isShow) {
        timeView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void showUserName(boolean isShow) {
        nameView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
