package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.WordSimple;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.example.lab.android.nuc.law_analysis.adapter.Main_Analysis_Adapter;
import com.example.lab.android.nuc.law_analysis.base.DataBean;

import com.example.lab.android.nuc.law_analysis.R;

import com.example.lab.android.nuc.law_analysis.communication.activity.ChatActivity;
import com.example.lab.android.nuc.law_analysis.communication.activity.DictationResult;
import com.example.lab.android.nuc.law_analysis.communication.utils.KeyBoardUtils;
import com.example.lab.android.nuc.law_analysis.main_analysis.ImagetoText;
import com.example.lab.android.nuc.law_analysis.util.tools.FileUtil;
import com.example.lab.android.nuc.law_analysis.util.tools.RecognizeService;
import com.example.lab.android.nuc.law_analysis.util.views.RecycleViewDivider2;
import com.example.lab.android.nuc.law_analysis.view.activity.MainActivity;
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



import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;




/**
 * 条目分析 语音输入分析
 * */
public class MainAnalysisFragment extends Fragment {

    private static final int REQUEST_CODE_GENERAL_BASIC = 106;
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




    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    private AlertDialog.Builder alertDialog;;

    private boolean hasGotToken = false;

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
               voice_to_text();
            }
        });



        //文字识别

        button_PaiZhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getActivity().getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_GENERAL_BASIC);
            }

        });

        initAccessTokenWithAkSk();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());


        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter_Analyis_Law = new Main_Analysis_Adapter(getActivity(),lists);

        recyclerView_Analyis_Law.addItemDecoration(new RecycleViewDivider2(getActivity(),layoutManager.getOrientation()));
              recyclerView_Analyis_Law.setItemAnimator(new DefaultItemAnimator());
        recyclerView_Analyis_Law.setLayoutManager(layoutManager);
        adapter_Analyis_Law.setHasStableIds(true);

        recyclerView_Analyis_Law.setAdapter(new AlphaInAnimationAdapter(adapter_Analyis_Law));
        //   recyclerView_Analyis_Law.setAdapter(adapter_Analyis_Law);

        return view;

    }




    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }



    private void initAccessTokenWithAkSk() {
        OCR.getInstance(getActivity()).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getContext(),  AK, SK);
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
        super.onActivityResult(requestCode, resultCode, data);

        // 识别成功回调，通用文字识别
        if (requestCode == REQUEST_CODE_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
//            RecognizeService.recGeneralBasic(getActivity(), FileUtil.getSaveFile(getContext()).getAbsolutePath(),
////                    new RecognizeService.ServiceListener() {
////                        @Override
////                        public void onResult(String result) {
////                          editText_Analyis.setText(result);
////                        }
////                    });
            recGeneral(FileUtil.getSaveFile(getContext()).getAbsolutePath());
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
        // 释放内存资源
        OCR.getInstance(getActivity()).release();
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
        lists.add(new DataBean("2016年12月20日，王某某因肇事逃逸被罚款........"));
        lists.add(new DataBean("2018年2月李某某因土地纠纷，把当地负责人告上法庭....."));
        lists.add(new DataBean("2017年10月赵莫某因版权问题把李某某告上法庭....."));
    }

}
