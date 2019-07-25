package com.mingyuechunqiu.agile.util;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mingyuechunqiu.agile.R;

import java.util.List;

import static com.mingyuechunqiu.agile.util.FragmentUtils.TransactionTypeConstants.TYPE_ADD;
import static com.mingyuechunqiu.agile.util.FragmentUtils.TransactionTypeConstants.TYPE_HIDE;
import static com.mingyuechunqiu.agile.util.FragmentUtils.TransactionTypeConstants.TYPE_REMOVE;
import static com.mingyuechunqiu.agile.util.FragmentUtils.TransactionTypeConstants.TYPE_REPLACE;
import static com.mingyuechunqiu.agile.util.FragmentUtils.TransactionTypeConstants.TYPE_SHOW;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/16
 *     desc   : Fragment管理工具类
 *     version: 1.0
 * </pre>
 */
public class FragmentUtils {

    public static final int NO_ID = -1;//无效资源ID

    /**
     * 替换Fragment，默认添加到栈中
     *
     * @param fragmentManager fragment管理器
     * @param containerViewId 容器ID
     * @param fragment        替换的Fragment
     */
    public static void replaceFragment(
            FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment) {
        replaceFragment(fragmentManager, containerViewId, fragment, false, true,
                R.anim.agile_slide_in_right, R.anim.agile_slide_out_left);
    }

    /**
     * 替换Fragment
     *
     * @param fragmentManager  fragment管理器
     * @param containerViewId  容器ID
     * @param fragment         替换的Fragment
     * @param addToBackStack   是否添加到栈中
     * @param allowStateLoss   是否允许丢失状态
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     */
    public static void replaceFragment(
            FragmentManager fragmentManager, @IdRes int containerViewId, Fragment fragment,
            boolean addToBackStack, boolean allowStateLoss,
            @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId) {
        updateFragment(fragmentManager, containerViewId, fragment, addToBackStack, allowStateLoss,
                TYPE_REPLACE, enterAnimationId, exitAnimationId);
    }

    /**
     * 更新布局中的Fragment
     *
     * @param manager          fragment管理器
     * @param containerViewId  父布局id
     * @param fragment         要更新的新fragment
     * @param addToBackStack   是否添加到栈中
     * @param allowStateLoss   是否允许丢失状态
     * @param type             更新的方式类型
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     */
    public static void updateFragment(FragmentManager manager, @IdRes int containerViewId, Fragment fragment,
                                      boolean addToBackStack, boolean allowStateLoss, int type,
                                      @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId) {
        updateFragment(manager, containerViewId, fragment, addToBackStack, null, allowStateLoss, type, enterAnimationId, exitAnimationId);
    }

    /**
     * 更新布局中的Fragment
     *
     * @param manager          fragment管理器
     * @param containerViewId  父布局id
     * @param fragment         要更新的新fragment
     * @param addToBackStack   是否添加到栈中
     * @param allowStateLoss   是否允许丢失状态
     * @param type             更新的方式类型
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     */
    public static void updateFragment(FragmentManager manager, @IdRes int containerViewId, Fragment fragment,
                                      boolean addToBackStack, String backStackName,
                                      boolean allowStateLoss, int type,
                                      @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId) {
        if (manager == null || fragment == null) {
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        if (enterAnimationId != NO_ID && exitAnimationId != NO_ID) {
            transaction.setCustomAnimations(enterAnimationId, exitAnimationId);
        }
        handleTransaction(containerViewId, fragment, addToBackStack, backStackName, allowStateLoss, type, transaction);
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
    public static void showFragment(FragmentManager manager, @IdRes int containerViewId, Fragment fragment,
                                    @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId) {
        if (manager == null || fragment == null) {
            return;
        }
        if (fragment.isAdded()) {
            FragmentUtils.updateFragment(manager, containerViewId, fragment, false, true,
                    TYPE_SHOW, enterAnimationId, exitAnimationId);
        } else {
            FragmentUtils.updateFragment(manager, containerViewId, fragment, false, true,
                    TYPE_ADD, enterAnimationId, exitAnimationId);
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
                                    @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId) {
        if (fragment != null && !fragment.isHidden()) {
            FragmentUtils.updateFragment(fragmentManager, FragmentUtils.NO_ID, fragment, false, true,
                    TYPE_HIDE, enterAnimationId, exitAnimationId);
        }
    }

    /**
     * 显示及隐藏Fragment
     *
     * @param fragmentManager  碎片管理器
     * @param containerViewId  Fragment容器资源ID
     * @param hideFg           需要隐藏的Fragment
     * @param showFg           需要显示的Fragment
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     */
    public static void showAndHideFragment(FragmentManager fragmentManager, @IdRes int containerViewId,
                                           Fragment hideFg, Fragment showFg,
                                           @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId) {
        if (fragmentManager == null) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (enterAnimationId != NO_ID && exitAnimationId != NO_ID) {
            transaction.setCustomAnimations(enterAnimationId, exitAnimationId);
        }
        if (hideFg != null) {
            transaction.hide(hideFg);
        }
        if (showFg != null) {
            if (showFg.isAdded()) {
                transaction.show(showFg);
            } else {
                transaction.add(containerViewId, showFg, showFg.getClass().getSimpleName());
            }
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 移除Fragment
     *
     * @param fragmentManager Fragment管理器
     * @param fragments       要移除的Fragment
     */
    public static void removeFragments(@Nullable FragmentManager fragmentManager,
                                       @Nullable Fragment... fragments) {
        removeFragments(fragmentManager, false, fragments);
    }

    /**
     * 移除Fragment
     *
     * @param fragmentManager Fragment管理器
     * @param fragmentList    要移除的Fragment集合
     */
    public static void removeFragments(@Nullable FragmentManager fragmentManager,
                                       @Nullable List<Fragment> fragmentList) {
        removeFragments(fragmentManager, false, fragmentList);
    }

    /**
     * 移除Fragment
     *
     * @param fragmentManager Fragment管理器
     * @param allowStateLoss  是否允许丧失状态
     * @param fragments       要移除的Fragment
     */
    public static void removeFragments(@Nullable FragmentManager fragmentManager,
                                       boolean allowStateLoss,
                                       @Nullable Fragment... fragments) {
        if (fragmentManager == null || fragments == null || fragments.length == 0) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isAdded()) {
                transaction.remove(fragment);
            }
        }
        if (allowStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    /**
     * 移除Fragment
     *
     * @param fragmentManager Fragment管理器
     * @param allowStateLoss  是否允许丢失状态
     * @param fragmentList    要移除的Fragment集合
     */
    public static void removeFragments(@Nullable FragmentManager fragmentManager,
                                       boolean allowStateLoss,
                                       @Nullable List<Fragment> fragmentList) {
        if (fragmentList == null) {
            return;
        }
        Fragment[] fragments = new Fragment[fragmentList.size()];
        removeFragments(fragmentManager, allowStateLoss, fragmentList.toArray(fragments));
    }

    /**
     * 处理事务
     *
     * @param containerViewId 父布局id
     * @param fragment        要更新的新fragment
     * @param addToBackStack  是否添加到栈中
     * @param backStackName   后退栈名称
     * @param allowStateLoss  是否允许丢失状态
     * @param type            更新的方式类型
     * @param transaction     事务
     */
    private static void handleTransaction(@IdRes int containerViewId, Fragment fragment,
                                          boolean addToBackStack, String backStackName,
                                          boolean allowStateLoss, int type, FragmentTransaction transaction) {
        if (fragment == null || transaction == null) {
            return;
        }
        switch (type) {
            case TYPE_REPLACE:
                transaction.replace(containerViewId, fragment);
                if (addToBackStack) {
                    transaction.addToBackStack(backStackName);
                }
                break;
            case TYPE_ADD:
                if (!fragment.isAdded()) {
                    transaction.add(containerViewId, fragment, fragment.getClass().getSimpleName());
                }
                break;
            case TYPE_SHOW:
                transaction.show(fragment);
                break;
            case TYPE_HIDE:
                transaction.hide(fragment);
                break;
            case TYPE_REMOVE:
                if (fragment.isAdded()) {
                    transaction.remove(fragment);
                }
                break;
            default:
                break;
        }
        if (allowStateLoss) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }

    /**
     * Fragment事务操作类型常量类
     */
    public static class TransactionTypeConstants {

        public static final int TYPE_REPLACE = 0x00;//替换
        public static final int TYPE_ADD = 0x01;//添加
        public static final int TYPE_SHOW = 0x02;//显示
        public static final int TYPE_HIDE = 0x03;//隐藏
        public static final int TYPE_REMOVE = 0x04;//移除

    }
}
