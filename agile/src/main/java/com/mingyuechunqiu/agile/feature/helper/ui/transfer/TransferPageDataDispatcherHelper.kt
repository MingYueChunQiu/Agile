package com.mingyuechunqiu.agile.feature.helper.ui.transfer

import android.os.Bundle
import com.mingyuechunqiu.agile.constants.AgileCommonConstants

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2021/5/10 11:05 下午
 *      Desc:       传递界面数据辅助类
 *                  实现ITransferPageDataHelper
 *      Version:    1.0
 * </pre>
 */
open class TransferPageDataDispatcherHelper(private val page: ITransferDataDispatcherPage) : ITransferPageDataDispatcherHelper {

    override fun transferDataToPage(
        targetPage: ITransferPageDataDispatcherHelper.TransferPageDataCallback,
        data: ITransferPageDataDispatcherHelper.TransferPageData?
    ): Boolean {
        targetPage.onReceiveTransferPageData(
            ITransferPageDataDispatcherHelper.TransferPageDataOwner(page.getPageTag()),
            data
        )
        return true
    }

    override fun transferDataToActivity(data: ITransferPageDataDispatcherHelper.TransferPageData?): Boolean {
        return (page.getOwnedActivity() as? ITransferPageDataDispatcherHelper.TransferPageDataCallback)?.let {
            transferDataToPage(it, data)
        } ?: false
    }

    override fun returnToPreviousPageWithActivity(interceptor: ITransferPageDataDispatcherHelper.TransferPageDataInterceptor?): Boolean {
        return transferDataToActivity(createReturnPreviousPageData(interceptor))
    }

    override fun transferDataToParentFragment(data: ITransferPageDataDispatcherHelper.TransferPageData?): Boolean {
        return (page.getOwnedParentFragment() as? ITransferPageDataDispatcherHelper.TransferPageDataCallback)?.let {
            transferDataToPage(it, data)
        } ?: false
    }

    override fun transferDataToTargetFragment(data: ITransferPageDataDispatcherHelper.TransferPageData?): Boolean {
        return (page.getOwnedTargetFragment() as? ITransferPageDataDispatcherHelper.TransferPageDataCallback)?.let {
            transferDataToPage(it, data)
        } ?: false
    }

    override fun returnToPreviousPageWithParentFragment(interceptor: ITransferPageDataDispatcherHelper.TransferPageDataInterceptor?): Boolean {
        return transferDataToParentFragment(createReturnPreviousPageData(interceptor))
    }

    override fun returnToPreviousPageWithTargetFragment(interceptor: ITransferPageDataDispatcherHelper.TransferPageDataInterceptor?): Boolean {
        return transferDataToTargetFragment(createReturnPreviousPageData(interceptor))
    }

    /**
     * 创建返回上一界面的传递数据
     *
     * @param interceptor 数据拦截器
     * @return 返回生成的传递数据
     */
    private fun createReturnPreviousPageData(interceptor: ITransferPageDataDispatcherHelper.TransferPageDataInterceptor?): ITransferPageDataDispatcherHelper.TransferPageData {
        return ITransferPageDataDispatcherHelper.TransferPageData(Bundle().apply {
            putBoolean(AgileCommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE, true)
        }.run {
            interceptor?.interceptTransferData(this) ?: this
        })
    }
}