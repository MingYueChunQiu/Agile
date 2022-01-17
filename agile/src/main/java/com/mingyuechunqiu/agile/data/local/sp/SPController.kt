package com.mingyuechunqiu.agile.data.local.sp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.mingyuechunqiu.agile.frame.Agile

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2022/1/16 4:19 下午
 *      Desc:       SharedPreferences控制器
 *      Version:    1.0
 * </pre>
 */
class SPController(private val mContext: Context, private val mConfigName: String) {

    constructor(configName: String) : this(Agile.getAppContext(), configName)

    private val mSp = mContext.getSharedPreferences(mConfigName, MODE_PRIVATE)

    fun putBoolean(key: String, value: Boolean) {
        mSp.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return mSp.getBoolean(key, defValue)
    }

    fun putInt(key: String, value: Int) {
        mSp.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return mSp.getInt(key, defValue)
    }

    fun putLong(key: String, value: Long) {
        mSp.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defValue: Long): Long {
        return mSp.getLong(key, defValue)
    }

    fun putFloat(key: String, value: Float) {
        mSp.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String, defValue: Float): Float {
        return mSp.getFloat(key, defValue)
    }

    fun putString(key: String, value: String?) {
        mSp.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String?): String? {
        return mSp.getString(key, defValue)
    }

    fun putStringSet(key: String, value: Set<String>?) {
        mSp.edit().putStringSet(key, value).apply()
    }

    fun getStringSet(key: String, defValue: Set<String>?): Set<String>? {
        return mSp.getStringSet(key, defValue)
    }

    /**
     * 强制刷新缓存中数据
     */
    @SuppressLint("ApplySharedPref")
    fun flush() {
        mSp.edit().commit()
    }

    /**
     * 清空配置中所有内容
     *
     * @return 是否清除成功
     */
    fun clearAll(): Boolean {
        return SharedPreferencesHelper.clearAll(mContext, mConfigName)
    }
}