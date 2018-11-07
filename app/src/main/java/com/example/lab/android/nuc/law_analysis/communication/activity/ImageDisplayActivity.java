package com.example.lab.android.nuc.law_analysis.communication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.GeneralParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.example.lab.android.nuc.law_analysis.communication.utils.Constants;
import com.example.lab.android.nuc.law_analysis.communication.utils.PhotoUtils;
import com.example.lab.android.nuc.law_analysis.R;

import java.io.File;


public class ImageDisplayActivity extends Activity {

    private ImageView imageView;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_image_brower_layout);
        imageView = (ImageView) findViewById( R.id.imageView);
        mProgressBar = (ProgressBar) findViewById( R.id.progressbar );
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        final String path = intent.getStringExtra( Constants.IMAGE_LOCAL_PATH);
        String url = intent.getStringExtra(Constants.IMAGE_URL);
        PhotoUtils.displayImageCacheElseNetwork(imageView, path, url);
        findViewById(R.id.lly_image_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        imageView.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new ActionSheetDialog( ImageDisplayActivity.this )
                        .builder()
                        .setCancelable( false )
                        .setCanceledOnTouchOutside( false )
                        .addSheetItem( "提取图中文字", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        mProgressBar.setVisibility( View.VISIBLE );
                                        recGeneral( path );
                                    }
                                } )
                        .addSheetItem( "保存到手机", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText( ImageDisplayActivity.this, "图片已保存至" + path, Toast.LENGTH_SHORT ).show();
                                    }
                                } )
                        .addSheetItem( "收藏", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        Toast.makeText( ImageDisplayActivity.this, "已收藏", Toast.LENGTH_SHORT ).show();
                                    }
                                } ).show();
                return false;
            }
        } );
    }

    private void recGeneral(final String filePath) {
        final GeneralParams param = new GeneralParams();
        param.setDetectDirection(true);
        param.setImageFile(new File(filePath));
        OCR.getInstance(this).recognizeGeneral(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                StringBuilder sb = new StringBuilder();
                for (WordSimple word : result.getWordList()) {
                    sb.append(word.getWords());
                    sb.append("\n");
                }
                mProgressBar.setVisibility( View.GONE );
                Intent intent = new Intent( ImageDisplayActivity.this,PictureTextActivity.class );
                intent.putExtra( "path",filePath );
                intent.putExtra( "text",sb.toString() );
                startActivity( intent );
            }

            @Override
            public void onError(OCRError error) {
                Toast.makeText( ImageDisplayActivity.this, error.toString(), Toast.LENGTH_SHORT ).show();
            }
        });
    }

}
