package com.mingyuechunqiu.agilemvpframeproject.constants;

import static com.mingyuechunqiu.agilemvpframe.constants.KeyPrefixConstants.KEY_PREF;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/11/5
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class UserConstants {

    public static final String PREF_GLOBAL_INFO = "global_info";//记录全局相关信息
    public static final String MEMBER_USERNAME = KEY_PREF + "member_username";//用户名
    public static final String PARENT_USERNAME = KEY_PREF + "parent_username";//家长用户名
    public static final String TEACHER_USERNAME = KEY_PREF + "teacher_username";//中教用户名
    public static final String PASSWORD = KEY_PREF + "password";//用户密码
    public static final String USER_ID = KEY_PREF + "user_id";//用户ID
    public static final String LATEST_USERNAME = KEY_PREF + "latest_username";//最新的登录用户
    public static final String PREF_AUTO_LOGIN = KEY_PREF + "auto_login";//标记是否下次自动登录

}
