/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.example.lab.android.nuc.law_analysis.utils.tools;

import java.io.File;

import android.content.Context;

public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
}
