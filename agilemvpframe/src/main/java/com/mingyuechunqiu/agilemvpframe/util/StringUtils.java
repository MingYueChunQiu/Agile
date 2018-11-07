package com.mingyuechunqiu.agilemvpframe.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/17
 *     desc   : 字符串工具类
 *     version: 1.0
 * </pre>
 */
public class StringUtils {

    /**
     * 对字符串进行MD5加密
     *
     * @param msg 需要加密的字符串
     * @return MD5值
     */
    public static String encryMD5(String msg) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(msg.getBytes());
            byte[] bytes = digest.digest();
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param bytes 需要转换的字节数组
     * @return 16进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
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
    public static boolean judgeIsNumeric(String s) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(s).matches();
    }

    /**
     * 保密手机号码
     *
     * @param phoneNumber 需要进行安全设置的手机号码
     * @return 加密完成的字符串
     */
    public static String securePhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return null;
        }
        StringBuilder sbPhoneNumber = new StringBuilder(phoneNumber);
        for (int i = 3; i < 7; i++) {
            sbPhoneNumber.setCharAt(i, '*');
        }
        return sbPhoneNumber.toString();
    }
}
