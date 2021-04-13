package com.mingyuechunqiu.agile.feature.helper.ui.transfer

import android.os.Bundle
import com.mingyuechunqiu.agile.constants.CommonConstants
import com.mingyuechunqiu.agile.frame.ui.IAgileFragmentPage

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/7/21 11:06 PM
 *      Desc:       Fragment传递界面数据辅助类
 *                  实现ITransferPageDataHelper
 *      Version:    1.0
 * </pre>
 */
class TransferPageDataHelper(private val page: IAgileFragmentPage) : ITransferPageDataHelper {

    override fun transferDataToActivity(data: ITransferPageDataHelper.TransferPageData?): Boolean {
        return (page.getOwnedActivity() as? ITransferPageDataHelper.TransferPageDataCallback)?.let {
            transferDataToPage(it, data)
        } ?: false
    }

    override fun transferDataToParentFragment(data: ITransferPageDataHelper.TransferPageData?): Boolean {
        return (page.getOwnedParentFragment() as? ITransferPageDataHelper.TransferPageDataCallback)?.let {
            transferDataToPage(it, data)
        } ?: false
    }

    override fun transferDataToTargetFragment(data: ITransferPageDataHelper.TransferPageData?): Boolean {
        return (page.getOwnedTargetFragment() as? ITransferPageDataHelper.TransferPageDataCallback)?.let {
            transferDataToPage(it, data)
        } ?: false
    }

    override fun transferDataToPage(targetPage: ITransferPageDataHelper.TransferPageDataCallback, data: ITransferPageDataHelper.TransferPageData?): Boolean {
        targetPage.onReceiveTransferPageData(ITransferPageDataHelper.TransferPageDataOwner(page.getPageTag()), data)
        return true
    }

    override fun returnToPreviousPageWithActivity(interceptor: ITransferPageDataHelper.TransferPageDataInterceptor?): Boolean {
        return transferDataToActivity(createReturnPreviousPageData(interceptor))
    }

    override fun returnToPreviousPageWithParentFragment(interceptor: ITransferPageDataHelper.TransferPageDataInterceptor?): Boolean {
        return transferDataToParentFragment(createReturnPreviousPageData(interceptor))
    }

    override fun returnToPreviousPageWithTargetFragment(interceptor: ITransferPageDataHelper.TransferPageDataInterceptor?): Boolean {
        return transferDataToTargetFragment(createReturnPreviousPageData(interceptor))
    }

    /**
     * 创建返回上一界面的传递数据
     *
     * @param interceptor 数据拦截器
     * @return 返回生成的传递数据
     */
    private fun createReturnPreviousPageData(interceptor: ITransferPageDataHelper.TransferPageDataInterceptor?): ITransferPageDataHelper.TransferPageData {
        return ITransferPageDataHelper.TransferPageData(Bundle().apply {
            putBoolean(CommonConstants.BUNDLE_RETURN_TO_PREVIOUS_PAGE, true)
        }.run {
            interceptor?.interceptTransferData(this) ?: this
        })
    }
}