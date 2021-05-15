package com.mingyuechunqiu.agile.base.presenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.base.model.BaseAbstractDataModel;
import com.mingyuechunqiu.agile.base.view.IBaseDataView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/23
 *     desc   : 所有具有倒计时功能的MVP模型的P层
 *              继承自BaseAbstractDataPresenter
 *     version: 1.0
 * </pre>
 */
public abstract class BaseAbstractCountDownPresenter<V extends IBaseDataView, M extends BaseAbstractDataModel> extends BaseAbstractDataPresenter<V, M> {

    @Nullable
    private Timer mTimer;
    @Nullable
    private TimerTask mTimerTask;
    @Nullable
    private ICountDownListener mListener;

    @Override
    public void detachView() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mListener = null;
        super.detachView();
    }

    /**
     * 进行倒计时处理
     *
     * @param count    倒计时时间
     * @param listener 处理倒计时的回调
     */
    public void setCountDown(final int count, @NonNull final ICountDownListener listener) {
        if (count <= 0) {
            return;
        }
        mListener = listener;
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        mTimerTask = new TimerTask() {
            int score = 0;

            @Override
            public void run() {
                startCountDown(count, score);
                score++;
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /**
     * 取消倒计时显示
     */
    public void cancelCountDown() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mListener != null) {
            mListener.onCountDownComplete();
            mListener = null;
        }
    }

    /**
     * 开始倒计时
     *
     * @param count 倒计时总数
     * @param score 当前计数进度
     */
    private void startCountDown(int count, int score) {
        if (mListener != null) {
            mListener.onCountDown(count - score);
        }
        if (score == count) {
            if (mTimerTask != null) {
                mTimerTask.cancel();
            }
            if (mTimer != null) {
                mTimer.cancel();
            }
            mTimerTask = null;
            mTimer = null;
            mListener.onCountDownComplete();
        }
    }

    public interface ICountDownListener {

        /**
         * 当倒计时时回调
         *
         * @param count 当前的计数
         */
        void onCountDown(int count);

        /**
         * 当倒计时结束时调用
         */
        void onCountDownComplete();
    }
}
