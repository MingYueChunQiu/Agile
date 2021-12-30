package com.mingyuechunqiu.agile.base.model.repository

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/18/21 3:39 PM
 *      Desc:       Repository层拥有者接口
 *      Version:    1.0
 * </pre>
 */
interface IRepositoryOwner {

    /**
     * 添加Repository层单元
     *
     * @param repository Repository单元
     * @return 如果添加成功返回true，否则返回false
     */
    fun addRepository(repository: IBaseRepository): Boolean

    /**
     * 添加Repository层单元
     *
     * @param repository Repository单元
     * @return 如果添加成功返回true，否则返回false
     */
    fun addRepository(repository: IBaseRepository, requestTags: List<String>): Boolean

    /**
     * 删除Repository单元
     *
     * @param repository Repository单元
     * @return 如果删除成功返回true，否则返回false
     */
    fun removeRepository(repository: IBaseRepository?): Boolean

    fun getRepositoryList(): List<IBaseRepository>
}