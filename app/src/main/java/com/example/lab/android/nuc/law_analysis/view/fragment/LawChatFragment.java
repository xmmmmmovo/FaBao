package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.law_analysis.R;
/*
法律讲坛的fragment
 */
public class LawChatFragment extends Fragment implements View.OnClickListener{


    public static LawChatFragment newInstance(){
        Bundle bundle = new Bundle( );
        LawChatFragment lawChatFragment = new LawChatFragment();
        lawChatFragment.setArguments( bundle );
        return lawChatFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_law_chat,container,false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
