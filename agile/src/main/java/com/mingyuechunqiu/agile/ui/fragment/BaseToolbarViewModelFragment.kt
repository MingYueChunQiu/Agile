package com.mingyuechunqiu.agile.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mingyuechunqiu.agile.constants.AgileCommonConstants
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper.ToolbarConfigure

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/10/19
 *     desc   : 所有MVVM层工具条界面的基类
 *              继承自BaseDataViewModelFragment
 *     version: 1.0
 * </pre>
 */
abstract class BaseToolbarViewModelFragment : BaseDataViewModelFragment() {

    private var tbBar: Toolbar? = null
    private var mToolbarConfigure: ToolbarConfigure? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnInflateToolbar(view)
        var actionBar: ActionBar? = null
        val activity = activity
        if (activity is AppCompatActivity) {
            setHasOptionsMenu(true)
            activity.setSupportActionBar(tbBar)
            actionBar = activity.supportActionBar
        }
        mToolbarConfigure = initToolbarConfigure()
        ToolbarHelper.initToolbar(tbBar, actionBar, mToolbarConfigure)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (mToolbarConfigure == null) {
            return
        }
        if (mToolbarConfigure!!.isClearActivityMenu) {
            menu.clear()
        }
        if (mToolbarConfigure!!.menuResId != AgileCommonConstants.NO_RESOURCE_ID) {
            inflater.inflate(mToolbarConfigure!!.menuResId, menu)
            ToolbarHelper.applyMenuColorFilter(menu, mToolbarConfigure!!.menuColorFilterColor)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mToolbarConfigure = null
    }

    /**
     * 初始化Toolbar控件
     *
     * @param view 父容器
     */
    private fun initOnInflateToolbar(view: View) {
        val resId = getInflateToolbarResId()
        if (resId != 0) {
            tbBar = view.findViewById(resId)
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