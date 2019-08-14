package com.mingyuechunqiu.agile.framework.function;

import android.os.Bundle;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/8/14
 *     desc   : 传递数据回调
 *     version: 1.0
 * </pre>
 */
public interface TransferDataCallback {

    /**
     * 传递Bundle数据
     *
     * @return 返回数据Bundle对象
     */
    Bundle transferData();
}
