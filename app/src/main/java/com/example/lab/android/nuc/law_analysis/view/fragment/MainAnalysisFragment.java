package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.law_analysis.util.tools.GlideImageLoader;
import com.example.lab.android.nuc.law_analysis.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import java.util.zip.Inflater;
/**
 * 条目分析 语音输入分析
 * */
public class MainAnalysisFragment extends Fragment {

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
        View view = inflater.inflate( R.layout.main_analysis_fragment,container,false);
        return view;
    }



}
