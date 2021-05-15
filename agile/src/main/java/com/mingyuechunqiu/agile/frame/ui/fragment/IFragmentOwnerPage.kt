package com.mingyuechunqiu.agile.frame.ui.fragment

import androidx.fragment.app.Fragment
import com.mingyuechunqiu.agile.frame.ui.activity.IActivityOwnerPage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/11 11:22 下午
 *      Desc:       Fragment拥有者接口
 *                  继承自IActivityOwner
 *      Version:    1.0
 * </pre>
 */
interface IFragmentOwnerPage : IActivityOwnerPage {

    fun getOwnedParentFragment(): Fragment?

    fun getOwnedTargetFragment(): Fragment?
}