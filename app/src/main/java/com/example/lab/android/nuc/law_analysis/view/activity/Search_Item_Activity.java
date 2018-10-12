package com.example.lab.android.nuc.law_analysis.view.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.adapter.LawListAdapter;
import com.example.lab.android.nuc.law_analysis.base.LawItemBean;
import com.example.lab.android.nuc.law_analysis.view.customview.CustomStatusView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Search_Item_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView emptyContent;
    private static TextView tipTextView;
    private static Dialog ProgressDialog;
    private  static CustomStatusView customStatusView;
    private LawListAdapter lawListAdapter;

    private String search_data;
    private String[] cutSearchData;
    private List<LawItemBean> laws;
    private LawItemBean[] all_items = {
            new LawItemBean("第四十六条", " 荒山、荒沟、荒丘、荒滩等可以直接通过招标、拍卖、公开协商等方式实行承包经营，也可以将土地承包经营权折股分给本集体经济组织成员后，再实行承包经营或者股份合作经营。承包荒山、荒沟、荒丘、荒滩的，应当遵守有关法律、行政法规的规定，防止水土流失，保护生态环境。", "中华人民共和国农村土地承包法(2009修正)"),
            new LawItemBean("第一条","为了加强安全生产工作，防止和减少生产安全事故，保障人民群众生命和财产安全，促进经济社会持续健康发展，制定本法。", "中华人民共和国安全生产法(2014修正)"),
            new LawItemBean("第二条", "在中华人民共和国领域内从事生产经营活动的单位（以下统称生产经营单位）的安全生产，适用本法；有关法律、行政法规对消防安全和道路交通安全、铁路交通安全、水上交通安全、民用航空安全以及核与辐射安全、特种设备安全另有规定的，适用其规定。","中华人民共和国安全生产法(2014修正)"),
            new LawItemBean("第十六条", "国家对在改善安全生产条件、防止生产安全事故、参加抢险救护等方面取得显著成绩的单位和个人，给予奖励。","中华人民共和国安全生产法(2014修正)"),
            new LawItemBean("第一条", "【立法目的】本法是婚姻家庭关系的基本准则。", "中华人民共和国婚姻法(2001修正).txt"),
            new LawItemBean("第四十三条", "【家庭暴力与虐待】实施家庭暴力或虐待家庭成员，受害人有权提出请求，居民委员会、村民委员会以及所在单位应当予以劝阻、调解。对正在实施的家庭暴力，受害人有权提出请求，居民委员会、村民委员会应当予以劝阻；公安机关应当予以制止。实施家庭暴力或虐待家庭成员，受害人提出请求的，公安机关应当依照治安管理处罚的法律规定予以行政处罚。", "中华人民共和国婚姻法(2001修正)"),
            new LawItemBean("第十七条", "【夫妻共有财产】夫妻在婚姻关系存续期间所得的下列财产，归夫妻共同所有：（一）工资、奖金；（二）生产、经营的收益；（三）知识产权的收益；（四）继承或赠与所得的财产，但本法第十八条第三项规定的除外；（五）其他应当归共同所有的财产。夫妻对共同所有的财产，有平等的处理权。", "中华人民共和国婚姻法(2001修正)"),
            new LawItemBean("第二十八条", "【祖与孙】有负担能力的祖父母、外祖父母，对于父母已经死亡或父母无力抚养的未成年的孙子女、外孙子女，有抚养的义务。有负担能力的孙子女、外孙子女，对于子女已经死亡或子女无力赡养的祖父母、外祖父母，有赡养的义务。", "中华人民共和国婚姻法(2001修正)"),
            new LawItemBean(" 第七十五条", "干涉老年人婚姻自由，对老年人负有赡养义务、扶养义务而拒绝赡养、扶养，虐待老年人或者对老年人实施家庭暴力的，由有关单位给予批评教育；构成违反治安管理行为的，依法给予治安管理处罚；构成犯罪的，依法追究刑事责任。", "中华人民共和国老年人权益保障法(2015修正)"),
            new LawItemBean("第三十条", "【尊重父母婚姻】子女应当尊重父母的婚姻权利，不得干涉父母再婚以及婚后的生活。子女对父母的赡养义务，不因父母的婚姻关系变化而终止。", "中华人民共和国婚姻法(2001修正)"),
            new LawItemBean("第三十七条", "【离婚后的子女抚养】离婚后，一方抚养的子女，另一方应负担必要的生活费和教育费的一部或全部，负担费用的多少和期限的长短，由双方协议；协议不成时，由人民法院判决。关于子女生活费和教育费的协议或判决，不妨碍子女在必要时向父母任何一方提出超过协议或判决原定数额的合理要求。", "中华人民共和国婚姻法(2001修正)"),
            new LawItemBean("第二十七条", "【继父母与继子女】继父母与继子女间，不得虐待或歧视。继父或继母和受其抚养教育的继子女间的权利和义务，适用本法对父母子女关系的有关规定。", "中华人民共和国婚姻法(2001修正)"),
            new LawItemBean("第四十八条", "【强制执行】对拒不执行有关扶养费、抚养费、赡养费、财产分割、遗产继承、探望子女等判决或裁定的，由人民法院依法强制执行。有关个人和单位应负协助执行的责任。", "中华人民共和国婚姻法(2001修正)"),
            new LawItemBean("第八条", "【结婚登记】要求结婚的男女双方必须亲自到婚姻登记机关进行结婚登记。符合本法规定的，予以登记，发给结婚证。取得结婚证，即确立夫妻关系。未办理结婚登记的，应当补办登记。", "中华人民共和国婚姻法(2001修正)"),
            new LawItemBean(" 第十八条", "家庭成员应当关心老年人的精神需求，不得忽视、冷落老年人。与老年人分开居住的家庭成员，应当经常看望或者问候老年人。用人单位应当按照国家有关规定保障赡养人探亲休假的权利。", "中华人民共和国老年人权益保障法(2015修正)"),
            new LawItemBean(" 第二十条", "经老年人同意，赡养人之间可以就履行赡养义务签订协议。赡养协议的内容不得违反法律的规定和老年人的意愿。基层群众性自治组织、老年人组织或者赡养人所在单位监督协议的履行。", "中华人民共和国老年人权益保障法(2015修正)"),
            new LawItemBean(" 第十九条", "赡养人不得以放弃继承权或者其他理由，拒绝履行赡养义务。赡养人不履行赡养义务，老年人有要求赡养人付给赡养费等权利。赡养人不得要求老年人承担力不能及的劳动。", "中华人民共和国老年人权益保障法(2015修正)"),
            new LawItemBean(" 第二十三条", "老年人与配偶有相互扶养的义务。由兄、姐扶养的弟、妹成年后，有负担能力的，对年老无赡养人的兄、姐有扶养的义务。", "中华人民共和国老年人权益保障法(2015修正)"),
            new LawItemBean(" 第十六条", "赡养人应当妥善安排老年人的住房，不得强迫老年人居住或者迁居条件低劣的房屋。老年人自有的或者承租的住房，子女或者其他亲属不得侵占，不得擅自改变产权关系或者租赁关系。老年人自有的住房，赡养人有维修的义务。", "中华人民共和国老年人权益保障法(2015修正)"),
            new LawItemBean("第二十条", "个人所得税的征收管理，依照本法和《中华人民共和国税收征收管理法》的规定执行。", "中华人民共和国个人所得税法(2018修正)"),
            new LawItemBean("第七条", "居民个人从中国境外取得的所得，可以从其应纳税额中抵免已在境外缴纳的个人所得税税额，但抵免额不得超过该纳税人境外所得依照本法规定计算的应纳税额。", "中华人民共和国个人所得税法(2018修正)"),
            new LawItemBean("第十条", "有下列情形之一的，纳税人应当依法办理纳税申报：（一）取得综合所得需要办理汇算清缴；（二）取得应税所得没有扣缴义务人；（三）取得应税所得，扣缴义务人未扣缴税款；（四）取得境外所得；（五）因移居境外注销中国户籍；（六）非居民个人在中国境内从两处以上取得工资、薪金所得；（七）国务院规定的其他情形。扣缴义务人应当按照国家规定办理全员全额扣缴申报，并向纳税人提供其个人所得和已扣缴税款等信息。", "中华人民共和国个人所得税法(2018修正)"),
            new LawItemBean("第十九条", "纳税人、扣缴义务人和税务机关及其工作人员违反本法规定的，依照《中华人民共和国税收征收管理法》和有关法律法规的规定追究法律责任。", "中华人民共和国个人所得税法(2018修正)"),
            new LawItemBean("第五条", "有下列情形之一的，可以减征个人所得税，具体幅度和期限，由省、自治区、直辖市人民政府规定，并报同级人民代表大会常务委员会备案：（一）残疾、孤老人员和烈属的所得；（二）因自然灾害遭受重大损失的。国务院可以规定其他减税情形，报全国人民代表大会常务委员会备案。", "中华人民共和国个人所得税法(2018修正)")
    };

    private Connection connection;//数据库连接操作变量

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_intent_item);
        initView();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            search_data = bundle.getString("data");
            //有时间写按空格切分
            cutSearchData = search_data.split(" ");
        }

        showCompleteDialog(Search_Item_Activity.this, "查找中");
    }

    void showCompleteDialog(final Context context, String msg){

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.shop_dialog_toast, null);// 得到加载view

        tipTextView = v.findViewById(R.id.tv_toast_content);// 提示文字
        tipTextView.setText(msg);// 设置加载信息
        customStatusView = v.findViewById(R.id.as_status);
        customStatusView.loadLoading();
        ProgressDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        ProgressDialog.setCancelable(false); // 是否可以按“返回键”消失
        ProgressDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        ProgressDialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局

        /**
         *将显示Dialog的方法封装在这里面
         */
        final Window window = ProgressDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width =1000;
        lp.height =800;
        lp.y=-150;
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        ProgressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Class.forName("com.mysql.jdbc.Driver");
//                    connection = DriverManager.getConnection("jdbc:mysql://39.105.110.28:3306/lawList?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false", "root", "FABAOin2018");
//                } catch (ClassNotFoundException e) {
//                    customStatusView.loadFailure();
//                    tipTextView.setText("寻找class方法失败！");
//                    e.printStackTrace();
//                    return;
//                } catch (SQLException e) {
//                    customStatusView.loadFailure();
//                    tipTextView.setText(e.toString());
//                    e.printStackTrace();
//                    return;
//                }
//
//                String sql_lines = "select * from LawArticleAll_set where concat(law_content, law_from) like '%"
//                        + cutSearchData[0] + "%'";
//                for (int i = 1; i < cutSearchData.length; i++) {
//                    sql_lines += " and concat(law_content, law_from) like '%" + cutSearchData[i] + "%'";
//                }
//
//                try {
//                    Statement statement = connection.createStatement();
//                    ResultSet cusor = statement.executeQuery(sql_lines);
//
//                    while (cusor.next()){
//                        String testString = cusor.getString("law_line");
//                        Log.d("sql_law_line", testString);
//                    }
//                } catch (SQLException e) {
//                    customStatusView.loadFailure();
//                    tipTextView.setText("查询失败！");
//                    e.printStackTrace();
//                    return;
//                }

           }
        }, 500);
        //这里用到了handler的定时器效果 延迟2秒执行dismiss();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ProgressDialog.dismiss();
            }
        }, 2000);
    }

    private void initView() {
        laws = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.search_recyclerView);
        emptyContent = (TextView)findViewById(R.id.empty_content);
        recyclerView = (RecyclerView) findViewById(R.id.search_item_recyclerView);

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            LawItemBean item = all_items[random.nextInt(24)];
            laws.add(item);
        }
        //tipTextView.setText("成功!");
        //customStatusView.loadSuccess();

        initrecycleView();
    }

    private void initrecycleView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(lawListAdapter = new LawListAdapter(laws));
        recyclerView.setLongClickable(true);
        lawListAdapter.setOnItemClickLitener(new LawListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(LawItemBean lawItemBean) {

            }

            @Override
            public void onItemLongClick(LawItemBean lawItemBean) {
                android.content.ClipboardManager cm = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cm.setText(lawItemBean.getLaw_line() + lawItemBean.getLaw_content() + lawItemBean.getLaw_from());
            }
        });
    }

}
