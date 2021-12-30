package com.mingyuechunqiu.agile.constants;

import static com.mingyuechunqiu.agile.constants.AgileKeyPrefixConstants.KEY_BUNDLE;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/17
 *     desc   : 应用全局常量类
 *     version: 1.0
 * </pre>
 */
public class AgileCommonConstants {

    public static final int NO_RESOURCE_ID = 0;//没有资源id

    //导航栏标题
    public static final String BUNDLE_NAVIGATION_TITLE = KEY_BUNDLE + "navigation_title";
    //返回上一个界面
    public static final String BUNDLE_RETURN_TO_PREVIOUS_PAGE = AgileKeyPrefixConstants.KEY_BUNDLE +
            "return_to_previous_page";

    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 10;//写入外部存储卡权限请求码
}
