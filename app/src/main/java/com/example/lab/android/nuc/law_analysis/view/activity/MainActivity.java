package com.example.lab.android.nuc.law_analysis.view.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.bumptech.glide.Glide;
import com.cocosw.bottomsheet.BottomSheet;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.MainAdapter;
import com.example.lab.android.nuc.law_analysis.adapter.PageAdapter;
import com.example.lab.android.nuc.law_analysis.application.MyApplication;
import com.example.lab.android.nuc.law_analysis.communication.utils.PathUtils;
import com.example.lab.android.nuc.law_analysis.news.util.SnackBarUtil;
import com.example.lab.android.nuc.law_analysis.utils.iflytek.IflytekSpeech;
import com.example.lab.android.nuc.law_analysis.utils.iflytek.Iflytekrecognize;
import com.example.lab.android.nuc.law_analysis.utils.iflytek.iflytekWakeUp;
import com.example.lab.android.nuc.law_analysis.utils.iflytek.resultresolve;
import com.example.lab.android.nuc.law_analysis.utils.tools.PermissionUtil;
import com.example.lab.android.nuc.law_analysis.utils.views.CanaroTextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qintong.library.InsLoadingView;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AlertDialog.Builder alertDialog;
    //语音唤醒
    public Iflytekrecognize rec;
    public static iflytekWakeUp wkup;
    private boolean ison = false;
    public IflytekSpeech spe;
    private boolean iswake = true;

    //aip.lecence初始话
    private boolean hasGotToken = false;
    private static final long RIPPLE_DURATION = 250;
    private InsLoadingView dingView;
    private View guillotineMenu;
    private View contentHamburger;
    private Toolbar toolbar;
    private boolean isOpenMenu = false;
    private FrameLayout root;
    private CanaroTextView toolbarText;
    private Button profileBtn,feedbackBtn,settingsbtn,aboutBtn;
    //照相调用变量
    private Intent openCameraIntent;
    private String camPicPath;
    private Uri uri,imageUri;
    private ContentValues contentValues;
    private final static int CAMERA_REQUEST = 2;

    private ImageView mImageView;

    // 记录第一次点击的时间
    private long clickTime = 0;


    public resultresolve resolver =new resultresolve()
    {
        @Override
        public void resolveresult(String str) {
            if(str.equals("speechover"))
            {
                if(ison){
                    ison=false;
                    rec.listening();
                }
                else {
                    wkup.startWakeuper();
                }
            }
            else{
                if(str.equals("waked"))
                {
                    ison=true;
                    spe.Speek("法宝时刻守护着您，您需要查什么案件或法律么");
                    wkup.startWakeuper();
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initUI();
        requestPermissions();
        alertDialog = new AlertDialog.Builder(this);
        //文字提取初始化
        initAccessToken();
        wkup = new iflytekWakeUp(this,resolver);
        rec = new Iflytekrecognize(this,resolver);
        spe = new IflytekSpeech(this,resolver);
        wkup.startWakeuper();
    }

    private void initUI(){
        toolbar = (Toolbar) findViewById( R.id.toolbar_main );
        contentHamburger = findViewById( R.id.content_hamburger );
        if (toolbar != null) {
            setSupportActionBar( toolbar );
            getSupportActionBar().setTitle( null );
        }
        root = (FrameLayout) findViewById( R.id.root );
        guillotineMenu = LayoutInflater.from(this).inflate(R.layout.shine_toolbar, null);
        root.addView(guillotineMenu);
        mImageView = (ImageView) findViewById( R.id.wakeup );
        mImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iswake){
                    mImageView.setImageResource( R.drawable.wakeupp );
                    Toast.makeText( MainActivity.this, "语音唤醒已开启", Toast.LENGTH_SHORT ).show();
                    wkup = new iflytekWakeUp(MainActivity.this,resolver);
                    rec = new Iflytekrecognize(MainActivity.this,resolver);
                    spe = new IflytekSpeech(MainActivity.this,resolver);
                    wkup.startWakeuper();
                    iswake = false;
                }else {
                    mImageView.setImageResource( R.drawable.wakeup );
                    Toast.makeText( MainActivity.this, "语音唤醒已关闭", Toast.LENGTH_SHORT ).show();
                    wkup.destroyWakeuper();
                    iswake = true;
                }
            }
        } );
        dingView = (InsLoadingView) guillotineMenu.findViewById( R.id.loading_view);
        dingView.setStatus( InsLoadingView.Status.LOADING );
        dingView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheet.Builder( MainActivity.this )
                        .title("请选择头像来源")
                        .sheet(R.menu.select_pic_menu)
                        .listener( new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case R.id.camera_chose:
                                        openCameraIntent = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                        camPicPath = PathUtils.getSavePicPath( MainActivity.this);
                                        if (Build.VERSION.SDK_INT < 24){//根据安卓版本适配
                                            uri = Uri.fromFile(new File(camPicPath));
                                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                            startActivityForResult(openCameraIntent, CAMERA_REQUEST);
                                        }else{//适配7.0
                                            contentValues = new ContentValues(1);
                                            contentValues.put(MediaStore.Images.Media.DATA, camPicPath);
                                            uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                    contentValues);
                                            grantUriPermission( "com.example.lab.android.nuc.chat",uri,Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                                            openCameraIntent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
                                            openCameraIntent.addFlags( Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                            openCameraIntent.putExtra( MediaStore.EXTRA_OUTPUT,uri);
                                            startActivityForResult( openCameraIntent,CAMERA_REQUEST);
                                        }
                                        break;
                                    case R.id.gallery_chose:
                                        PictureSelector
                                                .create(MainActivity.this)
                                                .openGallery( PictureMimeType.ofImage())
                                                .imageSpanCount(4)
                                                .selectionMode( PictureConfig.SINGLE)
                                                .previewImage(true)
                                                .compress(false)
                                                .isCamera(false)
                                                .forResult(PictureConfig.CHOOSE_REQUEST);
                                        break;
                                    case R.id.cancel_chose:
                                        Toast.makeText( MainActivity.this, "已取消", Toast.LENGTH_SHORT ).show();
                                        break;
                                }
                            }
                        } ).show();
            }
        } );
        profileBtn = (Button) guillotineMenu.findViewById( R.id.profile );
        feedbackBtn = (Button) guillotineMenu.findViewById( R.id.feed );
        settingsbtn = (Button) guillotineMenu.findViewById( R.id.settings);
        aboutBtn = (Button) guillotineMenu.findViewById( R.id.about );
        aboutBtn.setOnClickListener( this );
        settingsbtn.setOnClickListener( this );
        feedbackBtn.setOnClickListener( this );
        profileBtn.setOnClickListener( this );
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
        startAnimin();
        final ViewPager viewPager = (ViewPager) findViewById( R.id.vp_horizontal_ntb );
        PageAdapter adapter = new PageAdapter( getSupportFragmentManager(),this );
        viewPager.setAdapter( adapter );
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_searchs),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_searchs1))
                        .title("条目查询")
                        .badgeTitle("new")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_news2),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_news))
                        .title("法律新闻")
                        .badgeTitle("new")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("法律分析")
                        .badgeTitle("new")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.communication),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("社群")
                        .badgeTitle("new")
                        .build()
        );
        models.add(//典型案例
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.platform_normal),
                        Color.parseColor(colors[4]))
                        .selectedIcon(getResources().getDrawable(R.drawable.platform_begin))
                        .title("法律讲坛")
                        .badgeTitle("new")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setBehaviorEnabled(true);
        navigationTabBar.setOnTabBarSelectedIndexListener( new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {

            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                model.hideBadge();
            }
        } );

        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }

    private void startAnimin() {
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
        isOpenMenu = !isOpenMenu;
    }

    //覆写返回逻辑
    @Override
    public void onBackPressed() {
        if (isOpenMenu){
            startAnimin();
        }else {
            super.onBackPressed();
        }
    }

    private void requestPermissions() {
        PermissionUtil.requestPermissions(this,new PermissionUtil.OnRequestPermissionsListener() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "所有权限均已同意", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied() {
                AlertDialog deniedDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("错误！")
                        .setMessage("有权限未同意!")
                        .setCancelable(false)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile:
                Toast.makeText( this, "profile group", Toast.LENGTH_SHORT ).show();
                break;
            case R.id.feed:
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "1484290617@qq.com", null));
                intent.putExtra(Intent.EXTRA_EMAIL, "1484290617@qq.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "意见反馈");
                startActivity(Intent.createChooser(intent, "意见反馈"));
                startActivity( intent );
                break;
            case R.id.settings:
                break;
            case R.id.about:
                Intent intent_about = new Intent( MainActivity.this,AboutActivity.class );
                startActivity( intent_about );
                break;
            default:
                break;
        }
    }

    /**
     * 以license文件方式初始化
     */
    private void initAccessToken() {
        OCR.getInstance(this).initAccessToken( new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("licence方式获取token失败", error.getMessage());
            }
        }, getApplicationContext());
    }

    private void alertText(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - clickTime) > 2000) {
                SnackBarUtil.showSnackBar( R.string.exit, root, MainActivity.this );
                clickTime = System.currentTimeMillis();
            } else {
                MyApplication.getInstance().exitApp();
            }
            return true;
        }
        return super.onKeyDown( keyCode, event );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CAMERA_REQUEST:
                FileInputStream is = null;
                try {
                    is = new FileInputStream(camPicPath);
                    File camFile = new File(camPicPath); // 图片文件路径
                    if (camFile.exists()) {
                        Glide.with( this ).load( camFile ).into( dingView );
                    } else {
                        Toast.makeText(this, "该文件不存在", Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    // 关闭流
                    try {
                        is.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            case PictureConfig.CHOOSE_REQUEST:
                // 图片选择结果回调
                if (PictureSelector.obtainMultipleResult( data ) != null) {
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    LocalMedia media = selectList.get(0);
                    String path = media.getPath();

                    File file = new File( path );
                    Uri uri = Uri.fromFile( file );
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap( this.getContentResolver(), uri );
                        dingView.setImageBitmap( bitmap );
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }else {
                    dingView.setImageResource( R.drawable.icon);
                }
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
//                    mDesignCenterView.refreshSelectedPicture(selectList);
                //resizeImage(Uri.parse(path));
//                updateAvatar(path);
                break;
            default:
                break;
        }
    }


}
