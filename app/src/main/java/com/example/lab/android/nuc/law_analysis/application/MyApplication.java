package com.example.lab.android.nuc.law_analysis.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.multidex.MultiDexApplication;

import com.example.lab.android.nuc.law_analysis.communication.bean.DaoMaster;
import com.example.lab.android.nuc.law_analysis.communication.bean.DaoSession;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends MultiDexApplication{

    private static MyApplication instance;
    private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
    public static Typeface canaroExtraBold;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private DaoSession daoSession;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initTypeface();
    }

    private void initTypeface() {
        canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5bb37e54");
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "chat-message", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
        initImageLoader(this);
        instance = this;
    }

    /**
     * 初始化ImageLoader
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
                //.memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder( QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }
}