package com.mingyuechunqiu.agile.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mingyuechunqiu.agile.constants.AgileCommonConstants
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper
import com.mingyuechunqiu.agile.feature.helper.ui.widget.ToolbarHelper.ToolbarConfig

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
    private var mToolbarInflateCreator: ToolbarHelper.IToolbarInflateCreator? = null
    private var mToolbarConfig: ToolbarConfig? = null

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
        mToolbarInflateCreator?.let {
            mToolbarConfig = it.initToolbarConfig()
            ToolbarHelper.initToolbar(tbBar, actionBar, mToolbarConfig)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (mToolbarConfig == null) {
            return
        }
        if (mToolbarConfig!!.isClearActivityMenu) {
            menu.clear()
        }
        if (mToolbarConfig!!.menuResId != AgileCommonConstants.NO_RESOURCE_ID) {
            inflater.inflate(mToolbarConfig!!.menuResId, menu)
            ToolbarHelper.applyMenuColorFilter(menu, mToolbarConfig!!.menuColorFilterColor)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mToolbarConfig = null
    }

    /**
     * 初始化Toolbar控件
     *
     * @param view 父容器
     */
    private fun initOnInflateToolbar(view: View) {
        mToolbarInflateCreator = generateToolbarInflateCreator()
        val creator = mToolbarInflateCreator ?: return
        val resId = creator.inflateToolbarResId
        if (resId != 0) {
            tbBar = view.findViewById(resId)
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