package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.law_analysis.R;

public class Fragment2 extends Fragment implements View.OnClickListener{


    public static Fragment2 newInstance(){
        Bundle bundle = new Bundle( );
        Fragment2 fragment2 = new Fragment2();
        fragment2.setArguments( bundle );
        return fragment2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment2,container,false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
