package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mingyuechunqiu.agile.R;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/4/7 4:39 PM
 *      Desc:       可无线循环滚动控件
 *                  继承自FrameLayout
 *      Version:    1.0
 * </pre>
 */
public class LoopViewPager extends FrameLayout {

    private static final int DEFAULT_PLAY_INTERVAL_TIME = 3000;//默认播放间隔时间
    private static final int MSG_PLAY = 0;//播放消息码

    private ViewPager vpItem;

    private int mCurrentPosition = 1;//当前选中Item位置（因为需要在首尾各添加对应用于循环的item，所以要从1位置开始）
    private LoopHandler mHandler;
    private boolean openLoop;//标记是否开启循环
    private int mIntervalTime;//循环间隔时间
    private State mState = State.STATE_IDLE;//默认处于空闲状态

    public LoopViewPager(@NonNull Context context) {
        this(context, null);
    }

    public LoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case ACTION_DOWN:
                pausePlay();
                break;
            //用于点击抬起情况的判断，重新开始播放
            case ACTION_UP:
            case ACTION_CANCEL:
                resumePlay();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public <T> void setAdapter(@NonNull LoopPageAdapter<T> adapter) {
        vpItem.setAdapter(new LoopPageAdapterWrapper<>(adapter));
        mCurrentPosition = 1;
        vpItem.setCurrentItem(mCurrentPosition, false);
    }

    public <T> void setOnItemClickListener(@NonNull OnItemListener<T> listener) {
        PagerAdapter pagerAdapter = vpItem.getAdapter();
        if (pagerAdapter instanceof LoopPageAdapterWrapper) {
            //noinspection unchecked
            ((LoopPageAdapterWrapper) pagerAdapter).setOnItemClickListener(listener);
        }
    }

    /**
     * 开始循环
     */
    public void startPlay() {
        if (!openLoop) {
            return;
        }
        mState = State.STATE_PLAY;
        if (mHandler == null) {
            mHandler = new LoopHandler(this);
        }
        mHandler.sendEmptyMessageDelayed(MSG_PLAY, mIntervalTime);
    }

    public void pausePlay() {
        releaseLoopResource();
        mState = State.STATE_PAUSE;
    }

    public void resumePlay() {
        if (mState == State.STATE_PAUSE) {
            startPlay();
        }
    }

    public boolean isOpenLoop() {
        return openLoop;
    }

    public void setOpenLoop(boolean openLoop) {
        this.openLoop = openLoop;
    }

    public int getLoopTime() {
        return mIntervalTime;
    }

    public void setLoopTime(int loopTime) {
        mIntervalTime = loopTime;
    }

    private void updateCurrentPosition() {
        mCurrentPosition++;
        vpItem.setCurrentItem(mCurrentPosition);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopViewPager);
        openLoop = a.getBoolean(R.styleable.LoopViewPager_lvp_loop, true);
        mIntervalTime = a.getInt(R.styleable.LoopViewPager_lvp_interval_time, DEFAULT_PLAY_INTERVAL_TIME);
        a.recycle();
        vpItem = new ViewPager(context);
        vpItem.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        vpItem.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //用于拖拽后的判断（它不会触发UP事件，所以无法拦截），重新开始播放
                if (mState == State.STATE_PAUSE && state == SCROLL_STATE_IDLE) {
                    resumePlay();
                }
                //放在onPageScrolled里，需要处理数据顺序，因为一开始默认0位置就会调用onPageScrolled方法，
                // 导致切换到已经添加过首尾数据的List的倒数第二个Item
                //不能放在onPageSelected里，否则会有快速切换现象
                if (!openLoop || state != SCROLL_STATE_IDLE) {
                    return;
                }
                PagerAdapter pagerAdapter = vpItem.getAdapter();
                if (pagerAdapter == null) {
                    return;
                }
                if (mCurrentPosition == 0) {
                    mCurrentPosition = pagerAdapter.getCount() - 2;
                    vpItem.setCurrentItem(mCurrentPosition, false);
                } else if (mCurrentPosition == pagerAdapter.getCount() - 1) {
                    mCurrentPosition = 1;
                    vpItem.setCurrentItem(mCurrentPosition, false);
                }
            }
        });
        addView(vpItem);
    }

    private void releaseLoopResource() {
        if (mHandler != null) {
            mHandler.removeMessages(MSG_PLAY);
        }
    }

    /**
     * 需要用户去实现的列表适配器
     *
     * @param <T> Item类型
     */
    public static abstract class LoopPageAdapter<T> extends PagerAdapter {

        private SparseArray<Object> mCacheItemView = new SparseArray<>();//缓存对应Item控件实例，避免不断创建导致闪烁内存抖动等问题
        private List<T> mData;//Item数据集合
        private OnItemListener<T> mListener;//Item事件监听器

        public LoopPageAdapter(@NonNull List<T> data) {
            mData = data;
            if (mData.size() <= 1) {
                return;
            }
            //首尾各添加对应Item，用于循环处理
            mData.add(0, mData.get(mData.size() - 1));
            mData.add(mData.get(0));
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
            Object itemView = mCacheItemView.get(position);
            if (itemView == null) {
                itemView = onCreateItem(container, position);
                ((View) itemView).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onItemClick(container, v, position, mData.get(position));
                        }
                    }
                });
                mCacheItemView.put(position, itemView);
            }
            onBindItem(container, position, (View) itemView, mData.get(position));
            container.addView((View) itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            onDestroyItem(container, position, (View) object);
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        public void setOnItemClickListener(@NonNull OnItemListener<T> listener) {
            mListener = listener;
        }

        public abstract View onCreateItem(@NonNull ViewGroup container, int position);

        public abstract void onBindItem(@NonNull ViewGroup container, int position, @NonNull View itemView, T item);

        public abstract void onDestroyItem(@NonNull ViewGroup container, int position, @NonNull View itemView);

    }

    /**
     * 列表适配器代理类（用于包裹LoopPageAdapter，进行额外处理）
     *
     * @param <T> Item类型
     */
    private static class LoopPageAdapterWrapper<T> extends PagerAdapter {

        private LoopPageAdapter<T> mAdapter;

        LoopPageAdapterWrapper(@NonNull LoopPageAdapter<T> adapter) {
            mAdapter = adapter;
        }

        @Override
        public int getCount() {
            return mAdapter.getCount();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return mAdapter.isViewFromObject(view, object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return mAdapter.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            mAdapter.destroyItem(container, position, object);
        }

        @Override
        public void finishUpdate(@NonNull ViewGroup container) {
            mAdapter.finishUpdate(container);
        }

        public void setOnItemClickListener(@NonNull OnItemListener<T> listener) {
            mAdapter.setOnItemClickListener(listener);
        }
    }

    /**
     * 运行状态
     */
    private enum State {
        STATE_IDLE, STATE_PLAY, STATE_PAUSE
    }

    /**
     * Item点击事件监听器
     *
     * @param <T> Item类型
     */
    public interface OnItemListener<T> {

        void onItemClick(@NonNull ViewGroup container, @NonNull View itemView, int position, T item);
    }

    private static class LoopHandler extends Handler {

        private WeakReference<LoopViewPager> mViewRef;

        LoopHandler(@NonNull LoopViewPager loopViewPager) {
            mViewRef = new WeakReference<>(loopViewPager);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (mViewRef.get() == null || MSG_PLAY != msg.what) {
                return;
            }
            mViewRef.get().updateCurrentPosition();
            sendEmptyMessageDelayed(MSG_PLAY, mViewRef.get().getLoopTime());
        }
    }
}
