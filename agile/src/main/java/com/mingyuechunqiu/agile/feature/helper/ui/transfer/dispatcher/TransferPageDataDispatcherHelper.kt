package com.mingyuechunqiu.agile.feature.helper.ui.transfer.dispatcher

import android.os.Bundle
import com.mingyuechunqiu.agile.constants.AgileCommonConstants
import com.mingyuechunqiu.agile.feature.helper.ui.transfer.receiver.ITransferPageDataReceiverPage

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
open class TransferPageDataDispatcherHelper(private val page: ITransferPageDataDispatcherPage) :
    ITransferPageDataDispatcherHelper {

    override fun transferDataToPage(
        targetPage: ITransferPageDataReceiverPage,
        data: ITransferPageDataDispatcher.TransferPageData?
    ): Boolean {
        targetPage.getTransferPageDataReceiverHelper().onReceiveTransferPageData(
            ITransferPageDataDispatcher.TransferPageDataOwner(page.getPageTag()),
            data
        )
        return true
    }

    override fun transferDataToActivity(data: ITransferPageDataDispatcher.TransferPageData?): Boolean {
        return (page.getOwnedActivity() as? ITransferPageDataReceiverPage)?.let {
            transferDataToPage(it, data)
        } ?: false
    }

    override fun returnToPreviousPageWithActivity(interceptor: ITransferPageDataDispatcher.TransferPageDataInterceptor?): Boolean {
        return transferDataToActivity(createReturnPreviousPageData(interceptor))
    }

    override fun transferDataToParentFragment(data: ITransferPageDataDispatcher.TransferPageData?): Boolean {
        return (page.getOwnedParentFragment() as? ITransferPageDataReceiverPage)?.let {
            transferDataToPage(it, data)
        } ?: false
    }

    override fun transferDataToTargetFragment(data: ITransferPageDataDispatcher.TransferPageData?): Boolean {
        return (page.getOwnedTargetFragment() as? ITransferPageDataReceiverPage)?.let {
            transferDataToPage(it, data)
        } ?: false
    }

    override fun returnToPreviousPageWithParentFragment(interceptor: ITransferPageDataDispatcher.TransferPageDataInterceptor?): Boolean {
        return transferDataToParentFragment(createReturnPreviousPageData(interceptor))
    }

    override fun returnToPreviousPageWithTargetFragment(interceptor: ITransferPageDataDispatcher.TransferPageDataInterceptor?): Boolean {
        return transferDataToTargetFragment(createReturnPreviousPageData(interceptor))
    }

    /**
     * 创建返回上一界面的传递数据
     *
     * @param interceptor 数据拦截器
     * @return 返回生成的传递数据
     */
    private fun createReturnPreviousPageData(interceptor: ITransferPageDataDispatcher.TransferPageDataInterceptor?): ITransferPageDataDispatcher.TransferPageData {
        return ITransferPageDataDispatcher.TransferPageData(Bundle().apply {
            putBoolean(AgileCommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE, true)
        }.run {
            interceptor?.interceptTransferData(this) ?: this
        })
    }
}