package com.example.lab.android.nuc.law_analysis.communication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lab.android.nuc.law_analysis.communication.utils.Constants;
import com.example.lab.android.nuc.law_analysis.communication.utils.PhotoUtils;
import com.example.lab.android.nuc.law_analysis.R;

public class PictureTextActivity extends AppCompatActivity {


    private ImageView mImageView;

    private TextView mTextView;

    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_picture_text );

        mImageView = (ImageView) findViewById( R.id.image_view );
        mTextView = (TextView) findViewById( R.id.text_view );
        mScrollView = (ScrollView) findViewById( R.id.scrollview );

        mTextView.setSelected( true );
        mTextView.setTextIsSelectable( true );
        Intent intent = getIntent();
        String path = intent.getStringExtra( "path" );
        String text = intent.getStringExtra( "text" );
        String url = intent.getStringExtra( Constants.IMAGE_URL);
        PhotoUtils.displayImageCacheElseNetwork(mImageView, path, url);
        mTextView.setText( text );
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "文字识别" );
        }
        mScrollView.smoothScrollTo( 0,0 );
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected( item );
    }
}
