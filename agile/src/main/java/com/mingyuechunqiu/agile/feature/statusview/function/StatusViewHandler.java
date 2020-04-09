package com.mingyuechunqiu.agile.feature.statusview.function;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewConfigure;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewOption;
import com.mingyuechunqiu.agile.feature.statusview.bean.StatusViewTextOption;
import com.mingyuechunqiu.agile.feature.statusview.constants.StatusViewConstants;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-30 15:32
 *      Desc:       状态视图处理器
 *      Version:    1.0
 * </pre>
 */
final class StatusViewHandler {

    private StatusViewHandler() {
    }

    /**
     * 根据状态类型获取对应状态视图配置信息对象
     *
     * @param type 状态视图类型
     * @return 返回状态视图配置信息对象
     */
    @NonNull
    static StatusViewOption getGlobalStatusViewOptionByType(@NonNull StatusViewConstants.StatusType type) {
        StatusViewOption option = getStatusViewOptionByType(StatusViewManagerProvider.getGlobalConfigure(), type, true);
        if (option == null) {
            option = new StatusViewOption();
        }
        return option;
    }

    /**
     * 根据状态类型获取对应状态视图配置信息对象
     *
     * @param configure       状态视图配置信息对象
     * @param type            状态视图类型
     * @param useDefaultValue 是否在没有配置内容时使用默认值
     * @return 返回状态视图配置信息对象
     */
    @Nullable
    static StatusViewOption getStatusViewOptionByType(@Nullable StatusViewConfigure configure, @NonNull StatusViewConstants.StatusType type, boolean useDefaultValue) {
        StatusViewOption option = null;
        switch (type) {
            case TYPE_LOADING:
                option = configure == null || configure.getLoadingOption() == null
                        ? (useDefaultValue ? getLoadingStatusViewOption() : null) : configure.getLoadingOption();
                break;
            case TYPE_EMPTY:
                option = configure == null || configure.getEmptyOption() == null
                        ? (useDefaultValue ? getEmptyStatusViewOption() : null) : configure.getEmptyOption();
                break;
            case TYPE_NETWORK_ANOMALY:
                option = configure == null || configure.getNetworkAnomalyOption() == null
                        ? (useDefaultValue ? getNetworkAnomalyStatusViewOption() : null) : configure.getNetworkAnomalyOption();
                break;
            case TYPE_ERROR:
                option = configure == null || configure.getErrorOption() == null
                        ? (useDefaultValue ? getErrorStatusViewOption() : null) : configure.getErrorOption();
                break;
            case TYPE_CUSTOM:
                option = configure == null || configure.getCustomOption() == null
                        ? (useDefaultValue ? getCustomStatusViewOption() : null) : configure.getCustomOption();
                break;
        }
        return option;
    }

    private static StatusViewOption getLoadingStatusViewOption() {
        return new StatusViewOption.Builder()
                .setShowProgressView(true)
                .setContentOption(new StatusViewTextOption.Builder()
                        .setText("正在加载中……")
                        .build())
                .setCancelWithOutside(false)
                .build();
    }

    private static StatusViewOption getEmptyStatusViewOption() {
        return new StatusViewOption.Builder()
                .setShowReloadIcon(true)
                .setShowReloadText(true)
                .setContentOption(new StatusViewTextOption.Builder()
                        .setText("没有内容哦")
                        .build())
                .build();
    }

    private static StatusViewOption getNetworkAnomalyStatusViewOption() {
        return new StatusViewOption.Builder()
                .setShowReloadIcon(true)
                .setShowReloadText(true)
                .setContentOption(new StatusViewTextOption.Builder()
                        .setText("网络异常")
                        .build())
                .build();
    }

    private static StatusViewOption getErrorStatusViewOption() {
        return new StatusViewOption.Builder()
                .setShowReloadIcon(true)
                .setShowReloadText(true)
                .setContentOption(new StatusViewTextOption.Builder()
                        .setText("发生异常")
                        .build())
                .build();
    }

    private static StatusViewOption getCustomStatusViewOption() {
        return new StatusViewOption.Builder()
                .setContentOption(new StatusViewTextOption.Builder()
                        .setText("没有内容哦")
                        .build())
                .build();
    }
}
