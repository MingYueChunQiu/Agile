package com.mingyuechunqiu.agilemvpframe.framework.operation;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/27
 *     desc   : 操作接口集合
 *     version: 1.0
 * </pre>
 */
public interface Opertaion {

    /**
     * 无返回值操作
     */
    interface VoidOpertaion {

        void operation();
    }

    /**
     * 泛型操作
     */
    interface GenericOpertaion {

        <T> void operation(T t);
    }
}
