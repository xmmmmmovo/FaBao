package com.example.lab.android.nuc.law_analysis.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.GridImageAdapter;
import com.example.lab.android.nuc.law_analysis.utils.manager.FullyGridLayoutManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DynamicActivity extends AppCompatActivity {

    private final static String TAG = "dynamic_item_activity";
    private List<LocalMedia> selectList = new ArrayList<>(  );
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private int maxSelecNum = 9;
    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();

    private EditText mEditText;
    private RecyclerView mRecycler;
    private LinearLayout mLyImage;
    private LinearLayout mLyVideo;
    private LinearLayout mLyAiTe;
    private LinearLayout mLyTopic;
    private LinearLayout mLyAddArticle;

    //Toolbar
    private ImageView backView;
    private TextView sendView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dynamic );
        themeId = R.style.picture_default_style;

        mEditText  = (EditText) findViewById( R.id.et_content );
        mLyImage = (LinearLayout) findViewById(R.id.ly_image);
        mLyVideo = (LinearLayout) findViewById(R.id.ly_video);
        mLyAiTe = (LinearLayout) findViewById(R.id.ly_ai_te);
        mLyTopic = (LinearLayout) findViewById(R.id.ly_topic);
        mLyAddArticle = (LinearLayout) findViewById(R.id.ly_add_article);
        recyclerView = findViewById(R.id.recycler);

        backView = (ImageView) findViewById( R.id.toolbar_back);
        sendView = (TextView) findViewById( R.id.toolbar_send );
        backView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder( DynamicActivity.this );
                dialog.setMessage( "退出此次编辑?" );
                dialog.setCancelable( true );
                dialog.setPositiveButton( "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        //将此次编辑动态添加到动态list当中,在此次编写逻辑
                    }
                } );
                dialog.setNegativeButton( "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                } );
                dialog.show();
            }
        } );
        sendView.setOnClickListener( new View.OnClickListener() {

            //获取当前时间
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "MM月dd日 HH:mm:ss" );
            Date date = new Date( System.currentTimeMillis() );

            @Override
            public void onClick(View v) {

            }
        } );

        loadDate();

        FullyGridLayoutManager manager = new FullyGridLayoutManager( DynamicActivity.this,
                3, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager( manager );
        adapter = new GridImageAdapter( DynamicActivity.this,onAddPicClickListener);
        adapter.setList( selectList );
        recyclerView.setAdapter( adapter );
        adapter.setOnItemClickListener( new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0){
                    LocalMedia media = selectList.get( position );
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo( pictureType);
                    switch (mediaType){
                        case 1:
                            // 预览图片 可自定长按保存路径
//                            PictureSelector.create(DynamicActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(DynamicActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(DynamicActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(DynamicActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        } );
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions( this);
        permissions.request( Manifest.permission.WRITE_EXTERNAL_STORAGE ).subscribe( new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(DynamicActivity.this);
                } else {
                    Toast.makeText(DynamicActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        } );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    private void loadDate() {
        mLyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonAction(PictureMimeType.ofImage());
            }
        });

        mLyVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonAction(PictureMimeType.ofVideo());
            }
        });

        mLyAiTe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DynamicActivity.this,SelectLawyerActivity.class );
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        mLyTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DynamicActivity.this, SelectTopicActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
        mLyAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DynamicActivity.this,SelectArticleActivity.class );
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });
    }


    private void commonAction(int type) {
        PictureSelector.create(DynamicActivity.this)
                .openGallery(type)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_Sina_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
//                    themeId = R.style.picture_default_style;   默认样式
//                    themeId = R.style.picture_white_style;
//                    themeId = R.style.picture_QQ_style;
//                    themeId = R.style.picture_Sina_style;
                .maxSelectNum(9)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .selectionMode(
                        PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(true)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            if (true){
                commonAction( PictureMimeType.ofImage());
            }else {
                // 单独拍照
                PictureSelector.create( DynamicActivity.this )
                        .openCamera( chooseMode )// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .theme(themeId)// 主题样式设置 具体参考 values/styles
//                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
//                        .minSelectNum(1)// 最小选择数量
//                        .selectionMode(cb_choose_mode.isChecked() ?
//                                PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选 or 单选
//                        .previewImage(cb_preview_img.isChecked())// 是否可预览图片
//                        .previewVideo(cb_preview_video.isChecked())// 是否可预览视频
//                        .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
//                        .isCamera(cb_isCamera.isChecked())// 是否显示拍照按钮
//                        .enableCrop(cb_crop.isChecked())// 是否裁剪
//                        .compress(cb_compress.isChecked())// 是否压缩
                        .glideOverride(160, 160) // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                        .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
//                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                        .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                        .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                        .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                        .selectionMedia(selectList) // 是否传入已选图片
//                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
//                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        .cropCompressQuality(90) // 裁剪压缩质量 默认为100
                        .minimumCompressSize(100) // 小于100kb的图片不压缩
//                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                        .rotateEnabled() // 裁剪是否可旋转图片
//                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        .videoQuality(1)// 视频录制质量 0 or 1
//                        //.videoSecond()////显示多少秒以内的视频or音频也可适用
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//            }
                ;
            }
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            if (bundle.getString( "ai_te") != null)
                mEditText.setText( mEditText.getText() + "@" + bundle.getString( "ai_te" ) );
            if (bundle.getString("topic") != null)
                mEditText.setText(mEditText.getText() + "#" + bundle.getString("topic")+"#");
            if (bundle.getString("article") != null)
                mEditText.setText(mEditText.getText() + "-" + bundle.getString("article")+"-");

            //每次添加内容后光标到末尾
            Editable editable = mEditText.getText();
            Selection.setSelection( editable,editable.length());

            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    public String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
}
