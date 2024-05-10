package com.mingyuechunqiu.agile.io;

import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

import java.io.Closeable;
import java.io.IOException;

/**
 * <pre>
 *     author : xyj
 *     Github : <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/10
 *     desc   : IO工具类
 *     version: 1.0
 * </pre>
 */
public final class IOHelper {

    private static final String TAG = "IOUtils";

    private IOHelper() {
    }

    /**
     * 关闭流的操作，供子类调用
     *
     * @param streams 需要关闭的流
     */
    public static void closeStreams(@Nullable Closeable... streams) {
        if (streams == null) {
            return;
        }
        try {
            for (Closeable stream : streams) {
                if (stream == null) {
                    continue;
                }
                stream.close();
            }
        } catch (IOException e) {
            LogManagerProvider.e(TAG, e.getMessage());
        }
    }
}
