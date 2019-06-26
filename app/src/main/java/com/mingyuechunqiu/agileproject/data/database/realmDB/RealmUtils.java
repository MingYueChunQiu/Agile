package com.mingyuechunqiu.agileproject.data.database.realmDB;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;
import com.mingyuechunqiu.agile.util.SharedPreferencesUtils;
import com.mingyuechunqiu.agileproject.MyApplication;
import com.mingyuechunqiu.agileproject.data.database.realmDB.bean.User;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;

import static com.mingyuechunqiu.agile.constants.KeyPrefixConstants.KEY_PREF;
import static com.mingyuechunqiu.agile.constants.UserConstants.PREF_USER_INFO;
import static com.mingyuechunqiu.agile.constants.UserConstants.TOKEN;
import static com.mingyuechunqiu.agileproject.constants.UserConstants.LATEST_USERNAME;
import static com.mingyuechunqiu.agileproject.constants.UserConstants.MEMBER_USERNAME;
import static com.mingyuechunqiu.agileproject.constants.UserConstants.PARENT_USERNAME;
import static com.mingyuechunqiu.agileproject.constants.UserConstants.PASSWORD;
import static com.mingyuechunqiu.agileproject.constants.UserConstants.TEACHER_USERNAME;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/07/17
 *     desc   : Realm数据库工具类
 *     version: 1.0
 * </pre>
 */
public class RealmUtils {

    //安全数据
    private static final String SECURE_DATA = KEY_PREF + "secure_data";

    //realm数据库加密key
    private static final String PREF_REALM_ENCRYPTION_KEY = KEY_PREF + "realm_encryption_key";

    public static RealmConfiguration sConfiguration;

    public static RealmConfiguration getRealmConfiguration() {
        if (sConfiguration == null) {
            byte[] keys = getEncryptionKey();
            //如果本地存储过，则直接使用byte[]数组，否则新建加密键
            if (keys == null) {
                sConfiguration = encryptRealmConfiguration();
            } else {
                sConfiguration = new RealmConfiguration.Builder()
                        .encryptionKey(keys)
                        .build();
            }
        }
        return sConfiguration;
    }

    public static void setRealmConfiguration(RealmConfiguration configuration) {
        sConfiguration = configuration;
    }

    /**
     * 获取加密的Realm配置
     *
     * @return 设置好加密键的RealmConfiguration
     */
    @Nullable
    public static RealmConfiguration encryptRealmConfiguration() {
        byte[] bytes = new byte[64];
        new SecureRandom().nextBytes(bytes);
        String key = Base64.encodeToString(bytes, Base64.DEFAULT);
        Map<String, String> map = new HashMap<>(1);
        map.put(PREF_REALM_ENCRYPTION_KEY, key);
        //用安全数据实例来存储
        if (SharedPreferencesUtils.putString(MyApplication.getAppContext(),
                SECURE_DATA, PREF_REALM_ENCRYPTION_KEY, key)) {
            return new RealmConfiguration.Builder()
                    .encryptionKey(bytes)
                    .build();
        }
        return null;
    }

    /**
     * 获取Realm的加密byte数组
     *
     * @return 返回用于加密的byte数组
     */
    @Nullable
    public static byte[] getEncryptionKey() {
        String encryptionKey = SharedPreferencesUtils.getString(MyApplication.getAppContext(),
                SECURE_DATA, PREF_REALM_ENCRYPTION_KEY, null);
        if (TextUtils.isEmpty(encryptionKey)) {
            return null;
        }
        return Base64.decode(encryptionKey, Base64.DEFAULT);
    }

    /**
     * 保存用户登录信息
     *
     * @param username 用户名
     * @param password 用户密码
     * @param token    登录token
     * @param roleType 角色类型
     * @return 返回Realm异步数据库操作对象
     */
    public static RealmAsyncTask saveUserInfo(String username, String password, String token, int roleType) {
        SharedPreferencesUtils.putString(MyApplication.getAppContext(),
                PREF_USER_INFO, LATEST_USERNAME, username);
        String key;
        switch (roleType) {
            case 0:
                key = MEMBER_USERNAME;
                break;
            case 1:
                key = PARENT_USERNAME;
                break;
            case 2:
                key = TEACHER_USERNAME;
                break;
            default:
                key = MEMBER_USERNAME;
                break;
        }
        SharedPreferencesUtils.putString(MyApplication.getAppContext(), PREF_USER_INFO,
                key, username);
        SharedPreferencesUtils.putString(MyApplication.getAppContext(), PREF_USER_INFO,
                PASSWORD, password);
        SharedPreferencesUtils.putString(MyApplication.getAppContext(), PREF_USER_INFO,
                TOKEN, token);
        return saveUserInfoToDB(username, password, roleType);
    }

    /**
     * 保存用户登录信息
     *
     * @param username 用户名
     * @param password 用户密码
     * @param roleType 角色类型
     * @return 返回Realm异步数据库操作对象
     */
    public static RealmAsyncTask saveUserInfoToDB(final String username, final String password, final int roleType) {
        final Realm realm = Realm.getInstance(RealmUtils.getRealmConfiguration());
        return realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = new User();
                user.setName(username);
                user.setPassword(password);
                user.setLoginTime(System.currentTimeMillis());
                user.setRoleType(roleType);
                realm.copyToRealmOrUpdate(user);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                LogManagerProvider.e("saveUserInfoToDB", "登录信息保存异常 "
                        + error.toString());
                realm.close();
            }
        });
    }
}
