package com.mingyuechunqiu.agile.feature.statusview.function;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : 加载Fragment对外提供者工厂类
 *     version: 1.0
 * </pre>
 */
public final class StatusViewManagerProvider {

    private static StatusViewConfigure sConfigure;

    private StatusViewManagerProvider() {
    }

    /**
     * 创建加载Fragment提供者实例
     *
     * @return 返回实例对象
     */
    @NonNull
    public static IStatusViewManager newInstance() {
        return newInstance(LoadingDialogFragment.newInstance());
    }

    /**
     * 创建加载Fragment提供者实例
     *
     * @return 返回实例对象
     */
    @NonNull
    public static IStatusViewManager newInstance(@Nullable StatusViewOption option) {
        return newInstance(LoadingDialogFragment.newInstance(option));
    }

    /**
     * 创建加载Fragment提供者实例
     *
     * @param helper 用户提供的加载Fragment实现类
     * @return 返回实例对象
     */
    @NonNull
    public static IStatusViewManager newInstance(ILoadingHelper helper) {
        return new StatusViewManager(helper);
    }

    public static StatusViewConfigure getGlobalConfigur() {
        return sConfigure;
    }

    public static void applyGlobalConfigure(@Nullable StatusViewConfigure configure) {
        StatusViewManagerProvider.sConfigure = configure;
    }
}
