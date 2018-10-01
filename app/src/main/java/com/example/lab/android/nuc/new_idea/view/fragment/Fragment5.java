package com.example.lab.android.nuc.new_idea.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.new_idea.R;

public class Fragment5 extends Fragment implements View.OnClickListener{


    public static Fragment5 newInstance(){
        Bundle bundle = new Bundle( );
        Fragment5 fragment5 = new Fragment5();
        fragment5.setArguments( bundle );
        return fragment5;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment5,container,false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}