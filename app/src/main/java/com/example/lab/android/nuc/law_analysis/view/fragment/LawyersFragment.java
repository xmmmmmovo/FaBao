package com.example.lab.android.nuc.law_analysis.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lab.android.nuc.law_analysis.adapter.LawyerAdapter;
import com.example.lab.android.nuc.law_analysis.base.Lawyer;
import com.example.lab.android.nuc.law_analysis.R;

import java.util.ArrayList;

/*
律师联系人的fragmnet
 */
public class LawyersFragment extends Fragment implements View.OnClickListener,SearchView.OnQueryTextListener {

    private RecyclerView mRecyclerView;
    private ArrayList<Lawyer> mSearchLawyerList,filterLawyerList;
    private Lawyer mLawyer;
    private LawyerAdapter mLawyerAdapter;
    private EditText mEditText;

    public static LawyersFragment newInstance(){
        Bundle bundle = new Bundle( );
        LawyersFragment lawyersFragment = new LawyersFragment();
        lawyersFragment.setArguments( bundle );
        return lawyersFragment;
    }
    private OnRecyclerviewItemClickListener mOnRecyclerviewItemClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_lawyers,container,false);
        initLawyer();
        mOnRecyclerviewItemClickListener = new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClickListaner(View v, int position) {

            }
        };

        mRecyclerView = (RecyclerView) view.findViewById( R.id.card_recycler_view );

        mRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        mLawyerAdapter = new LawyerAdapter( mSearchLawyerList,mOnRecyclerviewItemClickListener );

        mRecyclerView.setAdapter( mLawyerAdapter );
        mRecyclerView.addItemDecoration( new DividerItemDecoration( getContext(),DividerItemDecoration.VERTICAL ) );
        SearchView mSearchView = (SearchView) view.findViewById( R.id.searchView );
        mSearchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterLawyerList = (ArrayList<Lawyer>) filter( mSearchLawyerList,newText );
                mLawyerAdapter.setFilter( filterLawyerList );
                return true;
            }
        } );
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    private void initLawyer(){
        mSearchLawyerList = new ArrayList<>(  );
        mSearchLawyerList.add( new Lawyer( "白云华","一级律师", "李国机律师事务所",
                "民商法","http://pd35yssng.bkt.clouddn.com/lawyer1.jpg") );
        mSearchLawyerList.add( new Lawyer( "萧晓林","初级律师", "内蒙古松川律师事务所",
                "婚姻法","http://pd35yssng.bkt.clouddn.com/lawyer2.jpg") );
        mSearchLawyerList.add( new Lawyer( "胡建国","高级律师", "科贝律师事务所",
                "行政法","http://pd35yssng.bkt.clouddn.com/images.jpg") );
        mSearchLawyerList.add( new Lawyer( "陈克刚","特级律师", "宁丰律师事务所",
                "交通运输法","http://pd35yssng.bkt.clouddn.com/lawyer4.jpg") );
        mSearchLawyerList.add( new Lawyer( "张世豪","二级律师", "佳镜律师事务所",
                "知识产权法","http://pd35yssng.bkt.clouddn.com/u=720330820,773254810&fm=26&gp=0.jpg") );
        mSearchLawyerList.add( new Lawyer( "万正学","特级律师", "隆安律师事务所",
                "刑事诉讼法","http://pd35yssng.bkt.clouddn.com/lawyer6.jpg") );
        mSearchLawyerList.add( new Lawyer( "李星亮","一级律师", "中诚律师事务所",
                "婚姻发","http://pd35yssng.bkt.clouddn.com/lawyer7.jpg") );
        mSearchLawyerList.add( new Lawyer( "郎坚","三级律师", "君翔律师事务所",
                "民商法","http://pd35yssng.bkt.clouddn.com/lawyer8.jpg") );
        mSearchLawyerList.add( new Lawyer( "王立建","初级律师", "神明律师事务所",
                "税务法","http://pd35yssng.bkt.clouddn.com/lawyer9.jpg") );
        mSearchLawyerList.add( new Lawyer( "张连民","二级律师", "毅达律师事务所",
                "行政法","http://pd35yssng.bkt.clouddn.com/lawyer10.jpg") );

    }

    private ArrayList<Lawyer> filter(ArrayList<Lawyer> dataList, String newText) {
        newText = newText.toLowerCase();
        String text;
        filterLawyerList = new ArrayList<>();
        for(Lawyer dataFromDataList:mSearchLawyerList){
            text = dataFromDataList.lawyer_name.toLowerCase();

            if(text.contains(newText)){
                filterLawyerList.add(dataFromDataList);
            }
        }
        return filterLawyerList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterLawyerList = filter(mSearchLawyerList,newText);
        mLawyerAdapter.setFilter( filterLawyerList );
        return true;
    }

    public interface OnRecyclerviewItemClickListener{
        void onItemClickListaner(View v,int position);
    }
}