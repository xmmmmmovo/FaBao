package com.example.lab.android.nuc.law_analysis.communication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.lab.android.nuc.law_analysis.communication.utils.EmotionHelper;
import com.example.lab.android.nuc.law_analysis.communication.viewholder.ViewHolder;
import com.example.lab.android.nuc.law_analysis.R;

import java.util.ArrayList;
import java.util.List;

public class ChatEmotionGridAdapter extends BaseAdapter {
  private Context context;
  private List<String> datas = new ArrayList<>();

  public ChatEmotionGridAdapter(Context ctx) {
    this.context = ctx;
  }

  public void setDatas(List<String> datas) {
    this.datas = datas;
  }

  @Override
  public int getCount() {
    return datas.size();
  }

  @Override
  public Object getItem(int i) {
    return datas.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int position, View conView, ViewGroup parent) {
    if (conView == null) {
      conView = View.inflate(context, R.layout.chat_emotion_item, null);
    }
    ImageView emotionImageView = ViewHolder.findViewById(conView, R.id.emotionImageView);
    String emotion = (String) getItem(position);
    emotion = emotion.substring(1, emotion.length() - 1);
    Bitmap bitmap = EmotionHelper.getEmojiDrawable(context, emotion);
    emotionImageView.setImageBitmap(bitmap);
    return conView;
  }
}
