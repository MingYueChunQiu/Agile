package com.mingyuechunqiu.agile.framework.ui

import android.view.View
import androidx.annotation.LayoutRes

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/12/21 11:36 PM
 *      Desc:       布局填充视图创建者接口
 *      Version:    1.0
 * </pre>
 */
interface IActivityInflateLayoutViewCreator {

    /**
     * 获取填充布局资源ID
     *
     * @return 返回布局资源ID
     */
    @LayoutRes
    fun getInflateLayoutId(): Int

    /**
     * 获取填充布局View（当getInflateLayoutId返回为0时，会被调用），可为null
     *
     * @return 返回View容器
     */
    fun getInflateLayoutView(): View?

    /**
     * 布局填充视图创建者适配器
     * 实现IInflateLayoutViewCreator
     */
    open class ActivityInflateLayoutViewCreatorAdapter : IActivityInflateLayoutViewCreator {

        override fun getInflateLayoutId(): Int {
            return 0
        }

        override fun getInflateLayoutView(): View? {
            return null
        }
    }
}