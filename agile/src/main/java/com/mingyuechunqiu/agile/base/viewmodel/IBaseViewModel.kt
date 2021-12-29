package com.mingyuechunqiu.agile.base.viewmodel

import android.content.Context
import com.mingyuechunqiu.agile.base.bridge.call.ICallExecutor
import com.mingyuechunqiu.agile.feature.helper.ui.hint.IPopHintOwner

interface IBaseViewModel: ICallExecutor, IPopHintOwner {

    fun releaseOnDetach()

    fun getAppContext():Context
}