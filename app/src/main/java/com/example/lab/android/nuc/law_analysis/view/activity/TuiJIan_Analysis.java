package com.example.lab.android.nuc.law_analysis.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.lab.android.nuc.law_analysis.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TuiJIan_Analysis extends AppCompatActivity {
    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton text_to_voice,change_voice,fab_up;
    private String VOICE = null;
    List<String> permissionList = new ArrayList<>();

    public TextView textView;
    private ScrollView scrollView;

    String[] detial = {
            " 第三十一条 根据《中华人民共和国道路交通安全法》及其实施条例、《中华人民共和国行政许可法》，制定本规定\n" +
                    "         第二十一条 本规定由公安机关交通管理部门负责实施。;\n" +
                    "         第十七条 车辆管理所办理机动车驾驶证业务，应当遵循严格、公开、公正、便民的原则。\n" +
                    "         第四条 申请办理机动车驾驶证业务的人，应当如实向车辆管理所提交规定的材料，如实申告规定的事项，并对其申请材料实质内容的真实性负责。\n" +
                    "         第五条 公安机关交通管理部门应当建立对车辆管理所办理机动车驾驶证业务的监督制度，加强对驾驶人考试、驾驶证核发和使用的监督管理。\n"
            ,
            "         第六条 车辆管理所应当使用机动车驾驶证计算机管理系统核发、打印机动车驾驶证，不使用计算机管理系统核发、打印的机动车驾驶证无效。\n" +
                    "         第一百三十条 驾驶机动车，应当依法取得机动车驾驶证。\n" +
                    "         第六十七条 机动车驾驶证记载和签注以下内容：\n" +
                    "         第九十五条 机动车驾驶证有效期分为六年、十年和长期。\n" +
                    "\n"
            ,
            "第十九条【夫妻财产约定】夫妻可以约定婚姻关系存续期间所得的财产以及婚前财产归各自所有、共同所有或部分各自所有、部分共同所有。约定应当采用书面形式。没有约定或约定不明确的，适用本法第十七条、第十八条的规定。夫妻对婚姻关系存续期间所得的财产以及婚前财产的约定，对双方具有约束力。夫妻对婚姻关系存续期间所得的财产约定归各自所有的，夫或妻一方对外所负的债务，第三人知道该约定的，以夫或妻一方所有的财产清偿。\n" +
                    "第四十四条【遗弃】对遗弃家庭成员，受害人有权提出请求，居民委员会、村民委员会以及所在单位应当予以劝阻、调解。对遗弃家庭成员，受害人提出请求的，人民法院应当依法作出支付扶养费、抚养费、赡养费的判决。\n" +
                    "第九条【互为家庭成员】登记结婚后，根据男女双方约定，女方可以成为男方家庭的成员，男方可以成为女方家庭的成员。\n" +
                    " 第七十五条干涉老年人婚姻自由，对老年人负有赡养义务、扶养义务而拒绝赡养、扶养，虐待老年人或者对老年人实施家庭暴力的，由有关单位给予批评教育；构成违反治安管理行为的，依法给予治安管理处罚；构成犯罪的，依法追究刑事责任。\n" +
                    "第二十六条【收养关系】国家保护合法的收养关系。养父母和养子女间的权利和义务，适用本法对父母子女关系的有关规定。养子女和生父母间的权利和义务，因收养关系的成立而消除。\n" +
                    "第四十八条【强制执行】对拒不执行有关扶养费、抚养费、赡养费、财产分割、遗产继承、探望子女等判决或裁定的，由人民法院依法强制执行。有关个人和单位应负协助执行的责任。\n" +
                    "第八条【结婚登记】要求结婚的男女双方必须亲自到婚姻登记机关进行结婚登记。符合本法规定的，予以登记，发给结婚证。取得结婚证，即确立夫妻关系。未办理结婚登记的，应当补办登记。\n" +
                    "第三十五条【复婚】离婚后，男女双方自愿恢复夫妻关系的，必须到婚姻登记机关进行复婚登记。\n",
            "第一百二十条之四【利用极端主义破坏法律实施罪】利用极端主义煽动、胁迫群众破坏国家法律确立的婚姻、司法、教育、社会管理等制度实施的，处三年以下有期徒刑、拘役或者管制，并处罚金；情节严重的，处三年以上七年以下有期徒刑，并处罚金；情节特别严重的，处七年以上有期徒刑，并处罚金或者没收财产。\n" +
                    "第二十五条【非婚生子女】非婚生子女享有与婚生子女同等的权利，任何人不得加以危害和歧视。不直接抚养非婚生子女的生父或生母，应当负担子女的生活费和教育费，直至子女能独立生活为止。\n" +
                    "第三十九条【夫妻共同财产的离婚处理】离婚时，夫妻的共同财产由双方协议处理；协议不成时，由人民法院根据财产的具体情况，照顾子女和女方权益的原则判决。夫或妻在家庭土地承包经营中享有的权益等，应当依法予以保护。\n" +
                    "第六条【法定婚龄】结婚年龄，男不得早于二十二周岁，女不得早于二十周岁。晚婚晚育应予鼓励。\n" +
                    "第二十二条【子女的姓】子女可以随父姓，可以随母姓。\n" +
                    "第二十一条【父母与子女】父母对子女有抚养教育的义务；子女对父母有赡养扶助的义务。父母不履行抚养义务时，未成年的或不能独立生活的子女，有要求父母付给抚养费的权利。子女不履行赡养义务时，无劳动能力的或生活困难的父母，有要求子女付给赡养费的权利。禁止溺婴、弃婴和其他残害婴儿的行为。\n" +
                    "第四十七条【隐藏、转移共同财产等】离婚时，一方隐藏、转移、变卖、毁损夫妻共同财产，或伪造债务企图侵占另一方财产的，分割夫妻共同财产时，对隐藏、转移、变卖、毁损夫妻共同财产或伪造债务的一方，可以少分或不分。离婚后，另一方发现有上述行为的，可以向人民法院提起诉讼，请求再次分割夫妻共同财产。人民法院对前款规定的妨害民事诉讼的行为，依照民事诉讼法的规定予以制裁。\n"
            ,
            "第三十六条【离婚与子女】父母与子女间的关系，不因父母离婚而消除。离婚后，子女无论由父或母直接抚养，仍是父母双方的子女。离婚后，父母对于子女仍有抚养和教育的权利和义务。离婚后，哺乳期内的子女，以随哺乳的母亲抚养为原则。哺乳期后的子女，如双方因抚养问题发生争执不能达成协议时，由人民法院根据子女的权益和双方的具体情况判决。\n" +
                    "第二十七条【继父母与继子女】继父母与继子女间，不得虐待或歧视。继父或继母和受其抚养教育的继子女间的权利和义务，适用本法对父母子女关系的有关规定。\n" +
                    "第七条【禁止结婚】有下列情形之一的，禁止结婚：（一）直系血亲和三代以内的旁系血亲；（二）患有医学上认为不应当结婚的疾病。\n" +
                    "第三十条【尊重父母婚姻】子女应当尊重父母的婚姻权利，不得干涉父母再婚以及婚后的生活。子女对父母的赡养义务，不因父母的婚姻关系变化而终止。\n" +
                    "第四十五条【家庭暴力、虐待、遗弃犯罪】对重婚的，对实施家庭暴力或虐待、遗弃家庭成员构成犯罪的，依法追究刑事责任。受害人可以依照刑事诉讼法的有关规定，向人民法院自诉；公安机关应当依法侦查，人民检察院应当依法提起公诉。\n" +
                    "第四十三条【家庭暴力与虐待】实施家庭暴力或虐待家庭成员，受害人有权提出请求，居民委员会、村民委员会以及所在单位应当予以劝阻、调解。对正在实施的家庭暴力，受害人有权提出请求，居民委员会、村民委员会应当予以劝阻；公安机关应当予以制止。实施家庭暴力或虐待家庭成员，受害人提出请求的，公安机关应当依照治安管理处罚的法律规定予以行政处罚。\n" +
                    "第十八条【夫妻一方的财产】有下列情形之一的，为夫妻一方的财产：（一）一方的婚前财产；（二）一方因身体受到伤害获得的医疗费、残疾人生活补助费等费用；（三）遗嘱或赠与合同中确定只归夫或妻一方的财产；（四）一方专用的生活用品；（五）其他应当归一方的财产。\n"
            ,
            "第五十条【变通规定】民族自治地方的人民代表大会有权结合当地民族婚姻家庭的具体情况，制定变通规定。自治州、自治县制定的变通规定，报省、自治区、直辖市人民代表大会常务委员会批准后生效。自治区制定的变通规定，报全国人民代表大会常务委员会批准后生效。\n" +
                    "第四条【家庭关系】夫妻应当互相忠实，互相尊重；家庭成员间应当敬老爱幼，互相帮助，维护平等、和睦、文明的婚姻家庭关系。\n" +
                    "第二十三条【父母对子女的保护和教育】父母有保护和教育未成年子女的权利和义务。在未成年子女对国家、集体或他人造成损害时，父母有承担民事责任的义务。\n" +
                    " 第二百零二条当事人对已经发生法律效力的解除婚姻关系的判决、调解书，不得申请再审。\n" +
                    "第三十一条【自愿离婚】男女双方自愿离婚的，准予离婚。双方必须到婚姻登记机关申请离婚。婚姻登记机关查明双方确实是自愿并对子女和财产问题已有适当处理时，发给离婚证。\n" +
                    "第二条【婚姻制度】实行婚姻自由、一夫一妻、男女平等的婚姻制度。保护妇女、儿童和老人的合法权益。实行计划生育。\n" +
                    "第十一条【胁迫结婚】因胁迫结婚的，受胁迫的一方可以向婚姻登记机关或人民法院请求撤销该婚姻。受胁迫的一方撤销婚姻的请求，应当自结婚登记之日起一年内提出。被非法限制人身自由的当事人请求撤销婚姻的，应当自恢复人身自由之日起一年内提出。\n" +
                    "第二十八条【祖与孙】有负担能力的祖父母、外祖父母，对于父母已经死亡或父母无力抚养的未成年的孙子女、外孙子女，有抚养的义务。有负担能力的孙子女、外孙子女，对于子女已经死亡或子女无力赡养的祖父母、外祖父母，有赡养的义务。"


    };

    String tvDetail = " 第一条 根据《中华人民共和国道路交通安全法》及其实施条例、《中华人民共和国行政许可法》，制定本规定\n" +
            "         第二条 本规定由公安机关交通管理部门负责实施。;\n" +
            "         第三条 车辆管理所办理机动车驾驶证业务，应当遵循严格、公开、公正、便民的原则。\n" +
            "         第四条 申请办理机动车驾驶证业务的人，应当如实向车辆管理所提交规定的材料，如实申告规定的事项，并对其申请材料实质内容的真实性负责。\n" +
            "         第五条 公安机关交通管理部门应当建立对车辆管理所办理机动车驾驶证业务的监督制度，加强对驾驶人考试、驾驶证核发和使用的监督管理。\n" +
            "         第六条 车辆管理所应当使用机动车驾驶证计算机管理系统核发、打印机动车驾驶证，不使用计算机管理系统核发、打印的机动车驾驶证无效。\n" +
            "         第七条 驾驶机动车，应当依法取得机动车驾驶证。\n" +
            "         第九条 机动车驾驶证记载和签注以下内容：\n" +
            "         第十条 机动车驾驶证有效期分为六年、十年和长期。\n" +
            "\n";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.analysis_tuijian_item_);

         textView = findViewById(R.id.textview);
         Random random = new Random();
         textView.setText(detial[random.nextInt(6)]);

        scrollView = (ScrollView ) findViewById( R.id.scrollView_w );

        initFloatButton();
        //获取手机录音机使用权限，听写、识别、语义理解需要用到此权限
        if(ContextCompat.checkSelfPermission( TuiJIan_Analysis.this, Manifest.
                permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.RECORD_AUDIO );
        }
        //读取手机信息权限
        if(ContextCompat.checkSelfPermission( TuiJIan_Analysis.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.READ_PHONE_STATE );
        }
        //SD卡读写的权限（如果需要保存音频文件到本地的话）
        if(ContextCompat.checkSelfPermission( TuiJIan_Analysis.this, Manifest.
                permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add( Manifest.permission.READ_EXTERNAL_STORAGE );
        }
        if (! permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions( TuiJIan_Analysis.this,permissions,1 );
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
                new BottomSheet.Builder( TuiJIan_Analysis.this)
                        .title( "选择声音种类" )
                        .sheet( R.menu.change_voice )
                        .listener( new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case R.id.xiaoyan:
                                        VOICE = "xiaoyan";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 小燕 女声 青年 中英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoyu:
                                        VOICE = "xiaoyu";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 小宇 男声 青年 中英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.henry:
                                        VOICE = "henry";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 亨利 男声 青年 英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vimary:
                                        VOICE = "vimary";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 玛丽 女声 青年 英文", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaomei:
                                        VOICE = "xiaomei";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 小梅 女声 青年 中英文粤语", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vixl:
                                        VOICE = "vixl";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 小莉 女声 青年 中英文台湾普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaorong:
                                        VOICE = "xiaorong";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 小蓉 女声 青年 汉语四川话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaokun:
                                        VOICE = "xiaokun";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 小坤 男声 青年 汉语 河南话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoqiang:
                                        VOICE = "xiaoqiang";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 小强 男声 青年 汉语湖南话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vixying:
                                        VOICE = "vixying";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 小莹 女声 青年 汉语陕西话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.nannan:
                                        VOICE = "nannan";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 楠楠 女声 童年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.vils:
                                        VOICE = "vils";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 老孙 男声 老年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.xiaoxin:
                                        VOICE = "xiaoxin";
                                        Toast.makeText( TuiJIan_Analysis.this,"已选择 小新 男声 童年 汉语普通话", Toast.LENGTH_SHORT ).show();
                                        break;
                                    case R.id.cancel_chose:
                                        Toast.makeText( TuiJIan_Analysis.this,"已取消声音切换", Toast.LENGTH_SHORT ).show();
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
                if (tvDetail != null && text_to_voice.getTitle().equals("文字播报")) {
                    SpeechSynthesizer( tvDetail);
                    SpeechSynthesizer.getSynthesizer().resumeSpeaking();
                    text_to_voice.setIcon( R.drawable.ic_pause);
                    text_to_voice.setTitle( "结束播报" );
                }else if (tvDetail!= null && text_to_voice.getTitle().equals("结束播报")){
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


    /*-------------------------------语音合成--------------------------*/
    public void SpeechSynthesizer(String text){
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(TuiJIan_Analysis.this, null);

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
                Toast.makeText(TuiJIan_Analysis.this, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(TuiJIan_Analysis.this, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
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
