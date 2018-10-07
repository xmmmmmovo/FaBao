package com.example.lab.android.nuc.law_analysis.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.PageAdapter;
import com.example.lab.android.nuc.law_analysis.application.MyApplication;
import com.example.lab.android.nuc.law_analysis.news.util.SnackBarUtil;
import com.example.lab.android.nuc.law_analysis.utils.tools.PermissionUtil;
import com.example.lab.android.nuc.law_analysis.utils.views.CanaroTextView;
import com.qintong.library.InsLoadingView;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private AlertDialog.Builder alertDialog;
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

    // 记录第一次点击的时间
    private long clickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initUI();
        requestPermissions();
        alertDialog = new AlertDialog.Builder(this);
        //文字提取初始化
        initAccessToken();
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
        dingView = (InsLoadingView) guillotineMenu.findViewById( R.id.loading_view);
        dingView.setStatus( InsLoadingView.Status.LOADING );
        final LinearLayout linearLayout = guillotineMenu.findViewById( R.id.profile_group);
        linearLayout.setOnClickListener( this);
        LinearLayout feedBack = guillotineMenu.findViewById( R.id.feed_group );
        feedBack.setOnClickListener( this );
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
            case R.id.profile_group:
                Toast.makeText( this, "profile group", Toast.LENGTH_SHORT ).show();
                break;
            case R.id.feed_group:
                Intent intent = new Intent( MainActivity.this,LoginActivity.class );
                startActivity( intent );
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
}
