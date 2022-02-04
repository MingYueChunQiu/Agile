package com.mingyuechunqiu.agile.feature.helper.ui.insets

import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.core.view.WindowInsetsControllerCompat

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/20 11:21 下午
 *      Desc:       窗口边衬辅助类
 *      Version:    1.0
 * </pre>
 */
class WindowInsetsHelper(window: Window) {

    private val mWindowInsetsController by lazy { ViewCompat.getWindowInsetsController(window.decorView) }
    private val mWindowInsets by lazy { WindowInsetsCompat.Builder().build() }

    /**
     * 设置状态栏为轻色调，避免白色字体被白色活动条遮挡
     */
    fun setLightStatusBars() {
        mWindowInsetsController?.isAppearanceLightStatusBars = true
    }

    /**
     * 设置状态栏为深色调，显示为白色字体
     */
    fun setDarkStatusBars() {
        mWindowInsetsController?.isAppearanceLightStatusBars = false
    }

    fun isLightStatusBars(): Boolean {
        return mWindowInsetsController?.isAppearanceLightStatusBars ?: false
    }

    fun setLightNavigationBars() {
        mWindowInsetsController?.isAppearanceLightNavigationBars = true
    }

    fun setDarkNavigationBars() {
        mWindowInsetsController?.isAppearanceLightNavigationBars = false
    }

    fun isLightNavigationBars(): Boolean {
        return mWindowInsetsController?.isAppearanceLightNavigationBars ?: false
    }

    fun showStatusBar(isShow: Boolean) {
        if (isShow) {
            mWindowInsetsController?.show(WindowInsetsCompat.Type.statusBars())
        } else {
            mWindowInsetsController?.hide(WindowInsetsCompat.Type.statusBars())
        }
    }

    fun showNavigationBar(isShow: Boolean) {
        if (isShow) {
            mWindowInsetsController?.show(WindowInsetsCompat.Type.navigationBars())
        } else {
            mWindowInsetsController?.hide(WindowInsetsCompat.Type.navigationBars())
        }
    }

    fun showSystemBar(isShow: Boolean) {
        if (isShow) {
            mWindowInsetsController?.show(WindowInsetsCompat.Type.systemBars())
        } else {
            mWindowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())
        }
    }

    fun showInputMethod(isShow: Boolean) {
        if (isShow) {
            mWindowInsetsController?.show(WindowInsetsCompat.Type.ime())
        } else {
            mWindowInsetsController?.hide(WindowInsetsCompat.Type.ime())
        }
    }

    fun isStatusBarsVisible(): Boolean {
        return mWindowInsets.isVisible(WindowInsetsCompat.Type.statusBars())
    }

    fun isNativeStatusBarsVisible(): Boolean {
        return mWindowInsets.isVisible(WindowInsetsCompat.Type.navigationBars())
    }

    fun isInputMethodVisible(): Boolean {
        return mWindowInsets.isVisible(WindowInsetsCompat.Type.ime())
    }

    fun isInsetsVisible(@InsetsType type: Int): Boolean {
        return mWindowInsets.isVisible(type)
    }

    fun setSystemBarsBehaviour(behavior: Int) {
        mWindowInsetsController?.systemBarsBehavior = behavior
    }

    fun addOnControllableInsetsChangedListener(listener: WindowInsetsControllerCompat.OnControllableInsetsChangedListener) {
        mWindowInsetsController?.addOnControllableInsetsChangedListener(listener)
    }

    fun removeOnControllableInsetsChangedListener(listener: WindowInsetsControllerCompat.OnControllableInsetsChangedListener) {
        mWindowInsetsController?.removeOnControllableInsetsChangedListener(listener)
    }
}