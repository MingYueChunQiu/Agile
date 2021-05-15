package com.mingyuechunqiu.agile.base.model.dao

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/18/21 3:39 PM
 *      Desc:       Dao层拥有者接口
 *      Version:    1.0
 * </pre>
 */
interface IDaoOwner {

    /**
     * 添加Dao层单元
     *
     * @param dao dao单元
     * @return 如果添加成功返回true，否则返回false
     */
    fun addDao(dao: IBaseDao<*>): Boolean

    /**
     * 添加Dao层单元
     *
     * @param dao dao单元
     * @return 如果添加成功返回true，否则返回false
     */
    fun addDao(dao: IBaseDao<*>, requestTags: List<String>): Boolean

    /**
     * 删除dao单元
     *
     * @param dao dao单元
     * @return 如果删除成功返回true，否则返回false
     */
    fun removeDao(dao: IBaseDao<*>?): Boolean

    fun getDaoList(): List<IBaseDao<*>>
}