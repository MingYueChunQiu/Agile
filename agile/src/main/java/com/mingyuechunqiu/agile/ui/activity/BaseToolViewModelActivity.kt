package com.mingyuechunqiu.agile.ui.activity

import android.os.Bundle
import android.view.Menu
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import com.mingyuechunqiu.agile.constants.AgileCommonConstants
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper.ToolbarConfigure

abstract class BaseToolViewModelActivity : BaseDataViewModelActivity() {

    private var tbBar: Toolbar? = null
    private var mToolbarConfigure: ToolbarConfigure? = null

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initOnInflateToolbar()
        setSupportActionBar(tbBar)
        mToolbarConfigure = initToolbarConfigure()
        ToolbarHelper.initToolbar(tbBar, supportActionBar, mToolbarConfigure)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (mToolbarConfigure != null && mToolbarConfigure!!.menuResId != AgileCommonConstants.NO_RESOURCE_ID) {
            menuInflater.inflate(mToolbarConfigure!!.menuResId, menu)
            ToolbarHelper.applyMenuColorFilter(menu, mToolbarConfigure!!.menuColorFilterColor)
        }
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 初始化Toolbar控件
     */
    private fun initOnInflateToolbar() {
        val resId = getInflateToolbarResId()
        if (resId != 0) {
            tbBar = findViewById(resId)
        }
        if (tbBar == null) {
            tbBar = getInflateToolbar()
        }
        initInflateToolbar(tbBar)
    }

    /**
     * 在初始化Toolbar时回调
     *
     * @param toolbar 初始化后的Toolbar控件
     */
    protected open fun initInflateToolbar(toolbar: Toolbar?) {}

    /**
     * 获取拥有的Toolbar
     *
     * @return 返回Toolbar控件
     */
    protected open fun getOwnedToolbar(): Toolbar? {
        return tbBar
    }

    /**
     * 获取填充的Toolbar（在getInflateToolbarResId返回为0时，会被调用）
     *
     * @return 返回Toolbar控件
     */
    protected open fun getInflateToolbar(): Toolbar? {
        return null
    }

    /**
     * 获取填充的Toolbar控件的资源ID
     *
     * @return 返回资源ID
     */
    @IdRes
    protected abstract fun getInflateToolbarResId(): Int

    /**
     * 供子类覆写的创建ToolbarBean方法，并放回创建好的ToolbarBean
     *
     * @return 返回创建好的ToolbarBean
     */
    protected abstract fun initToolbarConfigure(): ToolbarConfigure?
}