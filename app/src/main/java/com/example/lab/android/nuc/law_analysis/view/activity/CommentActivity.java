package com.example.lab.android.nuc.law_analysis.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.CommentExpandAdapter;
import com.example.lab.android.nuc.law_analysis.bean.CommentBean;
import com.example.lab.android.nuc.law_analysis.bean.CommentDetailBean;
import com.example.lab.android.nuc.law_analysis.bean.ReplyDetailBean;
import com.example.lab.android.nuc.law_analysis.utils.views.CommentExpandableListView;
import com.example.lab.android.nuc.law_analysis.utils.views.RoundImageView;
import com.google.gson.Gson;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "CommentActivity";

    private CircleImageView image;
    private TextView name, detail, time, title;
    private RoundImageView country;


    private android.support.v7.widget.Toolbar toolbar;
    private TextView bt_comment;
    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private List<CommentDetailBean> commentsList;
    private BottomSheetDialog dialog;
    private String testJson = "{\n" +
            "\t\"code\": 1000,\n" +
            "\t\"message\": \"查看评论成功\",\n" +
            "\t\"data\": {\n" +
            "\t\t\"total\": 3,\n" +
            "\t\t\"list\": [{\n" +
            "\t\t\t\t\"id\": 42,\n" +
            "\t\t\t\t\"nickName\": \"陈克刚\",\n" +
            "\t\t\t\t\"userLogo\": \"http://p8nssbtwi.bkt.clouddn.com/teacher_3.jpg\",\n" +
            "\t\t\t\t\"content\": \"\n" +
            "如果我的孩子长大，我会告诉他/她，要找一个爱的人结婚，在\n" +
            "你们俩都希望有个孩子的时候生孩子。这场婚姻和孩子，都是你们自愿的，基于\n" +
            "你们之间的爱情发生的。如果你在算计离婚之后能分到对方多少财产，那就不要\n" +
            "结婚，如果你在计算生了孩子之后折损了多少青春，希望对方给自己多少补偿，\n" +
            "那就别生。作为一个父亲，我会给我的孩子准备好力所能及的财产，但不管男女\n" +
            "，我不希望ta寄希望于通过婚姻而不是自己的努力提升自己的社会层次和地位。希\n" +
            "望婚姻能回到本来应有的面目，所以我支持保护男女双方任何一方婚前财产的法律。\n" +
            "而因此丢失的结婚率，都不值得惋惜。\n" +
            "\n\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"三分钟前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"李星亮\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://pd35yssng.bkt.clouddn.com/lawyer7.jpg\",\n" +
            "\t\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\t\"commentId\": \"42\",\n" +
            "\t\t\t\t\t\"content\": \"首先，我觉得普法是很重要的，习得婚姻法，对于什么夫妻共同财产啊，\n" +
            "万一离婚后出现财产纠纷啊之类之类的，都是很有帮助的。但是……结婚又不是奔着离婚去的\n" +
            "，学习婚姻法我是不反对的，\n" +
            "可是学习的多了，你确定你还要结婚吗？！（如果你是法律专业，当我没说）就酱，渐离易水送荆轲！\n" +
            "\n\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"一个小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 41,\n" +
            "\t\t\t\t\"nickName\": \"张连民\",\n" +
            "\t\t\t\t\"userLogo\": \"http://p8nssbtwi.bkt.clouddn.com/teacher_4.jpg\",\n" +
            "\t\t\t\t\"content\": \"真相： 德国从没有规定一定要给女方赡养费。原则上各自婚前财产归各自所有。婚后财产原则上平分。\n" +
            "离婚后如有一方收入达不到法定生活标准下限，由超出的另一方提供赡养费，但是不能让自身的收入在付赡养费后低于这个下限。\n" +
            "这个可以是男方付给女方，也可以是女方付给男方，而且这样的例子并不少见。\n" +
            "如果双方收入都超过这个最低标准，就不存在谁给谁赡养费的事情，如果都低于这个标准，主要由社会福利体系分担。\n" +
            "即使是一方需要付给一方赡养费的情况，现在新法也有所改变，不再是一辈子付下去，而是有规定时限，比如说三年。\n" +
            "这样收入低的一方有时间努力提高自己的生存技能，自力更生地去创造财富，而不是想当然地等着人来养。\n" +
            "所以谁先提出离婚对于财产分割基本没有任何影响。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"一天前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"萧晓林\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://pd35yssng.bkt.clouddn.com/lawyer2.jpg\",\n" +
            "\t\t\t\t\t\"commentId\": \"41\",\n" +
            "\t\t\t\t\t\"content\": \"这是个好事吧～这个法出来等于在对所有未婚姑娘说：“妹子清醒了。\n" +
            "不要想着和一个不怎么爱的人结婚了几年后离婚了还能分个共有财产。俺们不给你的。\n" +
            "你还是找个相爱的人吧。同时开发一下自己的劳动智慧成就下自己的地位和本钱吧。”\n" +
            "我觉得这挺好的。放出来妹子们才会思考和改变策略。毕竟人生精髓就是一个畅快。\n" +
            "为了占个房子和财产的便宜。和一个不喜欢的人在一起真真自己折磨自己。\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"三小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\"nickName\": \"白云华\",\n" +
            "\t\t\t\t\"userLogo\": \"http://pd35yssng.bkt.clouddn.com/lawyer1.jpg\",\n" +
            "\t\t\t\t\"content\": \"试我没看到新婚姻法里对男性或者女性存有恶意的条款，我只看到，\n" +
            "字里行间，新婚姻法都对寄生虫充满了恶意。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 0,\n" +
            "\t\t\t\t\"createDate\": \"三天前\",\n" +
            "\t\t\t\t\"replyList\": []\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_comment );
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_na);
        expandableListView = (CommentExpandableListView) findViewById(R.id.detail_page_lv_comment);
        bt_comment = (TextView) findViewById(R.id.detail_page_do_comment);
        bt_comment.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("社区详情");
        commentsList = generateTestData();
        initExpandableListView(commentsList);
        image = (CircleImageView) findViewById(R.id.detail_page_userLogo);
        name = (TextView) findViewById(R.id.detail_page_userName);
        time = (TextView) findViewById(R.id.detail_page_time);
        detail = (TextView) findViewById(R.id.detail_page_story);
        country = (RoundImageView) findViewById(R.id.country_comment);
        title = (TextView) findViewById(R.id.detail_page_title);
        Intent intent = getIntent();
        String NAME = intent.getStringExtra( "comment_name" );
        String IMAGE = intent.getStringExtra( "comment_image" );
        String TIME = intent.getStringExtra( "comment_time" );
        String DETAIL = intent.getStringExtra( "comment_detail" );
        int COUNTRY_IMAGE = intent.getIntExtra( "comment_country_image" ,1);
        name.setText( NAME );
        time.setText( TIME );
        detail.setText( DETAIL );
        Glide.with( this ).load( IMAGE ).into( image );
        Log.e( "wang",String.valueOf(  COUNTRY_IMAGE ));
        Glide.with( this ).load( COUNTRY_IMAGE ).into( country );
//        country.setImageResource( COUNTRY_IMAGE );
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentList) {
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(this, commentList);
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < commentList.size(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e(TAG, "onGroupClick: 当前的评论id>>>" + commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(CommentActivity.this, "点击了回复", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }

    /**
     * func:生成测试数据
     *
     * @return 评论数据
     */
    private List<CommentDetailBean> generateTestData() {
        Gson gson = new Gson();
        commentBean = gson.fromJson(testJson, CommentBean.class);
        List<CommentDetailBean> commentList = commentBean.getData().getList();
        return commentList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.detail_page_do_comment) {

            showCommentDialog();
        }
    }

    /**
     * func:弹出评论框
     */
    private void showCommentDialog() {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    CommentDetailBean detailBean = new CommentDetailBean("小明", commentContent, "刚刚");
                    adapter.addTheCommentData(detailBean);
                    Toast.makeText(CommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CommentActivity.this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor( Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * func:弹出回复框
     */
    private void showReplyDialog(final int position) {
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {

                    dialog.dismiss();
                    ReplyDetailBean detailBean = new ReplyDetailBean("小红", replyContent);
                    adapter.addTheReplyData(detailBean, position);
                    expandableListView.expandGroup(position);
                    Toast.makeText(CommentActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CommentActivity.this, "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence) && charSequence.length() > 2) {
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                } else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }
}
