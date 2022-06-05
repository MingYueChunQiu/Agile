package com.mingyuechunqiu.agile.ui.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import com.mingyuechunqiu.agile.constants.AgileCommonConstants
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper.ToolbarConfig

abstract class BaseToolViewModelActivity : BaseDataViewModelActivity() {

    private var tbBar: Toolbar? = null
    private var mToolbarInflateCreator: ToolbarHelper.IToolbarInflateCreator? = null
    private var mToolbarConfig: ToolbarConfig? = null

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initOnInflateToolbar()
        setSupportActionBar(tbBar)
        mToolbarInflateCreator?.let {
            mToolbarConfig = it.initToolbarConfig()
            ToolbarHelper.initToolbar(tbBar, supportActionBar, mToolbarConfig)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (mToolbarConfig != null && mToolbarConfig!!.menuResId != AgileCommonConstants.NO_RESOURCE_ID) {
            menuInflater.inflate(mToolbarConfig!!.menuResId, menu)
            ToolbarHelper.applyMenuColorFilter(menu, mToolbarConfig!!.menuColorFilterColor)
        }
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 初始化Toolbar控件
     */
    private fun initOnInflateToolbar() {
        mToolbarInflateCreator = generateToolbarInflateCreator()
        val creator = mToolbarInflateCreator ?: return
        val resId = creator.inflateToolbarResId
        if (resId != 0) {
            tbBar = findViewById(resId)
        }
        if (tbBar == null) {
            tbBar = creator.inflateToolbar
        }
        mToolbarConfig = creator.initToolbarConfig()
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
     * 获取Toolbar填充创建者
     *
     * @return 返回创建者对象
     */
    protected abstract fun generateToolbarInflateCreator(): ToolbarHelper.IToolbarInflateCreator
}