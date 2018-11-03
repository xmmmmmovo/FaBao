package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.GeneralParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.example.lab.android.nuc.law_analysis.adapter.Main_Analysis_Adapter;
import com.example.lab.android.nuc.law_analysis.base.DataBean;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.base.Result;
import com.example.lab.android.nuc.law_analysis.communication.activity.DictationResult;
import com.example.lab.android.nuc.law_analysis.communication.utils.KeyBoardUtils;
import com.example.lab.android.nuc.law_analysis.util.views.RecycleViewDivider2;
import com.example.lab.android.nuc.law_analysis.utils.iflytek.IflytekSpeech;
import com.example.lab.android.nuc.law_analysis.utils.iflytek.Iflytekrecognize;
import com.example.lab.android.nuc.law_analysis.utils.iflytek.iflytekWakeUp;
import com.example.lab.android.nuc.law_analysis.utils.tools.FileUtil;
import com.example.lab.android.nuc.law_analysis.view.activity.Analysis_Similar_Item;
import com.example.lab.android.nuc.law_analysis.view.activity.MainActivity;
import com.example.lab.android.nuc.law_analysis.view.activity.TuiJIan_Analysis;
import com.example.lab.android.nuc.law_analysis.view.activity.pictureTotext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 条目分析 语音输入分析
 * */
public class MainAnalysisFragment extends Fragment {

    private String filePath;
    //语音唤醒
    public Iflytekrecognize rec;
    public iflytekWakeUp wkup;
    private boolean ison = false;
    public IflytekSpeech spe;
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private static final int REQUEST_CODE_CAMERA = 102;
    private RecyclerView recyclerView_Analyis_Law;
    private List<DataBean> lists;
    private Main_Analysis_Adapter adapter_Analyis_Law;

    private Button button_delete;

    String AK = "0MzGKRNmALB5P7ErEGocQ7wZ";
    String SK ="G2P1W3gaXTV4KuAtp9ZZnBxLOz06fj5D";

    private EditText editText_Analyis;

    private FloatingActionButton button_YuYin;

    private FloatingActionButton button_PaiZhao;
    private RecognizerDialog iatDialog;
    private TextView Tv1;
    private Button button;
    private AlertDialog.Builder alertDialog;
    private boolean hasGotToken = false;
    private ProgressBar mProgressBar;

    private  View view;
    //实例化
    public static MainAnalysisFragment newInstance() {
        Bundle args = new Bundle();

    MainAnalysisFragment fragment = new MainAnalysisFragment();
        fragment.setArguments(args);
        return fragment;
}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        view = inflater.inflate( R.layout.main_analysis_fragment,container,false);
        initData();
        recyclerView_Analyis_Law = view.findViewById(R.id.recyclerview_analyis);
        button_delete =view.findViewById(R.id.bt_delete_text)  ;
        editText_Analyis = view.findViewById(R.id.edittext_analyis);
        button_YuYin = view.findViewById(R.id.bt_yuyin);
        button_PaiZhao = view.findViewById(R.id.bt_paizhao);
        button = view.findViewById(R.id.analysis_button);
        mProgressBar = (ProgressBar) view.findViewById( R.id.main_progress );
                button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_Analyis.setText("");
            }
        });
        //语音识别

        button_YuYin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.wkup.destroyWakeuper();
                voice_to_text();
            }
        });


        editText_Analyis.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Toast.makeText(getActivity(), "正在为你搜索...", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),TuiJIan_Analysis.class);
                startActivity(i);
            }
        });

        //文字识别  返回的字可以 是整个字符串

        button_PaiZhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_Analyis.setText( "" );
                startActivity( new Intent( getActivity(),pictureTotext.class ) );
            }

        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());


        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter_Analyis_Law = new Main_Analysis_Adapter(getActivity(),lists);

        recyclerView_Analyis_Law.addItemDecoration(new RecycleViewDivider2(getActivity(),layoutManager.getOrientation()));
              recyclerView_Analyis_Law.setItemAnimator(new DefaultItemAnimator());
        recyclerView_Analyis_Law.setLayoutManager(layoutManager);
        adapter_Analyis_Law.setHasStableIds(true);

       // recyclerView_Analyis_Law.setAdapter(new AlphaInAnimationAdapter(adapter_Analyis_Law));
           recyclerView_Analyis_Law.setAdapter(adapter_Analyis_Law);



        adapter_Analyis_Law.setOnItemClickListener(new Main_Analysis_Adapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Main_Analysis_Adapter.ItemHolder viewHolder = (Main_Analysis_Adapter.ItemHolder)recyclerView_Analyis_Law.findViewHolderForLayoutPosition(position);

                Tv1 = viewHolder.demo_textview;

                switch(view.getId()){
                    case R.id.textview_anli_item:
                        Intent i = new Intent(getActivity(),Analysis_Similar_Item.class);
                        startActivity(i);
                }

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Result.getResult() != null) {
            editText_Analyis.setText( Result.getResult().toString() );
            editText_Analyis.setSelection(Result.getResult().length());
        }
    }

    private void alertText(final String title, final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }


    private void infoPopText(final String result) {
        alertText("", result);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode  == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK){
            filePath = FileUtil.getSaveFile(getActivity().getApplicationContext()).getAbsolutePath();
            recGeneral( filePath );
        }
    }

    private void recGeneral(String filePath) {
        GeneralParams param = new GeneralParams();
        param.setDetectDirection(true);
        param.setImageFile(new File(filePath));
        OCR.getInstance(getActivity()).recognizeAccurate(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                StringBuilder sb = new StringBuilder();
                for (WordSimple word : result.getWordList()) {
                    sb.append(word.getWords());
                    sb.append("\n");
                }
                editText_Analyis.setText(sb);
            }

            @Override
            public void onError(OCRError error) {
                editText_Analyis.setText(error.getMessage());
            }
        });
    }


    @Override
   public void onDestroy() {
        super.onDestroy();
    }

    /*语音转文字*/
    public void voice_to_text(){
        // 有交互动画的语音识别器
        iatDialog = new RecognizerDialog(getActivity(), mInitListener);

        iatDialog.setListener(new RecognizerDialogListener() {
            String resultJson = "[";//放置在外边做类的变量则报错，会造成json格式不对（？）

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                System.out.println("-----------------   onResult   -----------------");
                if (!isLast) {
                    resultJson += recognizerResult.getResultString() + ",";
                } else {
                    resultJson += recognizerResult.getResultString() + "]";
                }

                if (isLast) {
                    //解析语音识别后返回的json格式的结果
                    Gson gson = new Gson();
                    List<DictationResult> resultList = gson.fromJson(resultJson,
                            new TypeToken<List<DictationResult>>() {
                            }.getType());
                    String result = "";
                    for (int i = 0; i < resultList.size() - 1; i++) {
                        result += resultList.get(i).toString();
                    }
                    editText_Analyis.setText(result);
                    //获取焦点
                    editText_Analyis.requestFocus();
                    //将光标定位到文字最后，以便修改
                    editText_Analyis.setSelection(result.length());
                    if (view.findViewById(R.id.cbv_other) != null) {
                       view.findViewById( R.id.cbv_other ).setVisibility( View.GONE );
                    }
                    KeyBoardUtils.showKeyBoard( getActivity(),editText_Analyis );
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                //自动生成的方法存根
                speechError.getPlainDescription(true);
            }
        });
        //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下（没有的话自己新建）
        iatDialog.show();
    }

    /**
     * 用于SpeechRecognizer（无交互动画）对象的监听回调
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Log.i(TAG, recognizerResult.toString());
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    public static final String TAG = "MainActivity";
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(getActivity(), "初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };



    private void initData() {
        lists = new ArrayList<>();
        lists.add(new DataBean("2016年12月20日，王某某因肇事逃逸被判刑......."));
        lists.add(new DataBean("2018年2月李某某因撞到行人，弃车逃跑....."));
        lists.add(new DataBean("2017年10月赵某某路上没遵守交通规则，被大货车撞到....."));
    }

}
