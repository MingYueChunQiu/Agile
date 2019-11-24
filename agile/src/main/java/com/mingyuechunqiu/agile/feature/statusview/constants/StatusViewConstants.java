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
public class StatusViewConstants {

    //模式类型
    public enum ModeType {
        //未设置，对话框模式，Fragment模式
        TYPE_NOT_SET, TYPE_DIALOG, TYPE_FRAGMENT
    }

    /**
     * 状态类型
     */
    public enum StatusType {
        //加载，空，网络异常，错误，自定义
        TYPE_LOADING, TYPE_EMPTY, TYPE_NETWORK_ANOMALY, TYPE_ERROR, TYPE_CUSTOM
    }
}
