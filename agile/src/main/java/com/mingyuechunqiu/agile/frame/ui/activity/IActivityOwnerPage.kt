package com.mingyuechunqiu.agile.frame.ui.activity

import androidx.fragment.app.FragmentActivity
import com.mingyuechunqiu.agile.frame.ui.IAgilePage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/11 11:20 下午
 *      Desc:       Activity拥有者接口
 *      Version:    1.0
 * </pre>
 */
interface IActivityOwnerPage: IAgilePage {

    fun getOwnedActivity(): FragmentActivity?
}