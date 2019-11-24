package com.mingyuechunqiu.agile.feature.statusview.ui;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-11-24 19:54
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
interface IStatusViewDelegate {

    void showLoadingStatus();

    void showNetworkAnomalyStatus();

    void showErrorStatus();

    void showCustomStatus();
}
