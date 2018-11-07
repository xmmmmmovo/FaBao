package com.example.lab.android.nuc.law_analysis.news.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.application.Config;
import com.example.lab.android.nuc.law_analysis.news.bean.NewsDetailBean;
import com.example.lab.android.nuc.law_analysis.news.util.ListUtils;
import com.example.lab.android.nuc.law_analysis.news.util.SnackBarUtil;
import com.example.lab.android.nuc.law_analysis.news.util.WebUtil;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zzhoujay.richtext.RichText;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/*
新闻的具体界面
 */
public class NewsDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    List<String> permissionList = new ArrayList<>(  );
    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ScrollView scrollView;
    private String VOICE = null;
    private String postId;
    private String title;
    private NewsDetailBean bean;
    /*与悬浮按钮相关*/
    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton text_to_voice,change_voice,fab_up;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news_detail );
        toolbar = (Toolbar) findViewById( R.id.toolbar_news );
        tvTitle = (TextView) findViewById( R.id.tv_title );
        tvDetail = (TextView) findViewById(R.id.news_detail_body );
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById( R.id.swipefreshlayout );
        scrollView = (ScrollView ) findViewById( R.id.scrollView_news );
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById( R.id.swipefreshlayout );
        init();
        //悬浮按钮
        initFloatButton();
        //获取手机录音机使用权限，听写、识别、语义理解需要用到此权限
        if(ContextCompat.checkSelfPermission( NewsDetailActivity.this, Manifest.
                permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.RECORD_AUDIO );
        }
        //读取手机信息权限
        if(ContextCompat.checkSelfPermission( NewsDetailActivity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.READ_PHONE_STATE );
        }
        //SD卡读写的权限（如果需要保存音频文件到本地的话）
        if(ContextCompat.checkSelfPermission( NewsDetailActivity.this, Manifest.
                permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.READ_EXTERNAL_STORAGE );
        }
        if (! permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions( NewsDetailActivity.this,permissions,1 );
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
                new BottomSheet.Builder( NewsDetailActivity.this)
                        .title( "选择声音种类" )
                        .sheet( R.menu.change_voice )
                        .listener( new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case R.id.xiaoyan:
                                        VOICE = "xiaoyan";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 小燕 女声 青年 中英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoyu:
                                        VOICE = "xiaoyu";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 小宇 男声 青年 中英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.henry:
                                        VOICE = "henry";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 亨利 男声 青年 英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vimary:
                                        VOICE = "vimary";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 玛丽 女声 青年 英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaomei:
                                        VOICE = "xiaomei";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 小梅 女声 青年 中英文粤语", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vixl:
                                        VOICE = "vixl";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 小莉 女声 青年 中英文台湾普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaorong:
                                        VOICE = "xiaorong";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 小蓉 女声 青年 汉语四川话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaokun:
                                        VOICE = "xiaokun";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 小坤 男声 青年 汉语 河南话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoqiang:
                                        VOICE = "xiaoqiang";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 小强 男声 青年 汉语湖南话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vixying:
                                        VOICE = "vixying";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 小莹 女声 青年 汉语陕西话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.nannan:
                                        VOICE = "nannan";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 楠楠 女声 童年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vils:
                                        VOICE = "vils";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 老孙 男声 老年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoxin:
                                        VOICE = "xiaoxin";
                                        Toast.makeText( NewsDetailActivity.this,"已选择 小新 男声 童年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.cancel_chose:
                                        Toast.makeText( NewsDetailActivity.this,"已取消声音切换", Toast.LENGTH_SHORT ).show();
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
                if (tvDetail.getText() != null && text_to_voice.getTitle().equals("文字播报")) {
                    SpeechSynthesizer( tvDetail.getText().toString());
                    SpeechSynthesizer.getSynthesizer().resumeSpeaking();
                    text_to_voice.setIcon( R.drawable.ic_pause);
                    text_to_voice.setTitle( "结束播报" );
                }else if (tvDetail.getText() != null && text_to_voice.getTitle().equals("结束播报")){
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

    protected void init() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.black));
        swipeRefreshLayout.setOnRefreshListener(this);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_toolbar_back));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        postId = getIntent().getStringExtra("postid");
        title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
        getDetail();
    }

    public void getDetail() {
      OkGo.get( Config.getNewsDetailUrl( postId ) )
              .tag( this )
              .execute( new StringCallback() {
                  @Override
                  public void onSuccess(String s, Call call, Response response) {
                      try {
                          Gson gson = new Gson();
                          JsonParser parser = new JsonParser();
                          JsonObject jsonObj = parser.parse(s).getAsJsonObject();
                          JsonElement jsonElement = jsonObj.get(postId);
                          if (null == jsonElement) {
                              SnackBarUtil.showSnackBar(R.string.loadfail, toolbar, NewsDetailActivity.this);
                          } else {
                              JsonObject jsonObject = jsonElement.getAsJsonObject();
                              bean = gson.fromJson(jsonObject, NewsDetailBean.class);
                              setDetail(bean);
                          }
                      } catch (Exception e) {
                          SnackBarUtil.showSnackBar(R.string.loadfail, toolbar, NewsDetailActivity.this);
                      }
                      if (null != swipeRefreshLayout) {
                          swipeRefreshLayout.setRefreshing(false);
                      }
                  }

                  @Override
                  public void onError(okhttp3.Call call, Response response, Exception e) {
                      super.onError(call, response, e);
                      SnackBarUtil.showSnackBar(e.getMessage(), toolbar, NewsDetailActivity.this);
                      if (null != swipeRefreshLayout) {
                          swipeRefreshLayout.setRefreshing(false);
                      }
                  }
              } );

    }

    public void setDetail(NewsDetailBean bean) {
        RichText.from(handleRichText(bean))
                .bind( this )
                .placeHolder( R.drawable.ic_default )
                .singleLoad( false )
                .into(tvDetail);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_webview:
                WebUtil.openWeb(NewsDetailActivity.this, bean.getTitle(), bean.getShareLink());
                break;
            case R.id.item_browser:
                Uri uri = Uri.parse(bean.getShareLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case android.R.id.home:
                // 回退
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        setIconEnable(menu, true);//让在overflow中的menuitem的icon显示
        return super.onPrepareOptionsPanel(view, menu);
    }



    /**
     * 利用反射机制调用MenuBuilder中的setOptionIconsVisable（），
     * 如果是集成自AppCompatActivity则不行,需要在onPreareOptionPanel（）中调用该方法
     *
     * @param menu   该menu实质为MenuBuilder，该类实现了Menu接口
     * @param enable enable为true时，菜单添加图标有效，enable为false时无效。因为4.0系统之后默认无效
     */

    private void setIconEnable(Menu menu, boolean enable) {
        if (menu != null) {
            try {
                Class clazz = menu.getClass();
                if (clazz.getSimpleName().equals("MenuBuilder")) {
                    Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
                    m.invoke(menu, enable);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理富文本包含图片的情况
     *
     * @param newsDetailBean 原始数据
     */
    private String handleRichText(NewsDetailBean newsDetailBean) {
        String detailBody;
        if (!ListUtils.isEmpty(newsDetailBean.getImg())) {
            String body = newsDetailBean.getBody();
            for (NewsDetailBean.ImgBean imgEntity : newsDetailBean.getImg()) {
                String ref = imgEntity.getRef();
                String src = imgEntity.getSrc();
                String img = HTML_IMG_TEMPLATE.replace("http", src);
                Log.i( "wanghao",img );
                body = body.replaceAll(ref, img);
            }
            detailBody = body;
        } else {
            detailBody = newsDetailBean.getBody();
        }
        return detailBody;
    }

    private static final String HTML_IMG_TEMPLATE = "<img src=\"http\" />";

    @Override
    public void onRefresh() {
        getDetail();
    }



    /*-------------------------------语音合成--------------------------*/
    public void SpeechSynthesizer(String text){
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(NewsDetailActivity.this, null);

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
                Toast.makeText(NewsDetailActivity.this, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(NewsDetailActivity.this, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
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


}
