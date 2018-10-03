package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.NineGridTest2Adapter;
import com.example.lab.android.nuc.law_analysis.base.NineGridTestModel;
import com.example.lab.android.nuc.law_analysis.view.activity.DynamicActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;
import java.util.List;

/*
法律社区的fragment
 */
public class LawyersChatFragment extends Fragment implements View.OnClickListener{

    private static final int REQUEST_DYNAMICS = 1;
    private View view;
    private static final String ARG_LIST = "list";
    private LinearLayout commentLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NineGridTest2Adapter mAdapter;
    private TextView commentFakeButton;
    private List<NineGridTestModel> mList = new ArrayList<>( );
    private TextView commentButton;


    /*与悬浮按钮相关*/
    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton mActionMap;
    private FloatingActionButton mActionModel;
    private FloatingActionButton mAction_c;
    private FloatingActionButton mAction_d;
    private FloatingActionButton mAction_e;


    public static LawyersChatFragment newInstance(){
        Bundle bundle = new Bundle( );
        LawyersChatFragment lawyersChatFragment = new LawyersChatFragment();
        lawyersChatFragment.setArguments( bundle );
        return lawyersChatFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        initListData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_law_chat,container,false);
        ShineButton shineButton1 = (ShineButton) view.findViewById( R.id.po_image1 );
        initFloatButton( view );
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
//        ShineButton shineButton1 = (ShineButton) view.findViewById( R.id.po_image1 );
        super.onResume();
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById( R.id.recyclerView );
        mLayoutManager = new LinearLayoutManager( getContext() );
        mRecyclerView.setLayoutManager( mLayoutManager );
        mAdapter = new NineGridTest2Adapter( getContext() );
        mAdapter.setList( mList );
        mRecyclerView.addItemDecoration( new DividerItemDecoration( getContext(),DividerItemDecoration.VERTICAL ));
        mRecyclerView.setAdapter( mAdapter );
    }

    private void initListData() {
        NineGridTestModel model1 = new NineGridTestModel();
        model1.name = "白云华";
        model1.imageUri = imageUrls[0];
        for (int i = 0; i < 2; i++) {
            model1.urlList.add( mUrls[i] );
        }
        model1.time = "今天 21:08";
        model1.detail = "不是最厉害的，但绝对是最用心的，最贴近人普通人的法律公众号。特别适合于对法律感兴趣，尝试了各种方法都不得要领的小伙伴们，不管" +
                "是零基础，还是有一定的积累，都可以从这里获取知识，专注对各门法律的发条，逐条学习，详细划重点解读";
        model1.location_imageUri = R.drawable.hebei;
        mList.add(model1);

        NineGridTestModel model2 = new NineGridTestModel();
        for (int i = 2; i < 6; i++) {
            model2.urlList.add( mUrls[i] );
        }
        model2.imageUri = imageUrls[7];
        model2.time = "今天 20:50";
        model2.name = "萧晓林";
        model2.detail = "婚姻就像是一只碗，破了很难再恢复原状，即使粘上了，裂痕也永远都在。夫妻感情不和，很多都会选择离婚，但是离婚也不是件容易事，" +
                "它和任何事情一样，也是要走程序的，大致梳理了几点条件，每一点出问题都可能影响离婚证的办理。";
        model2.location_imageUri = R.drawable.hebei;
        mList.add(model2);
//
//        NineGridTestModel model3 = new NineGridTestModel();
//        model3.urlList.add(mUrls[2]);
//        mList.add(model3);

        NineGridTestModel model4 = new NineGridTestModel();
        model4.urlList.add(mUrls[6]);//
        model4.isShowAll = true;
        model4.imageUri = imageUrls[1];
        model4.name ="胡建国";
        model4.time = "今天 12:40";
        model4.detail = "土地纠纷是指当事人因土地所有权和使用权以及其他有关土地的权利归属问题发生的争议。" +
                "具体而言，就是两个以上单位或个人同时对未经确权的同一块土地各据理由主张权属，根据各方理由难以解决的土地权属矛盾。";
        model4.location_imageUri = R.drawable.hebei;
        mList.add(model4);

        NineGridTestModel model5 = new NineGridTestModel();
        for (int i = 7; i < 10; i++) {
            model5.urlList.add(mUrls[i]);//
        }
        model5.isShowAll = true;//显示全部图片
        model5.imageUri = imageUrls[6];
        model5.name = "陈克刚";
        model5.time = "昨天 21:51";
        model5.detail = "生命诚可贵，专利价更高？从专利看《我不是药神》最近，电影《我不是药神》给沉寂许久的电影市场带来了新的生机与活力。" +
                "这部电影不像《战狼》和《红海行动》一样讲家国大事，它讲的只是可能会发生在你身边的事。" +
                "其中涉及到的药品专利话题，在我们IP人的朋友圈刷了一波又一波。";
        model5.location_imageUri = R.drawable.hebei;
        mList.add(model5);

        NineGridTestModel model6 = new NineGridTestModel();
        for (int i = 10; i < 16; i++) {
            model6.urlList.add(mUrls[i]);//
        }
        model6.imageUri = imageUrls[2];
        model6.name = "张世豪";
        model6.time = "前天 16:25";
        model6.detail = "养老教育医疗，谁能重新挑起房地产的担子？西谚有云，人生在世，唯税收和死亡无法避免。同理，养老、教育、医疗也是人生避无可避之事。\n" +
                "近日，有媒体撰文认为，养老、教育、医疗，有望成为拉动内需的三驾马车。";
        model6.location_imageUri = R.drawable.hebei;
        mList.add(model6);

        NineGridTestModel model7 = new NineGridTestModel();
        for (int i = 16; i < 20; i++) {
            model7.urlList.add(mUrls[i]);//
        }
        model7.time  = "08月15日 23:56";
        model7.isShowAll = true;
        model7.name = "万正学";
        model7.imageUri = imageUrls[5];
        model7.detail="农村土地纠纷的类型及其解决办法: 随着农民法律意识的增强，近几年，涉及农村土地的纠纷和案件有上升趋势。" +
                "农村土地纠纷，常常涉及多人利益，处理稍有不慎，很容易引发群体事件。解决大量的土地矛盾纠纷成为目前乡村两级工作的重头戏。";
        model7.location_imageUri = R.drawable.hebei;
        mList.add(model7);

        NineGridTestModel model8 = new NineGridTestModel();
        for (int i = 20; i < 22; i++) {
            model8.urlList.add(mUrls[i]);//
        }
        model8.imageUri = imageUrls[4];
        model8.name = "李星亮";
        model8.time = "08月14日 12:03";
        model8.detail = "婚前试爱，避免婚后4种夫妻关系纠葛，打造幸福婚姻家庭模式！男人与女人谈恋爱，最终会不会娶她，" +
                "大多从一开始就打算好的。之后，所有的交往过程，都不过是在为日后“分”与“合”寻找论证点。";
        model8.location_imageUri = R.drawable.hebei;
        mList.add(model8);


        NineGridTestModel model9 = new NineGridTestModel();
        for (int i = 22; i < 25; i++) {
            model9.urlList.add(mUrls[i]);//
        }
        model9.imageUri = imageUrls[3];
        model9.name = "郎坚";
        model9.time = "08月14日 12:03";
        model9.detail = "江宇：养老教育医疗是新三驾马车？可能是新三座大山!" +
                "养老、教育、医疗事业应该是坚持以人民为中心的公益事业，而不能走全盘市场化、商业化的道路。将养老、教育、" +
                "医疗作为拉动经济的手段，颠倒了经济发展和民生的关系";
        model9.location_imageUri = R.drawable.hebei;
        mList.add(model9);


        NineGridTestModel model10 = new NineGridTestModel();
        for(int i = 26;i < 28;i ++){
            model10.urlList.add( mUrls[i] );
        }
        model10.imageUri = imageUrls[9];
        model10.name = "王立建";
        model10.time = "08月14日 18:32";
        model10.detail = "农村土地纠纷常见的7大法律问题有答案了!近年来，农村城镇化发展迅速，农村土地纠纷已成为社会共同关注的热点、" +
                "为此，从现实案例中提炼出7个小问题，以问答的形式，依据现有相关的法律规定以及一般司法裁判规则，" +
                "阐述当前农村土地纠纷中部分常见的法律问题，以供各位朋友参考。";
        model10.location_imageUri = R.drawable.hebei;
        mList.add( model10 );

        NineGridTestModel model11 = new NineGridTestModel();
        for(int i = 28;i < 30;i ++){
            model11.urlList.add( mUrls[i] );
        }
        model11.imageUri = imageUrls[10];
        model11.name = "张连民";
        model11.time = "08月13日 7:29";
        model11.detail = "由盗窃、诈骗、抢夺转化为抢劫性质，最终以抢劫罪处理，这可以说是一种特殊形式的抢劫罪，" +
                "在理论上又称为转化型抢劫罪或准抢劫罪。这一特殊形式的抢劫罪在认定上往往会出现一些不一致的做法，" +
                "导致一些案件在定性上的争议或错误。";
        model11.location_imageUri = R.drawable.hebei;
        mList.add( model11 );

        OkGo.<String>post( "http://47.95.7.169:8080/getQuestion")
                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                } );
    }

    //图片数据的url;
    private String[] mUrls = new String[]{
            "http://pfx0y0vgp.bkt.clouddn.com/new2.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new4.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new5.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new6.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new7.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new8.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new9.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new10.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new11.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new12.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new13.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new14.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new15.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new16.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new17.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new18.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new19.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new20.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new21.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new22.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new23.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new24.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new25.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new26.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new27.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new32.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new29.jpg",
            "http://pbslsudal.bkt.clouddn.com/new31.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new32.jpg",
            "http://pbslsudal.bkt.clouddn.com/new31.jpg",
            "http://pfx0y0vgp.bkt.clouddn.com/new32.jpg",
    };

    //头像数据Uri
    private String[] imageUrls = new String[]{
            "http://pd35yssng.bkt.clouddn.com/lawyer1.jpg",
            "http://pd35yssng.bkt.clouddn.com/lawyer2.jpg",
            "http://pd35yssng.bkt.clouddn.com/images.jpg",
            "http://pd35yssng.bkt.clouddn.com/lawyer4.jpg",
            "http://pd35yssng.bkt.clouddn.com/u=720330820,773254810&fm=26&gp=0.jpg",
            "http://pd35yssng.bkt.clouddn.com/lawyer6.jpg",
            "http://pd35yssng.bkt.clouddn.com/lawyer7.jpg",
            "http://pd35yssng.bkt.clouddn.com/lawyer8.jpg",
            "http://pd35yssng.bkt.clouddn.com/lawyer9.jpg",
            "http://pd35yssng.bkt.clouddn.com/lawyer10.jpg",
            "http://pd35yssng.bkt.clouddn.com/lawyer13.jpg"
    };

    //悬浮按钮的一些点击事件
    private void initFloatButton(View view){
        mFloatingActionsMenu = (FloatingActionsMenu) view.findViewById( R.id.main_actions_menu );
        /**
         * 添加动态
         */
        mActionMap = (FloatingActionButton) view.findViewById( R.id.action_a );
        mActionMap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(),DynamicActivity.class );
                startActivity( intent );
                mFloatingActionsMenu.toggle();
            }
        } );



        mActionModel = (FloatingActionButton) view.findViewById( R.id.action_b );
        mActionModel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFloatingActionsMenu.toggle();
            }
        } );

        mAction_c = (FloatingActionButton) view.findViewById( R.id.action_c );
        mAction_c.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFloatingActionsMenu.toggle();
            }
        } );
        mAction_d = (FloatingActionButton) view.findViewById( R.id.action_d );
        mAction_d.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFloatingActionsMenu.toggle();
            }
        } );
    }

    @Override
    public void onClick(View v) {

    }
}
