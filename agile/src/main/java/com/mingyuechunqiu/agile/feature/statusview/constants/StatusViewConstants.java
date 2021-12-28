package com.mingyuechunqiu.agile.feature.statusview.constants;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/24
 *     desc   : 常量类
 *     version: 1.0
 * </pre>
 */
public final class StatusViewConstants {

    private StatusViewConstants() {
    }

    //模式类型
    public enum StatusViewMode {
        //无效模式，控件模式，对话框模式
        MODE_INVALID, MODE_WIDGET, MODE_DIALOG
    }

    /**
     * 状态类型
     */
    public enum StatusViewType {
        //未知，加载，空，网络异常，错误，自定义
        TYPE_UNKNOWN, TYPE_LOADING, TYPE_EMPTY, TYPE_NETWORK_ANOMALY, TYPE_ERROR, TYPE_CUSTOM
    }

    /**
     * 进度条类型
     */
    public enum ProgressStyle {
        //系统样式进度条，菊花样式进度条
        STYLE_SYSTEM, STYLE_DAISY
    }
}
