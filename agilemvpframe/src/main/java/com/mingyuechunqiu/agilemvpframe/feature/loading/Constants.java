package com.mingyuechunqiu.agilemvpframe.feature.loading;

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
public class Constants {

    //对话框主题类型（默认提供浅色和深色两种主题）
    public enum ThemeType {
        LIGHT_THEME, DARK_THEME
    }

    //模式类型
    public enum ModeType {
        //未设置，对话框模式，Fragment模式
        TYPE_NOT_SET, TYPE_DIALOG, TYPE_FRAGMENT
    }
}
