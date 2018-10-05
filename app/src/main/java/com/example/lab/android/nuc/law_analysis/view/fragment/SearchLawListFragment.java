package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.view.activity.Search_Intent_Activity;
import com.tangguna.searchbox.library.widget.SearchLayout;

public class SearchLawListFragment extends Fragment  {


    private View view;
    private EditText editText;

    int y;

    public static SearchLawListFragment newInstance() {
        Bundle args = new Bundle();
        
        SearchLawListFragment fragment = new SearchLawListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_law_list_fragment, container, false);

        editText = view.findViewById(R.id.et_searchtext_search);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Search_Intent_Activity.class);
                startActivity(i);
            }
        });




        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            TranslateAnimation animation = new TranslateAnimation(0, 0, -y, 0);
            animation.setDuration(520);
            animation.setFillAfter(true);
            getView().startAnimation(animation);
        }
    }

    public View getView() {
        return view;
    }
}
