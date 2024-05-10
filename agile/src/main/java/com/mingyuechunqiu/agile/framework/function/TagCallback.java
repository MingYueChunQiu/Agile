package com.mingyuechunqiu.agile.framework.function;

/**
 * <pre>
 *     author : xyj
 *     Github : <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/7/22
 *     desc   : 标签回调接口
 *     version: 1.0
 * </pre>
 */
public interface TagCallback {

    /**
     * 获取所属Tag
     *
     * @return 返回Tag字符串
     */
    String getOwnerTag();

    /**
     * 设置所属Tag
     *
     * @param tag tag字符串
     */
    void setOwnerTag(String tag);
}
