package com.example.lab.android.nuc.new_idea.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.new_idea.R;

public class Fragment3 extends Fragment implements View.OnClickListener{


    public static Fragment3 newInstance(){
        Bundle bundle = new Bundle( );
        Fragment3 fragment3 = new Fragment3();
        fragment3.setArguments( bundle );
        return fragment3;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment3,container,false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
