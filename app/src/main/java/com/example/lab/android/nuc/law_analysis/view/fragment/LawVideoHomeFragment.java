package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.lab.android.nuc.law_analysis.adapter.MagicPagerAdapter;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.application.Config;
import com.example.lab.android.nuc.law_analysis.video.VideoFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LawVideoHomeFragment extends Fragment implements View.OnClickListener{

    private static final String[] CHANNELS = new String[]{"热点视频","婚姻纠葛", "土地纠纷", "专利产权",
            "精品视频", "娱乐视频","养老医疗","盗窃抢劫", "搞笑视频"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ViewPager mViewPager;
    private  MagicPagerAdapter mMagicPagerAdapter;
    private ArrayList<String> mTitle1 = new ArrayList<>(  );
    private ArrayList<String> mUri1 = new ArrayList<>(  );
    private ArrayList<String> mTitle2 = new ArrayList<>(  );
    private ArrayList<String> mUri2 = new ArrayList<>(  );
    private ArrayList<String> mTitle3 = new ArrayList<>(  );
    private ArrayList<String> mUri3 = new ArrayList<>(  );

    public static LawVideoHomeFragment newInstance(){
        Bundle bundle = new Bundle( );
        LawVideoHomeFragment lawVideoHomeFragment = new LawVideoHomeFragment();
        lawVideoHomeFragment.setArguments( bundle );
        return lawVideoHomeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        initFragment1();
        initFragment2();
        initFragment3();
        mMagicPagerAdapter = new MagicPagerAdapter( getChildFragmentManager() );
        mMagicPagerAdapter.addFragment(VideoFragment.newInstance( Config.VIDEO_CHOICE_ID));
        mMagicPagerAdapter.addFragment(LawVideoFragment.newInstance(mTitle1,mUri1) );
        mMagicPagerAdapter.addFragment(LawVideoFragment.newInstance(mTitle2,mUri2) );
        mMagicPagerAdapter.addFragment(LawVideoFragment.newInstance(mTitle3,mUri3));
        mMagicPagerAdapter.addFragment(VideoFragment.newInstance(Config.VIDEO_ENTERTAINMENT_ID));
        mMagicPagerAdapter.addFragment(VideoFragment.newInstance(Config.VIDEO_FUN_ID));
        mMagicPagerAdapter.addFragment(LawVideoFragment.newInstance(mTitle3,mUri3) );
        mMagicPagerAdapter.addFragment(LawVideoFragment.newInstance(mTitle3,mUri3) );
        mMagicPagerAdapter.addFragment(VideoFragment.newInstance(Config.VIDEO_HOT_ID));
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_law_video,container,false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter( mMagicPagerAdapter );
        initMagicIndicator1(view);
        return view;
    }

    private void initMagicIndicator1(View view) {
        MagicIndicator magicIndicator = (MagicIndicator) view.findViewById(R.id.magic_indicator1);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor( Color.parseColor("#88ffffff"));
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#40c4ff"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding( UIUtil.dip2px(getContext(), 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void onClick(View v) {

    }

    private void initFragment1(){
        mTitle1.add( "2018婚姻法，缺了这9个，谁也离不了婚？" );
        mTitle1.add( "2018新婚姻法规定：3种情况离婚净身出户，懂了吗？" );
        mTitle1.add( "2018年，新婚姻法，起诉离婚后，查询法官的正确打开方式？" );
        mTitle1.add( "辽视第一时间 - 婚姻法" );
        mTitle1.add( "今年新婚姻法，不管结婚几十年，这项规定女方一分钱拿不到" );
        mTitle1.add( "中国访谈 -- 世界对话之新婚姻法的推出" );
        mTitle1.add( "2018婚姻法新规定，房产证即使有你名字，离婚时也可能分不到钱" );
        mTitle1.add( "《婚姻法》有规定！这几种情况房子写了你名字也未必是你的！" );
        mTitle1.add( "妻子频遭家暴怀着孕也被殴打，丈夫：我就这个样，你可以离婚啊！" );
        mTitle1.add( "2018新婚姻法，关于未婚同居都做了哪些规定？不知道可吃大亏了！" );
        mUri1.add("http://pd35yssng.bkt.clouddn.com/woc.mp4");
        mUri1.add("http://pd35yssng.bkt.clouddn.com/woc6.mp4");
        mUri1.add("http://pd35yssng.bkt.clouddn.com/woc7.mp4");
        mUri1.add("http://pd35yssng.bkt.clouddn.com/woc5.mp4");
        mUri1.add("http://pd35yssng.bkt.clouddn.com/woc1.mp4");
        mUri1.add("http://pd35yssng.bkt.clouddn.com/wocc1.mp4");
        mUri1.add("http://pd35yssng.bkt.clouddn.com/wocc2.mp4");
        mUri1.add("http://pd35yssng.bkt.clouddn.com/wocc3.mp4");
        mUri1.add("http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1810%252F01%252FUWXnL338L%252FHD%252FUWXnL338L-mobile.mp4");
        mUri1.add("http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1808%252F21%252FRhFCn026a%252FHD%252FRhFCn026a-mobile.mp4");
    }

    private void initFragment2(){
        mTitle2.add( "【土地确权】农村户口将全面取消！这4件事抓紧去办，越晚越吃亏！" );
        mTitle2.add( "农村土地纠纷，两家大打出手，为征土地年迈老人被打倒在地" );
        mTitle2.add( "划分土地起争执 村干部竟当众露下体侮辱女村民" );
        mTitle2.add( "五大措施教你处理土地纠纷" );
        mTitle2.add( "处理土地纠纷要遵循哪些原则" );
        mTitle2.add( "中国访谈 -- 世界对话之新婚姻法的推出" );
        mTitle2.add( "2018婚姻法新规定，房产证即使有你名字，离婚时也可能分不到钱" );
        mTitle2.add( "《婚姻法》有规定！这几种情况房子写了你名字也未必是你的！" );
        mTitle2.add( "妻子频遭家暴怀着孕也被殴打，丈夫：我就这个样，你可以离婚啊！" );
        mTitle2.add( "2018新婚姻法，关于未婚同居都做了哪些规定？不知道可吃大亏了！" );
        mUri2.add("http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1805%252F03%252FEDFVR59O6%252FSD%252FEDFVR59O6-mobile.mp4");
        mUri2.add("http://pd35yssng.bkt.clouddn.com/1245008497_3012156243_20170430180531.mp4");
        mUri2.add("http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1809%252F26%252FgoBOS340H%252FSD%252FgoBOS340H-mobile.mp4");
        mUri2.add("http://pd35yssng.bkt.clouddn.com/we.mp4");
        mUri2.add("http://pd35yssng.bkt.clouddn.com/we1.mp4");
        mUri2.add("http://pd35yssng.bkt.clouddn.com/wocc2.mp4");
        mUri2.add("http://pd35yssng.bkt.clouddn.com/wocc3.mp4");
        mUri2.add("http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1810%252F01%252FUWXnL338L%252FHD%252FUWXnL338L-mobile.mp4");
        mUri2.add("http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1808%252F21%252FRhFCn026a%252FHD%252FRhFCn026a-mobile.mp4");
    }
    private void initFragment3(){
        mTitle3.add( "最高法：加大知识产权侵权违法惩治力度！" );
        mTitle3.add( "农村土地纠纷，两家大打出手，为征土地年迈老人被打倒在地" );
        mTitle3.add( "划分土地起争执 村干部竟当众露下体侮辱女村民" );
        mTitle3.add( "五大措施教你处理土地纠纷" );
        mTitle3.add( "处理土地纠纷要遵循哪些原则" );
        mTitle3.add( "中国访谈 -- 世界对话之新婚姻法的推出" );
        mTitle3.add( "2018婚姻法新规定，房产证即使有你名字，离婚时也可能分不到钱" );
        mTitle3.add( "《婚姻法》有规定！这几种情况房子写了你名字也未必是你的！" );
        mTitle3.add( "妻子频遭家暴怀着孕也被殴打，丈夫：我就这个样，你可以离婚啊！" );
        mTitle3.add( "2018新婚姻法，关于未婚同居都做了哪些规定？不知道可吃大亏了！" );
        mUri3.add("http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1802%252F28%252FYHdpn7061%252FHD%252FYHdpn7061-mobile.mp4");
        mUri3.add("http://pd35yssng.bkt.clouddn.com/1245008497_3012156243_20170430180531.mp4");
        mUri3.add("http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1809%252F26%252FgoBOS340H%252FSD%252FgoBOS340H-mobile.mp4");
        mUri3.add("http://pd35yssng.bkt.clouddn.com/we.mp4");
        mUri3.add("http://pd35yssng.bkt.clouddn.com/we1.mp4");
        mUri3.add("http://pd35yssng.bkt.clouddn.com/wocc2.mp4");
        mUri3.add("http://pd35yssng.bkt.clouddn.com/wocc3.mp4");
        mUri3.add("http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1810%252F01%252FUWXnL338L%252FHD%252FUWXnL338L-mobile.mp4");
        mUri3.add("http://pd35yssng.bkt.clouddn.com/videolib_repo%252F1808%252F21%252FRhFCn026a%252FHD%252FRhFCn026a-mobile.mp4");
    }
}