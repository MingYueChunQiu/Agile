package com.mingyuechunqiu.agilemvpframe.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mingyuechunqiu.agilemvpframe.R;
import com.mingyuechunqiu.agilemvpframe.ui.fragment.BaseFragment;

import static com.mingyuechunqiu.agilemvpframe.constants.CommonConstants.BUNDLE_RETURN_TO_PREVIOUS_FRAGMENT;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/05/16
 *     desc   : 碎片管理工具类
 *     version: 1.0
 * </pre>
 */
public class FragmentUtils {

    public static final int NO_ID = -1;//没有资源id
    public static final int TYPE_REPLACE = 0x00;//替换
    public static final int TYPE_ADD = 0x01;//添加
    public static final int TYPE_SHOW = 0x02;//显示
    public static final int TYPE_HIDE = 0x03;//隐藏

    /**
     * 替换Fragment，默认添加到栈中
     *
     * @param fragmentManager fragment管理器
     * @param containerViewId 容器ID
     * @param f               替换的Fragment
     * @param <F>             替换的Fragment类型
     */
    public static <F extends BaseFragment> void replaceFragment(
            FragmentManager fragmentManager, int containerViewId, F f) {
        replaceFragment(fragmentManager, containerViewId, f, true,
                R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * 替换Fragment
     *
     * @param fragmentManager  fragment管理器
     * @param containerViewId  容器ID
     * @param f                替换的Fragment
     * @param isAddToBack      是否添加到栈中
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     * @param <F>              替换的Fragment类型
     */
    public static <F extends BaseFragment> void replaceFragment(
            FragmentManager fragmentManager, int containerViewId, F f, boolean isAddToBack,
            int enterAnimationId, int exitAnimationId) {
        updateFragment(fragmentManager, containerViewId, f, isAddToBack, TYPE_REPLACE,
                enterAnimationId, exitAnimationId);
    }

    /**
     * 更新布局中的Fragment
     *
     * @param manager          fragment管理器
     * @param containerViewId  父布局id
     * @param fragment         要更新的新fragment
     * @param isAddToBack      是否添加到栈中
     * @param type             更新的方式类型
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     */
    public static void updateFragment(FragmentManager manager, int containerViewId, Fragment fragment,
                                      boolean isAddToBack, int type,
                                      int enterAnimationId, int exitAnimationId) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (enterAnimationId != NO_ID && exitAnimationId != NO_ID) {
            transaction.setCustomAnimations(enterAnimationId, exitAnimationId);
        }
        switch (type) {
            case TYPE_REPLACE:
                transaction.replace(containerViewId, fragment);
                if (isAddToBack) {
                    transaction.addToBackStack(null);
                }
                break;
            case TYPE_ADD:
                transaction.add(containerViewId, fragment, fragment.getClass().getSimpleName());
                break;
            case TYPE_SHOW:
                transaction.show(fragment);
                break;
            case TYPE_HIDE:
                transaction.hide(fragment);
                break;
        }
        transaction.commit();
    }

    /**
     * 显示使用add模式的fragment
     *
     * @param manager          fragment管理器
     * @param containerViewId  父布局id
     * @param fragment         要更新的新fragment
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     */
    public static void showFragment(FragmentManager manager, int containerViewId, Fragment fragment,
                                    int enterAnimationId, int exitAnimationId) {
        if (!fragment.isAdded()) {
            FragmentUtils.updateFragment(manager, containerViewId, fragment, false,
                    FragmentUtils.TYPE_ADD, enterAnimationId, exitAnimationId);
        } else {
            FragmentUtils.updateFragment(manager, containerViewId, fragment, false,
                    FragmentUtils.TYPE_SHOW, enterAnimationId, exitAnimationId);
        }
    }

    /**
     * 隐藏Fragment
     *
     * @param fragmentManager  Fragment管理器
     * @param fragment         碎片
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     */
    public static void hideFragment(FragmentManager fragmentManager, Fragment fragment,
                                    int enterAnimationId, int exitAnimationId) {
        if (fragment != null && !fragment.isHidden()) {
            FragmentUtils.updateFragment(fragmentManager, FragmentUtils.NO_ID, fragment, false,
                    FragmentUtils.TYPE_HIDE, enterAnimationId, exitAnimationId);
        }
    }

    /**
     * 显示及隐藏Fragment
     *
     * @param fragmentManager  碎片管理器
     * @param hideFg           需要隐藏的Fragment
     * @param containerViewId  Fragment容器资源ID
     * @param showFg           需要显示的Fragment
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     */
    public static void showAndHideFragment(FragmentManager fragmentManager, int containerViewId,
                                           Fragment hideFg, Fragment showFg,
                                           int enterAnimationId, int exitAnimationId) {
        FragmentUtils.hideFragment(fragmentManager, hideFg, enterAnimationId, exitAnimationId);
        FragmentUtils.showFragment(fragmentManager, containerViewId, showFg,
                enterAnimationId, exitAnimationId);
    }

    /**
     * 返回上一个fragment
     *
     * @param fragment 当前fragment
     */
    public static void returnToPreviousFragment(@NonNull BaseFragment fragment) {
        FragmentActivity activity = fragment.getActivity();
        if (activity instanceof BaseFragment.Callback) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(BUNDLE_RETURN_TO_PREVIOUS_FRAGMENT, true);
            ((BaseFragment.Callback) activity).onCall(fragment, bundle);
        }
    }
}
