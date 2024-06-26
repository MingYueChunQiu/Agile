package com.mingyuechunqiu.agile.util;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/17
 *     desc   : 字符串工具类
 *     version: 1.0
 * </pre>
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * 对字符串进行MD5加密
     *
     * @param msg 需要加密的字符串
     * @return MD5值
     */
    @Nullable
    public static String encryptMD5(@Nullable String msg) {
        if (TextUtils.isEmpty(msg)) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(msg.getBytes());
            byte[] bytes = digest.digest();
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            LogManagerProvider.e("StringUtils:encryptMD5", e.getMessage());
            return null;
        }
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param bytes 需要转换的字节数组
     * @return 16进制字符串
     */
    @Nullable
    public static String bytesToHex(@Nullable byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, length = bytes.length; j < length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * 判断一个字符串是否都是数字
     *
     * @param s 需要判断的字符串
     * @return 判断结果
     */
    public static boolean judgeIsNumeric(@Nullable String s) {
        if (TextUtils.isEmpty(s)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(s).matches();
    }

    /**
     * 保密手机号码
     *
     * @param phoneNumber 需要进行安全设置的手机号码
     * @return 加密完成的字符串
     */
    @Nullable
    public static String securePhoneNumber(@Nullable String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return null;
        }
        StringBuilder sbPhoneNumber = new StringBuilder(phoneNumber);
        for (int i = 3; i < 7; i++) {
            sbPhoneNumber.setCharAt(i, '*');
        }
        return sbPhoneNumber.toString();
    }

    /**
     * 创建指定颜色的URL字符Span
     *
     * @param source  源字符串
     * @param urlText 链接文字
     * @param url     链接地址
     * @param color   链接及链接文字颜色
     * @return 返回Span
     */
    @Nullable
    public static SpannableStringBuilder createColorUrlSpan(@Nullable final String source, @Nullable final String urlText,
                                                            @Nullable final String url, int color,
                                                            @NonNull final OnClickUrlLinkListener listener) {
        if (TextUtils.isEmpty(source) || TextUtils.isEmpty(urlText) || !source.contains(urlText)
                || TextUtils.isEmpty(url)) {
            return null;
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(source);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                listener.onClickUrlLink(source, urlText, url);
            }
        };
        int start = source.indexOf(urlText);
        int end = start + urlText.length();
        ssb.setSpan(clickableSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ssb.setSpan(underlineSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        ssb.setSpan(colorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    /**
     * 当点击网址链接时回调时间
     */
    public interface OnClickUrlLinkListener {

        void onClickUrlLink(@NonNull String source, @NonNull String urlText, @NonNull String url);
    }
}
