package com.example.lab.android.nuc.new_idea.view.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab.android.nuc.new_idea.adapter.PageAdapter;
import com.example.lab.android.nuc.new_idea.util.views.CanaroTextView;
import com.example.lab.android.nuc.new_idea.R;
import com.qintong.library.InsLoadingView;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;
    InsLoadingView dingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initUI();
    }
    private void initUI(){
         Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
         View contentHamburger = findViewById( R.id.content_hamburger );
         if (toolbar != null) {
             setSupportActionBar( toolbar );
             getSupportActionBar().setTitle( null );
         }
        FrameLayout root = (FrameLayout) findViewById( R.id.root );
        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.shine_toolbar, null);
        root.addView(guillotineMenu);
        dingView = (InsLoadingView) guillotineMenu.findViewById( R.id.loading_view);
        dingView.setStatus( InsLoadingView.Status.LOADING );
        final LinearLayout linearLayout = guillotineMenu.findViewById( R.id.profile_group);
        linearLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( MainActivity.this, "Profile OnClicked", Toast.LENGTH_SHORT ).show();
                CanaroTextView view = (CanaroTextView) findViewById( R.id.text_1 );
                view.setHighlightColor( getResources().getColor( R.color.selected_item_color ) );
            }
        } );
        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();
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
                        .title("Heart")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Cup")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("Diploma")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Flag")
                        .badgeTitle("icon")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Medal")
                        .badgeTitle("777")
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

}
