package com.mingyuechunqiu.agileproject.feature.function;

import com.mingyuechunqiu.agile.base.bridge.Request;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/4/7 6:30 PM
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class FunctionParamsInfo implements Request.IParamsInfo {

    public String getTestText() {
        return "test";
    }
}
