package com.mingyuechunqiu.agile.util;

import android.text.TextUtils;

import org.jetbrains.annotations.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 *      Project:    DoomsdayTaoistSanctuary
 *
 *      author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-05-04 22:27
 *      Desc:       加密工具类
 *      Version:    1.0
 * </pre>
 */
public class EncryptionUtils {

    @Nullable
    public static byte[] encrypt64MD5(String msg) {
        byte[] bytes = encryptMD5(msg);
        if (bytes == null) {
            return null;
        }
        byte[] newBytes = new byte[64];
        for (int i = 0; i < 64; i++) {
            newBytes[i] = bytes[i % 16];
        }
        return newBytes;
    }

    /**
     * 对字符串进行MD5加密
     *
     * @param msg 需要加密的字符串
     * @return MD5值
     */
    @Nullable
    public static byte[] encryptMD5(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(msg.getBytes());
            return digest.digest();
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
}
