package com.example.lab.android.nuc.law_analysis.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lab.android.nuc.law_analysis.adapter.PageAdapter;
import com.example.lab.android.nuc.law_analysis.util.tools.PermissionUtil;
import com.example.lab.android.nuc.law_analysis.util.views.CanaroTextView;
import com.example.lab.android.nuc.new_idea.R;
import com.qintong.library.InsLoadingView;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;
    private InsLoadingView dingView;
    private View guillotineMenu;
    private View contentHamburger;
    private Toolbar toolbar;
    private boolean isOpenMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initUI();
        requestPermissions();
    }
    private void initUI(){
         toolbar = (Toolbar) findViewById( R.id.toolbar );
         contentHamburger = findViewById( R.id.content_hamburger );
         if (toolbar != null) {
             setSupportActionBar( toolbar );
             getSupportActionBar().setTitle( null );
         }
        FrameLayout root = (FrameLayout) findViewById( R.id.root );
        guillotineMenu = LayoutInflater.from(this).inflate(R.layout.shine_toolbar, null);
        root.addView(guillotineMenu);
        dingView = (InsLoadingView) guillotineMenu.findViewById( R.id.loading_view);
        dingView.setStatus( InsLoadingView.Status.LOADING );
        startAnimin();
        final ViewPager viewPager = (ViewPager) findViewById( R.id.vp_horizontal_ntb );
        PageAdapter adapter = new PageAdapter( getSupportFragmentManager(),this );
        viewPager.setAdapter( adapter );

        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("条目查询")
                        .badgeTitle("new")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
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
        navigationTabBar.setViewPager(viewPager, 2);
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
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};

        PermissionUtil.requestPermissions(this, permissions, new PermissionUtil.OnRequestPermissionsListener() {
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


}
