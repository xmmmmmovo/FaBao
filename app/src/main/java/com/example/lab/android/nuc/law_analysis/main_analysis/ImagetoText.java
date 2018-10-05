package com.example.lab.android.nuc.law_analysis.main_analysis;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.example.lab.android.nuc.law_analysis.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImagetoText  extends AppCompatActivity {

   private FloatingActionButton button_PaiZhao;

    String AK = "0MzGKRNmALB5P7ErEGocQ7wZ";
    String SK ="G2P1W3gaXTV4KuAtp9ZZnBxLOz06fj5D";

    private Uri imageUri;

    private static final int TAKE_PHOTO = 1;
    private static final int CROP_PHOTO = 2;

    private EditText editText_Analysis;

    Bitmap bitmap;
    private boolean hasGotToken = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_analysis_fragment);

       button_PaiZhao = findViewById(R.id.bt_paizhao);
       editText_Analysis = findViewById(R.id.edittext_analyis);

                       File outputImage = new File(Environment.getExternalStorageDirectory(),
                               "tempImage" + ".jpg");
                       try {
                           if (outputImage.exists()) {
                               outputImage.delete();
                           }
                           outputImage.createNewFile();

                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       imageUri = Uri.fromFile(outputImage);
                       Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                       intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                       startActivityForResult(intent, TAKE_PHOTO);


        initAccessTokenWithAkSk();
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    Currency();
//                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序

                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));
                        Currency();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }



    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
            }
            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                Log.i("ImagetoText.this", "onError: "+error.toString());
               // alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getApplicationContext(),  AK, SK);

    }

    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    public void Currency(){
        final StringBuffer sb=new StringBuffer();
        // 通用文字识别参数设置
        GeneralBasicParams param = new GeneralBasicParams();
        param.setDetectDirection(true);
        String str=getRealFilePath(this,imageUri);
        Log.e("TGA",str+"------str-------------");
        param.setImageFile(new File(getRealFilePath(this,imageUri)));

// 调用通用文字识别服务
        OCR.getInstance(this).recognizeGeneralBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                // 调用成功，返回GeneralResult对象
                for (WordSimple wordSimple : result.getWordList()) {
                    // wordSimple不包含位置信息
                    sb.append(wordSimple.getWords());
                    sb.append("\n");
                }
                editText_Analysis.setText(sb.toString());
                // json格式返回字符串
//                listener.onResult(result.getJsonRes());
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError对象
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
        OCR.getInstance(this).release();
    }


}
