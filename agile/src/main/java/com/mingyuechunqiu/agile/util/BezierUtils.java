package com.mingyuechunqiu.agile.util;

import android.graphics.PointF;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/1/16
 *     desc   : 贝塞尔曲线工具类
 *     version: 1.0
 * </pre>
 */
public final class BezierUtils {

    /**
     * 计算贝塞尔曲线点
     *
     * @param type              点坐标轴类型
     * @param ratio             进度比例
     * @param k                 阶数
     * @param index             控制点索引位置
     * @param controlPointFList 控制点集合
     * @return 返回对应坐标轴类型的贝塞尔曲线点
     */
    public static float computeBezierPoint(@NonNull PointType type, @FloatRange(from = 0.0, to = 1.0) float ratio,
                                           int k, int index, @NonNull List<PointF> controlPointFList) {
        //分为1阶和多阶的情况
        if (k == 1) {
            float p1, p2;
            if (type == PointType.X_TYPE) {
                p1 = controlPointFList.get(index).x;
                p2 = controlPointFList.get(index + 1).x;
            } else {
                p1 = controlPointFList.get(index).y;
                p2 = controlPointFList.get(index + 1).y;
            }
            return (1 - ratio) * p1 + ratio * p2;
        } else {
            return (1 - ratio) * computeBezierPoint(type, ratio, k - 1, index, controlPointFList)
                    + ratio * computeBezierPoint(type, ratio, k - 1, index + 1, controlPointFList);
        }
    }

    /**
     * 获取贝塞尔曲线点集合
     *
     * @param controlPointFList 控制点集合
     * @param frame             帧数
     * @return 返回贝塞尔曲线点集合
     */
    @NonNull
    public static List<PointF> getBezierPointFList(@NonNull List<PointF> controlPointFList, int frame) {
        List<PointF> bezierPointFList = new ArrayList<>();
        if (controlPointFList.size() == 0) {
            return bezierPointFList;
        }
        //错误输入时，默认60帧
        if (frame <= 0) {
            frame = 60;
        }
        float delta = 1f / frame;
        int k = controlPointFList.size() - 1;
        for (float ratio = 0; ratio <= 1f; ratio += delta) {
            bezierPointFList.add(new PointF(computeBezierPoint(PointType.X_TYPE, ratio, k, 0, controlPointFList),
                    computeBezierPoint(PointType.Y_TYPE, ratio, k, 0, controlPointFList)));
        }
        return bezierPointFList;
    }

    //控制点坐标轴类型
    public enum PointType {
        X_TYPE, Y_TYPE
    }
}
