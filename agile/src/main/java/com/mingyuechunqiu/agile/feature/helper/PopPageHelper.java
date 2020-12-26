package com.mingyuechunqiu.agile.feature.helper;

import android.os.Build;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.mingyuechunqiu.agile.feature.logmanager.LogManagerProvider;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       12/26/20 1:24 PM
 *      Desc:       弹出界面辅助类，实现生命周期自动感知，清除数据
 *                  实现LifecycleObserver
 *      Version:    1.0
 * </pre>
 */
public final class PopPageHelper implements LifecycleObserver {

    private final WeakReference<LifecycleOwner> mLifecycleOwnerRef;
    private final PopPageCallback mCallback;
    private final CopyOnWriteArrayList<PopPageInfo> mPopInfoList;

    private PopPageHelper(@NonNull LifecycleOwner owner, @NonNull PopPageCallback callback) {
        mLifecycleOwnerRef = new WeakReference<>(owner);
        mCallback = callback;
        mPopInfoList = new CopyOnWriteArrayList<>();
        owner.getLifecycle().addObserver(this);
    }

    @NonNull
    public static PopPageHelper newInstance(@NonNull FragmentActivity activity, @NonNull PopPageCallback callback) {
        return new PopPageHelper(activity, callback);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        mPopInfoList.clear();
        if (mLifecycleOwnerRef.get() == null) {
            return;
        }
        mLifecycleOwnerRef.get().getLifecycle().removeObserver(this);
    }

    @NonNull
    public PopPageHelper addPopPage(@NonNull PopPageInfo info) {
        mPopInfoList.add(info);
        Collections.sort(mPopInfoList);
        Collections.reverse(mPopInfoList);
        return this;
    }

    /**
     * 根据Id移除单个弹出界面
     *
     * @param id 弹出界面信息Id
     */
    public void removePopPageWithId(@NonNull String id) {
        for (PopPageInfo info : mPopInfoList) {
            if (id.equals(info.id)) {
                mPopInfoList.remove(info);
                break;
            }
        }
    }

    /**
     * 根据Tag移除一个或多个相同Tag弹出界面
     *
     * @param tag 弹出界面信息Tag
     */
    public void removePopPageWithTag(@NonNull String tag) {
        for (PopPageInfo info : mPopInfoList) {
            if (tag.equals(info.tag)) {
                mPopInfoList.remove(info);
            }
        }
    }

    public void showPopPage(@NonNull Lifecycle.State currentState) {
        if (!mCallback.canShowPopPage()) {
            return;
        }
        if (mPopInfoList.isEmpty()) {
            return;
        }
        LifecycleOwner owner = mLifecycleOwnerRef.get();
        if (owner == null) {
            LogManagerProvider.w("showPopPage", "LifecycleOwner == null");
            return;
        }
        if (owner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            LogManagerProvider.w("showPopPage", "LifecycleOwner destroyed");
            onDestroy();
            return;
        }
        for (PopPageInfo info : mPopInfoList) {
            if (info == null || info.page == null || !currentState.isAtLeast(info.showLifecycleState)) {
                continue;
            }
            if (info.page.isShouldShow(owner)) {
                info.page.show(owner);
                mPopInfoList.remove(info);
                break;
            }
        }
    }

    public void showHeaderPopPage(@NonNull Lifecycle.State currentState) {
        if (!mCallback.canShowPopPage()) {
            return;
        }
        if (mPopInfoList.isEmpty()) {
            return;
        }
        PopPageInfo info = mPopInfoList.get(0);
        if (info == null || info.page == null) {
            return;
        }
        LifecycleOwner owner = mLifecycleOwnerRef.get();
        if (owner == null) {
            LogManagerProvider.w("showPopPage", "LifecycleOwner == null");
            return;
        }
        if (owner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            LogManagerProvider.w("showPopPage", "LifecycleOwner destroyed");
            onDestroy();
            return;
        }
        if (info == null || info.page == null || !currentState.isAtLeast(info.showLifecycleState)) {
            return;
        }
        if (info.page.isShouldShow(owner)) {
            info.page.show(owner);
            mPopInfoList.remove(0);
        }
    }

    public interface PopPageCallback {

        boolean canShowPopPage();
    }

    public static class PopPageInfo implements Comparable<PopPageInfo> {

        private final String id;
        private final String tag;
        private final IPopPage page;
        private final int priority;
        private final Lifecycle.State showLifecycleState;

        public PopPageInfo(@IntRange(from = 0, to = 10000) int priority, @NonNull IPopPage page) {
            this(null, priority, Lifecycle.State.STARTED, page);
        }

        public PopPageInfo(@Nullable String tag, @IntRange(from = 0, to = 10000) int priority, @NonNull IPopPage page) {
            this(tag, priority, Lifecycle.State.STARTED, page);
        }

        public PopPageInfo(@Nullable String tag, @IntRange(from = 0, to = 10000) int priority, @NonNull Lifecycle.State showLifecycleState, @NonNull IPopPage page) {
            this.id = UUID.randomUUID().toString();
            this.tag = tag;
            this.priority = priority;
            this.showLifecycleState = showLifecycleState;
            this.page = page;
        }

        @Override
        public int compareTo(@NonNull PopPageInfo o) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return Integer.compare(priority, o.priority);
            } else {
                return priority > o.priority ? 1 : (priority == o.priority ? 0 : -1);
            }
        }

        @NonNull
        @Override
        public String toString() {
            return "PopPageInfo{" +
                    "id='" + id + '\'' +
                    ", tag='" + tag + '\'' +
                    ", page=" + page +
                    ", priority=" + priority +
                    ", showLifecycleState=" + showLifecycleState +
                    '}';
        }
    }

    public interface IPopPage {

        boolean isShouldShow(@NonNull LifecycleOwner owner);

        void show(@NonNull LifecycleOwner owner);
    }
}
