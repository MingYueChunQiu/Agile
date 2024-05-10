package com.mingyuechunqiu.agile.framework.operation;

/**
 * <pre>
 *     author : xyj
 *     Github : <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/27
 *     desc   : 操作接口集合
 *     version: 1.0
 * </pre>
 */
public interface Operation {

    /**
     * 无返回值操作
     */
    interface VoidOperation {

        void operation();
    }

    /**
     * 泛型操作
     */
    interface GenericOperation {

        <T> void operation(T t);
    }
}
