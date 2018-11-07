package com.example.lab.android.nuc.law_analysis.utils;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SQLiteUtils {

    /**
     * 将打包在app中的数据库文件放到手机内存里面
     * 并返回数据库文件
     * */
    public static SQLiteDatabase getDatabase(Context context) throws IOException {
        File file = new File("data/data/com.example.lab.android.nuc.law_analysis/database/lawList.db");
        SQLiteDatabase sqLiteDatabase;

        if (file.exists()){
            return SQLiteDatabase.openOrCreateDatabase(file, null);
        }else {
            File dir = new File("data/data/com.example.lab.android.nuc.law_analysis/database");
            dir.mkdir();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("lawList.db");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];//按字节存入
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0){
                fileOutputStream.write(buffer, 0, count);
            }
            //关闭输入输出流
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
        }

        return getDatabase(context);//存储完成之后从新调用这个函数返回数据库文件
    }
}
