package com.mingyuechunqiu.agile.util;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
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

    public static final int NO_ID = 0;//无效资源ID(-1会导致资源崩溃Unable to find resource ID #0xffffffff)

    /**
     * 替换Fragment，默认添加到栈中
     *
     * @param fragmentManager fragment管理器
     * @param containerViewId 容器ID
     * @param fragment        替换的Fragment
     */
    public static void replaceFragment(
            @Nullable FragmentManager fragmentManager, @IdRes int containerViewId, @Nullable Fragment fragment) {
        replaceFragment(fragmentManager, containerViewId, fragment, false, null, true,
                NO_ID, NO_ID, NO_ID, NO_ID);
    }

    /**
     * 替换Fragment
     *
     * @param fragmentManager     fragment管理器
     * @param containerViewId     容器ID
     * @param fragment            替换的Fragment
     * @param addToBackStack      是否添加到栈中
     * @param backStackName       后退栈名称
     * @param allowStateLoss      是否允许丢失状态
     * @param enterAnimationId    入场动画
     * @param exitAnimationId     出场动画
     * @param popEnterAnimationId 弹出进入动画
     * @param popExitAnimationId  弹出退出动画
     */
    public static void replaceFragment(
            @Nullable FragmentManager fragmentManager, @IdRes int containerViewId, @Nullable Fragment fragment,
            boolean addToBackStack, @Nullable String backStackName, boolean allowStateLoss,
            @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId,
            @AnimatorRes @AnimRes int popEnterAnimationId, @AnimatorRes @AnimRes int popExitAnimationId) {
        updateFragment(fragmentManager, containerViewId, fragment, addToBackStack, backStackName, allowStateLoss,
                TYPE_REPLACE, enterAnimationId, exitAnimationId, popEnterAnimationId, popExitAnimationId);
    }

    /**
     * 更新布局中的Fragment
     *
     * @param manager          fragment管理器
     * @param containerViewId  父布局id
     * @param fragment         要更新的新fragment
     * @param allowStateLoss   是否允许丢失状态
     * @param type             更新的方式类型
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     */
    public static void updateFragment(@Nullable FragmentManager manager, @IdRes int containerViewId, @Nullable Fragment fragment,
                                      boolean allowStateLoss, int type,
                                      @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId) {
        updateFragment(manager, containerViewId, fragment, false, null, allowStateLoss, type,
                enterAnimationId, exitAnimationId, NO_ID, NO_ID);
    }

    /**
     * 更新布局中的Fragment
     *
     * @param manager             fragment管理器
     * @param containerViewId     父布局id
     * @param fragment            要更新的新fragment
     * @param addToBackStack      是否添加到栈中
     * @param backStackName       后退栈名称
     * @param allowStateLoss      是否允许丢失状态
     * @param type                更新的方式类型
     * @param enterAnimationId    入场动画
     * @param exitAnimationId     出场动画
     * @param popEnterAnimationId 弹出进入动画
     * @param popExitAnimationId  弹出退出动画
     */
    public static void updateFragment(@Nullable FragmentManager manager, @IdRes int containerViewId, @Nullable Fragment fragment,
                                      boolean addToBackStack, @Nullable String backStackName,
                                      boolean allowStateLoss, int type,
                                      @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId,
                                      @AnimatorRes @AnimRes int popEnterAnimationId, @AnimatorRes @AnimRes int popExitAnimationId) {
        if (manager == null || fragment == null) {
            return;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        if (enterAnimationId != NO_ID && exitAnimationId != NO_ID &&
                popEnterAnimationId != NO_ID && popExitAnimationId != NO_ID) {
            transaction.setCustomAnimations(enterAnimationId, exitAnimationId, popEnterAnimationId, popExitAnimationId);
        } else if (enterAnimationId != NO_ID && exitAnimationId != NO_ID) {
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
    public static void showFragment(@Nullable FragmentManager manager, @IdRes int containerViewId, @Nullable Fragment fragment,
                                    @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId) {
        if (manager == null || fragment == null) {
            return;
        }
        updateFragment(manager, containerViewId, fragment, true,
                fragment.isAdded() ? TYPE_SHOW : TYPE_ADD, enterAnimationId, exitAnimationId);
    }

    /**
     * 隐藏Fragment
     *
     * @param fragmentManager  Fragment管理器
     * @param fragment         碎片
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     */
    public static void hideFragment(@Nullable FragmentManager fragmentManager, @Nullable Fragment fragment,
                                    @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId) {
        if (fragment != null && !fragment.isHidden()) {
            FragmentUtils.updateFragment(fragmentManager, FragmentUtils.NO_ID, fragment, true,
                    TYPE_HIDE, enterAnimationId, exitAnimationId);
        }
    }

    /**
     * 显示及隐藏Fragment
     *
     * @param fragmentManager 碎片管理器
     * @param containerViewId Fragment容器资源ID
     * @param hideFg          需要隐藏的Fragment
     * @param showFg          需要显示的Fragment
     */
    public static void showAndHideFragment(@Nullable FragmentManager fragmentManager, @IdRes int containerViewId,
                                           @Nullable Fragment hideFg, @Nullable Fragment showFg) {
        showAndHideFragment(fragmentManager, containerViewId, hideFg, showFg,
                true, null, true,
                R.anim.agile_slide_in_right, R.anim.agile_slide_out_left,
                R.anim.agile_slide_in_left, R.anim.agile_slide_out_right);
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
    public static void showAndHideFragment(@Nullable FragmentManager fragmentManager, @IdRes int containerViewId,
                                           @Nullable Fragment hideFg, @Nullable Fragment showFg,
                                           @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId) {
        showAndHideFragment(fragmentManager, containerViewId, hideFg, showFg,
                false, null, true,
                enterAnimationId, exitAnimationId, NO_ID, NO_ID);
    }

    /**
     * 显示及隐藏Fragment
     *
     * @param fragmentManager     碎片管理器
     * @param containerViewId     Fragment容器资源ID
     * @param hideFg              需要隐藏的Fragment
     * @param showFg              需要显示的Fragment
     * @param addToBackStack      是否添加到栈中
     * @param backStackName       后退栈名称
     * @param allowStateLoss      是否允许丢失状态
     * @param enterAnimationId    入场动画
     * @param exitAnimationId     出场动画
     * @param popEnterAnimationId 弹出进入动画
     * @param popExitAnimationId  弹出退出动画
     */
    public static void showAndHideFragment(@Nullable FragmentManager fragmentManager, @IdRes int containerViewId,
                                           @Nullable Fragment hideFg, @Nullable Fragment showFg,
                                           boolean addToBackStack, @Nullable String backStackName,
                                           boolean allowStateLoss,
                                           @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId,
                                           @AnimatorRes @AnimRes int popEnterAnimationId, @AnimatorRes @AnimRes int popExitAnimationId) {
        if (fragmentManager == null) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (enterAnimationId != NO_ID && exitAnimationId != NO_ID &&
                popEnterAnimationId != NO_ID && popExitAnimationId != NO_ID) {
            transaction.setCustomAnimations(enterAnimationId, exitAnimationId, popEnterAnimationId, popExitAnimationId);
        } else if (enterAnimationId != NO_ID && exitAnimationId != NO_ID) {
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
        if (addToBackStack) {
            transaction.addToBackStack(backStackName);
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
     * @param fragments       要移除的Fragment
     */
    public static void removeFragments(@Nullable FragmentManager fragmentManager,
                                       @Nullable Fragment... fragments) {
        removeFragments(fragmentManager, false, NO_ID, NO_ID, fragments);
    }

    /**
     * 移除Fragment
     *
     * @param fragmentManager Fragment管理器
     * @param fragmentList    要移除的Fragment集合
     */
    public static void removeFragments(@Nullable FragmentManager fragmentManager,
                                       @Nullable List<Fragment> fragmentList) {
        removeFragments(fragmentManager, false, NO_ID, NO_ID, fragmentList);
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
        removeFragments(fragmentManager, allowStateLoss, NO_ID, NO_ID, fragments);
    }

    /**
     * 移除Fragment
     *
     * @param fragmentManager  Fragment管理器
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     * @param fragments        要移除的Fragment
     */
    public static void removeFragments(@Nullable FragmentManager fragmentManager,
                                       @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId,
                                       @Nullable Fragment... fragments) {
        removeFragments(fragmentManager, false, enterAnimationId, exitAnimationId, fragments);
    }

    /**
     * 移除Fragment
     *
     * @param fragmentManager  Fragment管理器
     * @param allowStateLoss   是否允许丧失状态
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     * @param fragments        要移除的Fragment
     */
    public static void removeFragments(@Nullable FragmentManager fragmentManager,
                                       boolean allowStateLoss,
                                       @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId,
                                       @Nullable Fragment... fragments) {
        if (fragmentManager == null || fragments == null || fragments.length == 0) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (enterAnimationId != NO_ID && exitAnimationId != NO_ID) {
            transaction.setCustomAnimations(enterAnimationId, exitAnimationId);
        }
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
        removeFragments(fragmentManager, allowStateLoss, NO_ID, NO_ID, fragmentList);
    }

    /**
     * 移除Fragment
     *
     * @param fragmentManager  Fragment管理器
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     * @param fragmentList     要移除的Fragment集合
     */
    public static void removeFragments(@Nullable FragmentManager fragmentManager,
                                       @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId,
                                       @Nullable List<Fragment> fragmentList) {
        removeFragments(fragmentManager, false, enterAnimationId, exitAnimationId, fragmentList);
    }

    /**
     * 移除Fragment
     *
     * @param fragmentManager  Fragment管理器
     * @param allowStateLoss   是否允许丢失状态
     * @param enterAnimationId 入场动画
     * @param exitAnimationId  出场动画
     * @param fragmentList     要移除的Fragment集合
     */
    public static void removeFragments(@Nullable FragmentManager fragmentManager,
                                       boolean allowStateLoss,
                                       @AnimatorRes @AnimRes int enterAnimationId, @AnimatorRes @AnimRes int exitAnimationId,
                                       @Nullable List<Fragment> fragmentList) {
        if (fragmentList == null) {
            return;
        }
        Fragment[] fragments = new Fragment[fragmentList.size()];
        removeFragments(fragmentManager, allowStateLoss, enterAnimationId, exitAnimationId, fragmentList.toArray(fragments));
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
    private static void handleTransaction(@IdRes int containerViewId, @Nullable Fragment fragment,
                                          boolean addToBackStack, @Nullable String backStackName,
                                          boolean allowStateLoss, int type, @NonNull FragmentTransaction transaction) {
        if (fragment == null) {
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
                    if (addToBackStack) {
                        transaction.addToBackStack(backStackName);
                    }
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
