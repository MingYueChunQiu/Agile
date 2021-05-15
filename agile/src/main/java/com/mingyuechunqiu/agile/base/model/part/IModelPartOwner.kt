package com.mingyuechunqiu.agile.base.model.part

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       4/18/21 3:44 PM
 *      Desc:       Model层Part层拥有者接口
 *      Version:    1.0
 * </pre>
 */
interface IModelPartOwner {

    /**
     * 添加模型层part单元
     *
     * @param part part单元模块
     * @return 如果添加成功返回true，否则返回false
     */
    fun addModelPart(part: IBaseModelPart): Boolean

    /**
     * 删除指定的模型层part单元
     *
     * @param part part单元模块
     * @return 如果删除成功返回true，否则返回false
     */
    fun removeModelPart(part: IBaseModelPart?): Boolean

    fun getModelPartList(): List<IBaseModelPart>
}