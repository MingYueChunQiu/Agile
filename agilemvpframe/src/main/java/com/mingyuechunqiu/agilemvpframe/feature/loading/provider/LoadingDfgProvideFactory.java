package com.mingyuechunqiu.agilemvpframe.feature.loading.provider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mingyuechunqiu.agilemvpframe.feature.loading.data.LoadingDialogFragmentOption;
import com.mingyuechunqiu.agilemvpframe.feature.loading.function.LoadingDialogFragment;
import com.mingyuechunqiu.agilemvpframe.feature.loading.function.LoadingDialogFragmentable;

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
public class LoadingDfgProvideFactory {

    /**
     * 创建加载Fragment提供者实例
     *
     * @return 返回实例对象
     */
    @NonNull
    public static LoadingDfgProviderable newInstance() {
        return newInstance(LoadingDialogFragment.newInstance());
    }

    /**
     * 创建加载Fragment提供者实例
     *
     * @return 返回实例对象
     */
    @NonNull
    public static LoadingDfgProviderable newInstance(@Nullable LoadingDialogFragmentOption option) {
        return newInstance(LoadingDialogFragment.newInstance(option));
    }

    /**
     * 创建加载Fragment提供者实例
     *
     * @param loadingDfgable 用户提供的加载Fragment实现类
     * @return 返回实例对象
     */
    @NonNull
    public static LoadingDfgProviderable newInstance(LoadingDialogFragmentable loadingDfgable) {
        return new LoadingDialogFragmentProvider(loadingDfgable);
    }
}
