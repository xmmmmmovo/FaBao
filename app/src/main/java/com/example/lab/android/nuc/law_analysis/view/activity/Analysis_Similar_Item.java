package com.example.lab.android.nuc.law_analysis.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.lab.android.nuc.law_analysis.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.ArrayList;
import java.util.List;

public class Analysis_Similar_Item  extends AppCompatActivity {
    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton text_to_voice,change_voice,fab_up;
    private String VOICE = null;
    List<String> permissionList = new ArrayList<>();
    private TextView titleTextView;
    private TextView contentTextView;
    String tvDetail;
    private String title;
    private String content;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis_item_show);
        titleTextView = (TextView) findViewById(R.id.anjian_title);
        contentTextView = (TextView)findViewById(R.id.anjian_content);
        contentTextView.setMovementMethod(new ScrollingMovementMethod());
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        content = bundle.getString("content");
        tvDetail = title + content;
        titleTextView.setText(title);
        contentTextView.setText(content);
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "相似案件" );
        }

        scrollView = (ScrollView) findViewById( R.id.scrollView_w );

        initFloatButton();
        //获取手机录音机使用权限，听写、识别、语义理解需要用到此权限
        if(ContextCompat.checkSelfPermission( Analysis_Similar_Item.this, Manifest.
                permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.RECORD_AUDIO );
        }
        //读取手机信息权限
        if(ContextCompat.checkSelfPermission( Analysis_Similar_Item.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.READ_PHONE_STATE );
        }
        //SD卡读写的权限（如果需要保存音频文件到本地的话）
        if(ContextCompat.checkSelfPermission( Analysis_Similar_Item.this, Manifest.
                permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.READ_EXTERNAL_STORAGE );
        }
        if (! permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions( Analysis_Similar_Item.this,permissions,1 );
        }else {
//            setRecognitionManager();
        }

    }




    //悬浮按钮的一些点击事件
    private void initFloatButton(){
        mFloatingActionsMenu = (FloatingActionsMenu) findViewById( R.id.detail_actions_menu );
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
                new BottomSheet.Builder( Analysis_Similar_Item.this)
                        .title( "选择声音种类" )
                        .sheet( R.menu.change_voice )
                        .listener( new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case R.id.xiaoyan:
                                        VOICE = "xiaoyan";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 小燕 女声 青年 中英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoyu:
                                        VOICE = "xiaoyu";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 小宇 男声 青年 中英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.henry:
                                        VOICE = "henry";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 亨利 男声 青年 英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vimary:
                                        VOICE = "vimary";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 玛丽 女声 青年 英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaomei:
                                        VOICE = "xiaomei";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 小梅 女声 青年 中英文粤语", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vixl:
                                        VOICE = "vixl";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 小莉 女声 青年 中英文台湾普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaorong:
                                        VOICE = "xiaorong";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 小蓉 女声 青年 汉语四川话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaokun:
                                        VOICE = "xiaokun";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 小坤 男声 青年 汉语 河南话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoqiang:
                                        VOICE = "xiaoqiang";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 小强 男声 青年 汉语湖南话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vixying:
                                        VOICE = "vixying";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 小莹 女声 青年 汉语陕西话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.nannan:
                                        VOICE = "nannan";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 楠楠 女声 童年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vils:
                                        VOICE = "vils";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 老孙 男声 老年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoxin:
                                        VOICE = "xiaoxin";
                                        Toast.makeText( Analysis_Similar_Item.this,"已选择 小新 男声 童年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.cancel_chose:
                                        Toast.makeText( Analysis_Similar_Item.this,"已取消声音切换", Toast.LENGTH_SHORT ).show();
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
                if (tvDetail != null && text_to_voice.getTitle().equals("文字播报")) {
                     SpeechSynthesizer( tvDetail);
                    SpeechSynthesizer.getSynthesizer().resumeSpeaking();
                    text_to_voice.setIcon( R.drawable.ic_pause);
                    text_to_voice.setTitle( "结束播报" );
                }else if (tvDetail!= null && text_to_voice.getTitle().equals("结束播报")){
                    if (SpeechSynthesizer.getSynthesizer().isSpeaking())
                        SpeechSynthesizer.getSynthesizer().pauseSpeaking();
                    text_to_voice.setIcon( R.drawable.ic_play );
                    text_to_voice.setTitle( "文字播报" );
                }
            }
        } );

        fab_up = (FloatingActionButton) findViewById( R.id.fab_up );
        fab_up.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
                mFloatingActionsMenu.toggle();
            }
        } );
    }


    /*-------------------------------语音合成--------------------------*/
    public void SpeechSynthesizer(String text){
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(Analysis_Similar_Item.this, null);

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
                Toast.makeText(Analysis_Similar_Item.this, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Analysis_Similar_Item.this, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
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


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText( this, "必须同意所有的权限才能使用本程序", Toast.LENGTH_SHORT ).show();
                            return;
                        }
                    }
//                    setRecognitionManager();
                }else {
                    Toast.makeText( this,"发生未知错误", Toast.LENGTH_SHORT ).show();
                }
                break;
            default:
        }
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
