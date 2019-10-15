package com.mingyuechunqiu.agile.framework.ui;

import android.view.KeyEvent;

/**
 * <pre>
 *      Project:    Agile
 *
 *      author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2019-10-15 22:55
 *      Desc:       按键监听器
 *      Version:    1.0
 * </pre>
 */
public interface OnKeyEventListener {

    /**
     * 当触发按键事件时回调
     *
     * @param keyCode 键值
     * @param event   按键事件
     * @return 如果自己处理完成，不需要Activity继续处理返回true，否则返回false
     */
    boolean onKeyEvent(int keyCode, KeyEvent event);
}
