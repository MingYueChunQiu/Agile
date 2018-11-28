package com.mingyuechunqiu.agilemvpframe.constants;

import static com.mingyuechunqiu.agilemvpframe.constants.KeyPrefixConstants.KEY_BUNDLE;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/17
 *     desc   : 应用全局常量类
 *     version: 1.0
 * </pre>
 */
public class CommonConstants {

    //导航栏标题
    public static final String BUNDLE_NAVIGATION_TITLE = KEY_BUNDLE + "navigation_title";
    public static final String BUNDLE_COURSEWARE_URL = KEY_BUNDLE + "courseware_url";//课件网址
    public static final String BUNDLE_UPDATE_APP_DESCRIPTION = KEY_BUNDLE + "update_app_description";//更新应用描述
    public static final String BUNDLE_UPDATE_APP_URL = KEY_BUNDLE + "update_app_url";//更新应用URL地址
    //返回上一个fragment
    public static final String BUNDLE_RETURN_TO_PREVIOUS_FRAGMENT = KeyPrefixConstants.KEY_BUNDLE +
            "return_to_previous_fragment";

    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 10;//写入外部存储卡权限请求码
}
