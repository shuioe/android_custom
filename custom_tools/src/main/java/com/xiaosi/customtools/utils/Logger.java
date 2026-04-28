package com.xiaosi.customtools.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.MessageFormat;

/**
 * 日志打印工具
 */
public class Logger {

    private static String TAG = "CustomTools";

    /**
     * 设置日志中的tagName
     * @param tagName tagName
     */
    public static void setTagName(String tagName) {
        if (TextUtils.isEmpty(tagName)) {
            Log.e(TAG, "setTagName: param is empty");
            return;
        }
        TAG = tagName;
    }

    /**
     * debug日志
     *
     * @param tag     类tag
     * @param message 具体信息
     * @param params  format参数
     */
    public static void debug(String tag, String message, Object... params) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message)) {
            Log.e(TAG, "Logger.debug param is null");
            return;
        }
        String logMessage;
        if (params == null || params.length == 0) {
            logMessage = tag + " - " + message;
        } else {
            logMessage = tag + " - " + MessageFormat.format(message, params);
        }
        Log.d(TAG, logMessage);
    }

    /**
     * info日志
     *
     * @param tag     类tag
     * @param message 具体信息
     * @param params  format参数
     */
    public static void info(String tag, String message, Object... params) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message)) {
            Log.e(TAG, "Logger.info param is null");
            return;
        }

        String logMessage;
        if (params == null || params.length == 0) {
            logMessage = tag + " - " + message;
        } else {
            // MessageFormat.format 接受可变参数，直接传入 params 即可
            logMessage = tag + " - " + MessageFormat.format(message, params);
        }

        Log.i(TAG, logMessage);
    }
    /**
     * error日志
     *
     * @param tag     类tag
     * @param message 具体信息
     * @param params  format参数
     */
    public static void error(String tag, String message, Object... params) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(message)) {
            Log.e(TAG, "Logger.error param is null");
            return;
        }
        String logMessage;
        if (params == null || params.length == 0) {
            logMessage = tag + " - " + message;
        } else {
            logMessage = tag + " - " + MessageFormat.format(message, params);
        }
        Log.e(TAG, logMessage);
    }
}
