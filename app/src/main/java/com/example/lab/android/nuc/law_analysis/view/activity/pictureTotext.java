package com.example.lab.android.nuc.law_analysis.view.activity;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.GeneralParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.cocosw.bottomsheet.BottomSheet;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.base.Result;
import com.example.lab.android.nuc.law_analysis.communication.activity.ImageDisplayActivity;
import com.example.lab.android.nuc.law_analysis.utils.tools.FileUtil;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.io.File;
import java.io.FileNotFoundException;

public class pictureTotext extends AppCompatActivity {
    private String VOICE = null;
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int REQUEST_CODE_CAMERA = 102;
    private TextView infoTextView;
    private ImageView imageView;
    private String filePath;

    /*与悬浮按钮相关*/
    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton text_to_voice,change_voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_picture_totext );
        initFloatButton();
        infoTextView = (TextView) findViewById(R.id.info_text_view);
        infoTextView.setTextIsSelectable( true );
        imageView = (ImageView) findViewById( R.id.image_view );

        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( pictureTotext.this,ImageDisplayActivity.class );
                intent.putExtra( "file_path",filePath );
                startActivity( intent );
            }
        } );

        findViewById(R.id.camera_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(pictureTotext.this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled( true );
            actionBar.setTitle( "法案提取" );
        }
    }

    //悬浮按钮的一些点击事件
    private void initFloatButton(){
        mFloatingActionsMenu = (FloatingActionsMenu) findViewById( R.id.main_actions_menu );
        change_voice = (FloatingActionButton) findViewById( R.id.change_voice );
        change_voice.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpeechSynthesizer.getSynthesizer() != null && SpeechSynthesizer.getSynthesizer().isSpeaking()){
                    SpeechSynthesizer.getSynthesizer().pauseSpeaking();
                    text_to_voice.setIcon( R.drawable.ic_play );
                    text_to_voice.setTitle( "文字播报" );
                }
                mFloatingActionsMenu.toggle();
                new BottomSheet.Builder(pictureTotext.this)
                        .title( "选择声音种类" )
                        .sheet( R.menu.change_voice )
                        .listener( new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case R.id.xiaoyan:
                                        VOICE = "xiaoyan";
                                        Toast.makeText( pictureTotext.this,"已选择 小燕 女声 青年 中英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoyu:
                                        VOICE = "xiaoyu";
                                        Toast.makeText( pictureTotext.this,"已选择 小宇 男声 青年 中英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.henry:
                                        VOICE = "henry";
                                        Toast.makeText( pictureTotext.this,"已选择 亨利 男声 青年 英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vimary:
                                        VOICE = "vimary";
                                        Toast.makeText( pictureTotext.this,"已选择 玛丽 女声 青年 英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaomei:
                                        VOICE = "xiaomei";
                                        Toast.makeText( pictureTotext.this,"已选择 小梅 女声 青年 中英文粤语", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vixl:
                                        VOICE = "vixl";
                                        Toast.makeText( pictureTotext.this,"已选择 小莉 女声 青年 中英文台湾普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaorong:
                                        VOICE = "xiaorong";
                                        Toast.makeText( pictureTotext.this,"已选择 小蓉 女声 青年 汉语四川话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaokun:
                                        VOICE = "xiaokun";
                                        Toast.makeText( pictureTotext.this,"已选择 小芸 女声 青年 汉语东北话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoqiang:
                                        VOICE = "xiaoqiang";
                                        Toast.makeText( pictureTotext.this,"已选择 小强 男声 青年 汉语湖南话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vixying:
                                        VOICE = "vixying";
                                        Toast.makeText( pictureTotext.this,"已选择 小莹 女声 青年 汉语陕西话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.nannan:
                                        VOICE = "nannan";
                                        Toast.makeText( pictureTotext.this,"已选择 楠楠 女声 童年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vils:
                                        VOICE = "vils";
                                        Toast.makeText( pictureTotext.this,"已选择 老孙 男声 老年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoxin:
                                        VOICE = "xiaoxin";
                                        Toast.makeText( pictureTotext.this,"已选择 小新 男声 童年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.cancel_chose:
                                        Toast.makeText( pictureTotext.this,"已取消声音切换", Toast.LENGTH_SHORT ).show();
                                        break;

                                }
                            }
                        } ).show();
            }
        } );

        text_to_voice = (FloatingActionButton) findViewById( R.id.action_text_to_voice );
        text_to_voice.setIcon( R.drawable.ic_play  );
        text_to_voice.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatingActionsMenu.toggle();
                if (infoTextView.getText() != null && text_to_voice.getTitle().equals("文字播报")) {
                    SpeechSynthesizer( infoTextView.getText().toString());
                    SpeechSynthesizer.getSynthesizer().resumeSpeaking();
                    text_to_voice.setIcon( R.drawable.ic_pause);
                    text_to_voice.setTitle( "结束播报" );
                }else if (infoTextView.getText() != null && text_to_voice.getTitle().equals("结束播报")){
                    if (SpeechSynthesizer.getSynthesizer().isSpeaking())
                        SpeechSynthesizer.getSynthesizer().pauseSpeaking();
                    text_to_voice.setIcon( R.drawable.ic_play );
                    text_to_voice.setTitle( "文字播报" );
                }
            }
        } );

    }


    /*-------------------------------语音合成--------------------------*/
    public void SpeechSynthesizer(String text){
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(pictureTotext.this, null);

        /**
         2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
         *
         */
        // 清空参数
        mTts.setParameter( SpeechConstant.PARAMS, null);
        mTts.setParameter( SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        if (VOICE != null){
            mTts.setParameter( SpeechConstant.VOICE_NAME, VOICE);//设置发音人
        }else {
            mTts.setParameter( SpeechConstant.VOICE_NAME, "xiaoyan" );//设置发音人
        }

        mTts.setParameter( SpeechConstant.SPEED, "50");//设置语速
        //设置合成音调
        mTts.setParameter( SpeechConstant.PITCH, "50");
        mTts.setParameter( SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter( SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter( SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        boolean isSuccess = mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts2.wav");
//        Toast.makeText(MainActivity.this, "语音合成 保存音频到本地：\n" + isSuccess, Toast.LENGTH_LONG).show();
        //3.开始合成
        int code = mTts.startSpeaking(text, mSynListener);

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //上面的语音配置对象为初始化时：
                Toast.makeText(pictureTotext.this, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(pictureTotext.this, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
            }
        }
    }
    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {

        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }
        //开始播放
        public void onSpeakBegin() {
        }
        //暂停播放
        public void onSpeakPaused() {
        }
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }
        //恢复播放回调接口
        public void onSpeakResumed() {
        }
        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (imageView.getVisibility() == View.GONE) {
                imageView.setVisibility( View.VISIBLE );
            }
            filePath = getRealPathFromURI(uri);
            try {
                Bitmap bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream(  uri) );
                imageView.setImageBitmap( bitmap );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            recGeneral(filePath);
        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
            if (imageView.getVisibility() == View.GONE) {
                imageView.setVisibility( View.VISIBLE );
            }
            Bitmap bitmap = BitmapFactory.decodeFile( filePath );
            imageView.setImageBitmap( bitmap );
            recGeneral(filePath);

        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                    this.finish();
                    Result.setResult(infoTextView.getText().toString() );
                    return true;
                }
                return super.onOptionsItemSelected( item );
            }

            private void recGeneral(String filePath) {
                GeneralParams param = new GeneralParams();
                param.setDetectDirection(true);
                param.setImageFile(new File(filePath));
                OCR.getInstance(this).recognizeAccurate(param, new OnResultListener<GeneralResult>() {
                    @Override
                    public void onResult(GeneralResult result) {
                        StringBuilder sb = new StringBuilder();
                        for (WordSimple word : result.getWordList()) {
                            sb.append(word.getWords());
                    sb.append("\n");
                }
                infoTextView.setText(sb);
            }

            @Override
            public void onError(OCRError error) {
                infoTextView.setText(error.getMessage());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (SpeechSynthesizer.getSynthesizer() != null && SpeechSynthesizer.getSynthesizer().isSpeaking()){
            SpeechSynthesizer.getSynthesizer().stopSpeaking();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (SpeechSynthesizer.getSynthesizer() != null) {
            SpeechSynthesizer.getSynthesizer().destroy();
        }
    }


}
