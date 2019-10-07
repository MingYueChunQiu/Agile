package com.mingyuechunqiu.agile.feature.json;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/2/21
 *     desc   : Json处理帮助类管理器接口
 *              继承自JsonHelperable
 *     version: 1.0
 * </pre>
 */
interface JsonManagerable extends JsonHelperable {

    void setJsonHelper(@NonNull JsonHelperable helper);
}
