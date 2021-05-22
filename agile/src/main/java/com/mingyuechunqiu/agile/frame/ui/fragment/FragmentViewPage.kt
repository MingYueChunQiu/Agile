package com.mingyuechunqiu.agile.frame.ui.fragment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.mingyuechunqiu.agile.frame.ui.IAgilePage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/16 11:53 下午
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
data class FragmentViewPage(val owner: LifecycleOwner, val tag: String) : IAgilePage {

    override fun getPageTag(): String {
        return tag
    }

    override fun getLifecycle(): Lifecycle {
        return owner.lifecycle
    }
}