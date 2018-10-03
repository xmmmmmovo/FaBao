package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab.android.nuc.law_analysis.R;

public class SearchLawListFragment extends Fragment implements View.OnClickListener {

    public static SearchLawListFragment newInstance() {
        Bundle args = new Bundle();
        
        SearchLawListFragment fragment = new SearchLawListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_law_list_fragment, container, false);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
