package com.mingyuechunqiu.agile.feature.loading.function;

import android.support.v4.app.DialogFragment;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2019/1/24
 *     desc   : 加载Fragment主接口（用户实现此接口）
 *              继承LoadingDfgFunctionable, LoadingDfgControlable
 *     version: 1.0
 * </pre>
 */
public interface LoadingDialogFragmentable extends LoadingDfgFunctionable, LoadingDfgControlable {

    DialogFragment getDialogFragment();
}
