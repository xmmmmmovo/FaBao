package com.example.lab.android.nuc.law_analysis.communication.utils;

import android.content.Context;

import com.example.lab.android.nuc.law_analysis.communication.bean.ChatMessageBean;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.Closeable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wanghao15536870732 on 18/6/07.
 */
public class Utils {

    public static String millisecsToDateString(long timestamp) {
        long gap = System.currentTimeMillis() - timestamp;
        if (gap < 1000 * 60 * 60 * 24) {
            String s = (new PrettyTime()).format(new Date(timestamp));
            return s;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
            return format.format(new Date(timestamp));
        }
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
        }
    }

    public static CharSequence getMessageeShorthand(Context context, ChatMessageBean message) {
        if (message != null) {
            int messageType = ((ChatMessageBean) message).getMessagetype();
            switch (messageType) {
                case -1:
                    return EmotionHelper.replace(context, ((ChatMessageBean) message).getUserContent());
                case -2:
                    return "[图片]";
                case -3:
                    return "[语音]";

                default:
                    return EmotionHelper.replace(context, ((ChatMessageBean) message).getUserContent());
            }

        }
        return "[]";
    }
}
