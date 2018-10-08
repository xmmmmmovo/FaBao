package com.example.lab.android.nuc.law_analysis.view.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.application.Config;
import com.example.lab.android.nuc.law_analysis.news.util.ShareUtil;
import com.example.lab.android.nuc.law_analysis.news.util.SnackBarUtil;
import com.example.lab.android.nuc.law_analysis.news.util.WebUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_github,tv_updata,tv_share,tvVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about );
        tv_github = (TextView) findViewById( R.id.tv_github );
        tv_updata = (TextView) findViewById( R.id.tv_update );
        tv_share = (TextView) findViewById( R.id.tv_share );
        tvVersion = (TextView) findViewById( R.id.tv_version );
        tv_github.setOnClickListener( this );
        tv_updata.setOnClickListener( this );
        tv_share.setOnClickListener( this );
        //添加返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle( "关于" );
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_github:
                WebUtil.openWeb(AboutActivity.this, "法宝项目主页", "https://github.com/xmmmmmovo/law-analysis");
                break;
            case R.id.tv_update:
//                OkGo.get( Config.APP_UPDATE_URL )
//                        .tag( this )
//                        .execute( new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, Call call, Response response) {
//                                Gson gson = new Gson();
//                                UpdateBean bean = gson.fromJson(s, UpdateBean.class);
//                                if (Integer.valueOf(VersionUtil.getVersionCode(getActivity())) < Integer.valueOf(bean.getVersion())) {
//                                    // 手动更新
//                                    UpdateFunGO.manualStart(getActivity());
//                                } else {
//                                    SnackBarUtil.showSnackBar("已经是最新版本~", tvVersion, this);
//                                }
//                            }
//                        } );

                SnackBarUtil.showSnackBar("已经是最新版本~", tvVersion, AboutActivity.this);
                break;
            case R.id.tv_share:
                ShareUtil.share(AboutActivity.this, "分享", "https://github.com/xmmmmmovo/law-analysis");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected( item );
    }
}
