package com.example.lab.android.nuc.law_analysis.news.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.lab.android.nuc.law_analysis.R;
import com.example.lab.android.nuc.law_analysis.application.Config;
import com.example.lab.android.nuc.law_analysis.news.bean.NewsDetailBean;
import com.example.lab.android.nuc.law_analysis.news.util.ListUtils;
import com.example.lab.android.nuc.law_analysis.news.util.SnackBarUtil;
import com.example.lab.android.nuc.law_analysis.news.util.WebUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zzhoujay.richtext.RichText;
import java.lang.reflect.Method;
import okhttp3.Call;
import okhttp3.Response;

/*
新闻的具体界面
 */
public class NewsDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ScrollView scrollView;
    private FloatingActionButton mFloatingActionButton;

    private String postId;

    private String title;

    private NewsDetailBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news_detail );
        toolbar = (Toolbar) findViewById( R.id.toolbar_news );
        tvTitle = (TextView) findViewById( R.id.tv_title );
        tvDetail = (TextView) findViewById(R.id.news_detail_body );
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById( R.id.swipefreshlayout );
        scrollView = (ScrollView ) findViewById( R.id.scrollView_news );
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById( R.id.swipefreshlayout );
        mFloatingActionButton = (FloatingActionButton) findViewById( R.id.fab );
        mFloatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        } );
        init();
    }

    protected void init() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.black));
        swipeRefreshLayout.setOnRefreshListener(this);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_toolbar_back));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        postId = getIntent().getStringExtra("postid");
        title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
        getDetail();
    }

    public void getDetail() {
      OkGo.get( Config.getNewsDetailUrl( postId ) )
              .tag( this )
              .execute( new StringCallback() {
                  @Override
                  public void onSuccess(String s, Call call, Response response) {
                      try {
                          Gson gson = new Gson();
                          JsonParser parser = new JsonParser();
                          JsonObject jsonObj = parser.parse(s).getAsJsonObject();
                          JsonElement jsonElement = jsonObj.get(postId);
                          if (null == jsonElement) {
                              SnackBarUtil.showSnackBar(R.string.loadfail, toolbar, NewsDetailActivity.this);
                          } else {
                              JsonObject jsonObject = jsonElement.getAsJsonObject();
                              bean = gson.fromJson(jsonObject, NewsDetailBean.class);
                              setDetail(bean);
                          }
                      } catch (Exception e) {
                          SnackBarUtil.showSnackBar(R.string.loadfail, toolbar, NewsDetailActivity.this);
                      }
                      if (null != swipeRefreshLayout) {
                          swipeRefreshLayout.setRefreshing(false);
                      }
                  }

                  @Override
                  public void onError(okhttp3.Call call, Response response, Exception e) {
                      super.onError(call, response, e);
                      SnackBarUtil.showSnackBar(e.getMessage(), toolbar, NewsDetailActivity.this);
                      if (null != swipeRefreshLayout) {
                          swipeRefreshLayout.setRefreshing(false);
                      }
                  }
              } );

    }

    public void setDetail(NewsDetailBean bean) {
        RichText.from(handleRichText(bean))
                .bind( this )
                .placeHolder( R.drawable.ic_default )
                .singleLoad( false )
                .into(tvDetail);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_webview:
                WebUtil.openWeb(NewsDetailActivity.this, bean.getTitle(), bean.getShareLink());
                break;
            case R.id.item_browser:
                Uri uri = Uri.parse(bean.getShareLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case android.R.id.home:
                // 回退
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        setIconEnable(menu, true);//让在overflow中的menuitem的icon显示
        return super.onPrepareOptionsPanel(view, menu);
    }



    /**
     * 利用反射机制调用MenuBuilder中的setOptionIconsVisable（），
     * 如果是集成自AppCompatActivity则不行,需要在onPreareOptionPanel（）中调用该方法
     *
     * @param menu   该menu实质为MenuBuilder，该类实现了Menu接口
     * @param enable enable为true时，菜单添加图标有效，enable为false时无效。因为4.0系统之后默认无效
     */

    private void setIconEnable(Menu menu, boolean enable) {
        if (menu != null) {
            try {
                Class clazz = menu.getClass();
                if (clazz.getSimpleName().equals("MenuBuilder")) {
                    Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
                    m.invoke(menu, enable);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理富文本包含图片的情况
     *
     * @param newsDetailBean 原始数据
     */
    private String handleRichText(NewsDetailBean newsDetailBean) {
        String detailBody;
        if (!ListUtils.isEmpty(newsDetailBean.getImg())) {
            String body = newsDetailBean.getBody();
            for (NewsDetailBean.ImgBean imgEntity : newsDetailBean.getImg()) {
                String ref = imgEntity.getRef();
                String src = imgEntity.getSrc();
                String img = HTML_IMG_TEMPLATE.replace("http", src);
                Log.i( "wanghao",img );
                body = body.replaceAll(ref, img);
            }
            detailBody = body;
        } else {
            detailBody = newsDetailBean.getBody();
        }
        return detailBody;
    }

    private static final String HTML_IMG_TEMPLATE = "<img src=\"http\" />";

    @Override
    public void onRefresh() {
        getDetail();
    }

}
