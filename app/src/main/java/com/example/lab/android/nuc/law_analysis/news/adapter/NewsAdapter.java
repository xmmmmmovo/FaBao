package com.example.lab.android.nuc.law_analysis.news.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.news.ImageLoader;
import com.example.lab.android.nuc.law_analysis.news.adapter.AbsListAdapter;
import com.example.lab.android.nuc.law_analysis.news.bean.NewsBean;
/*
新闻Adapter
 */
public class NewsAdapter extends AbsListAdapter<NewsBean>{


    public NewsAdapter() {
        super( R.layout.item_news );
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NewsBean item) {
        baseViewHolder.setText( R.id.tv_news_title,item.getTitle() );
        baseViewHolder.setText( R.id.tv_news_source,item.getSource());
        baseViewHolder.setText(R.id.tv_news_date, item.getLmodify().substring(0, 10));
        ImageLoader.getInstance().withDefault(mContext, item.getImgsrc(), (ImageView) baseViewHolder.getView(R.id.img_news));
    }
}
