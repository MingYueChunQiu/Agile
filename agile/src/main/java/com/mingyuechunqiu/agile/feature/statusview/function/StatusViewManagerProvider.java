package com.mingyuechunqiu.agile.feature.statusview.function;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;
import com.mingyuechunqiu.agile.frame.ui.IAgilePage;

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

    private static volatile StatusViewConfigure sConfigure;

    private StatusViewManagerProvider() {
    }

    /**
     * 创建加载Fragment提供者实例
     *
     * @return 返回实例对象
     */
    @NonNull
    public static IStatusViewManager newInstance(@NonNull IAgilePage page) {
        return newInstance(page, new StatusViewHelper());
    }

    /**
     * 创建状态视图管理器实例对象
     *
     * @param helper 用户提供的状态视图辅助类
     * @return 返回状态视图管理器实例对象
     */
    @NonNull
    public static IStatusViewManager newInstance(@NonNull IAgilePage page, @NonNull IStatusViewHelper helper) {
        IStatusViewManager manager = new StatusViewManager(helper);
        page.getLifecycle().addObserver(manager);
        return manager;
    }

    @Nullable
    public static StatusViewConfigure getGlobalConfigure() {
        return sConfigure;
    }

    public static synchronized void applyGlobalConfigure(@Nullable StatusViewConfigure configure) {
        StatusViewManagerProvider.sConfigure = configure;
    }

    public static StatusViewOption getGlobalStatusViewOptionByType(@NonNull StatusViewConstants.StatusViewType type) {
        return StatusViewHandler.getGlobalStatusViewOptionByType(type);
    }
}
