package com.mingyuechunqiu.agile.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.feature.helper.ScreenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *      Project:    Agile
 *
 *      Author:     xiyujie
 *      Github:     https://github.com/MingYueChunQiu
 *      Email:      xiyujieit@163.com
 *      Time:       2020/3/28 10:47 PM
 *      Desc:
 *      Version:    1.0
 * </pre>
 */
public class BottomNavigationBar extends LinearLayoutCompat {

    private List<BottomNavigationTabItem> mTabList;

    private LinearLayoutCompat llTabContainer;

    private @ColorInt
    int mTextInactiveColor, mTextActiveColor;
    private float mTextInactiveSize, mTextActiveSize;
    private float mIconInactiveSize, mIconActiveSize;
    private boolean textInactiveBold, textActiveBold;

    private int mSelectedTabItemIndex = -1;//被选中的Tab索引位置
    private OnTabItemSelectedListener mOnTabItemSelectedListener;

    public BottomNavigationBar(Context context) {
        this(context, null);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationBar);
        mTextInactiveColor = a.getColor(R.styleable.BottomNavigationBar_bnb_text_inactive_color, Color.GRAY);
        mTextActiveColor = a.getColor(R.styleable.BottomNavigationBar_bnb_text_active_color, Color.BLACK);
        mTextInactiveSize = a.getDimensionPixelSize(R.styleable.BottomNavigationBar_bnb_text_inactive_size, (int) ScreenHelper.getPxFromSp(getResources(), 12));
        mTextActiveSize = a.getDimensionPixelSize(R.styleable.BottomNavigationBar_bnb_text_active_size, (int) ScreenHelper.getPxFromSp(getResources(), 14));
        textInactiveBold = a.getBoolean(R.styleable.BottomNavigationBar_bnb_text_inactive_bold, false);
        textActiveBold = a.getBoolean(R.styleable.BottomNavigationBar_bnb_text_active_bold, false);

        mIconInactiveSize = a.getDimensionPixelSize(R.styleable.BottomNavigationBar_bnb_icon_inactive_size, (int) ScreenHelper.getPxFromDp(getResources(), 20));
        mIconActiveSize = a.getDimensionPixelSize(R.styleable.BottomNavigationBar_bnb_icon_active_size, (int) ScreenHelper.getPxFromDp(getResources(), 22));

        a.recycle();
        mTabList = new ArrayList<>();
        llTabContainer = new LinearLayoutCompat(context);
        setGravity(Gravity.CENTER);
        addView(llTabContainer);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(widthMeasureSpec), getMeasuredHeight(heightMeasureSpec));
        if (llTabContainer != null) {
            LayoutParams params = (LayoutParams) llTabContainer.getLayoutParams();
            params.width = getWidth();
            llTabContainer.setLayoutParams(params);
        }
    }

    public BottomNavigationBar addTabItem(BottomNavigationTabItem item) {
        mTabList.add(item);
        return this;
    }

    public BottomNavigationBar setTabItems(List<BottomNavigationTabItem> items) {
        mTabList.clear();
        mTabList.addAll(items);
        return this;
    }

    public BottomNavigationBar setOnTabItemSelectedListener(OnTabItemSelectedListener listener) {
        mOnTabItemSelectedListener = listener;
        return this;
    }

    public void selectTabItem(int position) {
        int count = llTabContainer.getChildCount();
        if (count == 0 || position < 0 || position > count) {
            return;
        }
        if (position != mSelectedTabItemIndex) {
            if (mSelectedTabItemIndex != -1) {
                updateTabItemAppearance(llTabContainer.getChildAt(mSelectedTabItemIndex),
                        mTabList.get(mSelectedTabItemIndex), false);
            }
            updateTabItemAppearance(llTabContainer.getChildAt(position), mTabList.get(position), true);
        }
        handleTabItemSelected(position, mTabList.get(position));
    }

    public int getSelectedTabItemIndex() {
        return mSelectedTabItemIndex;
    }

    /**
     * 获取指定索引位置的TabItem
     *
     * @param position Item索引位置
     * @return 返回对应索引位置的TabItem
     */
    @Nullable
    public BottomNavigationTabItem getTabItem(int position) {
        if (position < 0 || position >= mTabList.size()) {
            return null;
        }
        return mTabList.get(position);
    }

    /**
     * 在设置最后调用进行添加初始化（必须调用才有效果）
     */
    public void initialize() {
        int count = mTabList.size();
        if (count == 0) {
            return;
        }
        if (llTabContainer != null) {
            llTabContainer.removeAllViews();
        }
        for (int i = 0; i < count; i++) {
            addTabView(i);
        }
        selectTabItem(0);
    }

    public boolean isInitialized() {
        return mTabList != null && mTabList.size() > 0;
    }

    /**
     * 更新Tab的样式
     *
     * @param vItemContainer Item的容器
     * @param item           Item
     * @param active         是否处于活跃选中状态
     */
    private void updateTabItemAppearance(@NonNull View vItemContainer, @NonNull BottomNavigationTabItem item, boolean active) {
        AppCompatImageView ivIcon = vItemContainer.findViewById(R.id.iv_bnb_tab_item_icon);
        AppCompatTextView tvItem = vItemContainer.findViewById(R.id.tv_bnb_tab_item_text);

        Drawable drawable = getTabItemIcon(active ? item.getItemActiveIcon() : item.getItemInactiveIcon(),
                active ? item.getItemActiveIconResId() : item.getItemInactiveIconResId());
        if (drawable != null) {
            ivIcon.setImageDrawable(drawable);
        } else {
            String url = active ? item.getItemActiveIconUrl() : item.getItemInactiveIconUrl();
            if (!TextUtils.isEmpty(url)) {
                ivIcon.setImageURI(Uri.parse(url));
            }
        }
        int size = active ? (int) mIconActiveSize : (int) mIconInactiveSize;
        ivIcon.getLayoutParams().width = size;
        ivIcon.getLayoutParams().height = size;
        if (!item.isHideText()) {
            tvItem.setTextColor(active ? mTextActiveColor : mTextInactiveColor);
            tvItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, active ? mTextActiveSize : mTextInactiveSize);
            tvItem.getPaint().setFakeBoldText(active ? textActiveBold : textInactiveBold);
        }
        //必须有
        ivIcon.requestLayout();
    }

    /**
     * 添加Tab到容器中
     *
     * @param index 被添加的Tab索引位置
     */
    private void addTabView(final int index) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.agile_layout_bnb_tab_item, llTabContainer, false);

        //平均分配容器空间
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0F);
        view.setLayoutParams(params);
        BottomNavigationTabItem item = mTabList.get(index);

        AppCompatTextView tvItem = view.findViewById(R.id.tv_bnb_tab_item_text);
        tvItem.setVisibility(item.isHideText() ? GONE : VISIBLE);
        if (!item.isHideText()) {
            tvItem.setText(mTabList.get(index).getItemText());
        }

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTabItem(index);
            }
        });
        llTabContainer.addView(view);
        updateTabItemAppearance(view, mTabList.get(index), false);
    }

    /**
     * 获取Tab的图标
     *
     * @param iconDrawable      图标
     * @param iconDrawableResId 图标资源Id
     * @return 返回创建好的图标资源
     */
    private Drawable getTabItemIcon(Drawable iconDrawable, int iconDrawableResId) {
        Drawable icon = iconDrawable;
        if (icon == null) {
            if (iconDrawableResId != 0) {
                icon = getResources().getDrawable(iconDrawableResId);
            }
        }
        return icon;
    }

    /**
     * 处理Tab被选中事件
     *
     * @param index 被选中的Tab索引位置
     * @param item  被选中的TabItem
     */
    private void handleTabItemSelected(int index, BottomNavigationTabItem item) {
        if (mOnTabItemSelectedListener == null) {
            return;
        }
        if (mSelectedTabItemIndex == index) {
            mOnTabItemSelectedListener.onTabItemReselected(item, index);
        } else {
            if (mSelectedTabItemIndex != -1) {
                mOnTabItemSelectedListener.onTabItemUnselected(mTabList.get(mSelectedTabItemIndex), mSelectedTabItemIndex);
            }
            mOnTabItemSelectedListener.onTabItemSelected(item, index);
            mSelectedTabItemIndex = index;
        }
    }

    /**
     * 获取测量宽度
     *
     * @param widthMeasureSpec 传入宽度
     * @return 返回处理过的宽度
     */
    private int getMeasuredWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            size = getResources().getDisplayMetrics().widthPixels;
        }
        return size;
    }

    /**
     * 获取测量高度
     *
     * @param heightMeasureSpec 传入高度
     * @return 返回处理过的高度
     */
    private int getMeasuredHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            size = llTabContainer == null
                    ? (int) ScreenHelper.getPxFromDp(getResources(), 56F)
                    : llTabContainer.getHeight();
        }
        return size;
    }

    /**
     * 选项Item
     */
    public static class BottomNavigationTabItem {

        private Builder mBuilder;

        public BottomNavigationTabItem() {
            this(new Builder());
        }

        public BottomNavigationTabItem(String text, @DrawableRes int iconInactiveResId,
                                       @DrawableRes int iconActiveResId) {
            this(new Builder().setItemText(text)
                    .setItemInactiveIconResId(iconInactiveResId)
                    .setItemActiveIconResId(iconActiveResId));
        }

        public BottomNavigationTabItem(@NonNull Builder builder) {
            mBuilder = builder;
        }

        public String getItemText() {
            return mBuilder.itemText;
        }

        public void setItemText(String itemText) {
            mBuilder.itemText = itemText;
        }

        public int getItemTextResId() {
            return mBuilder.itemTextResId;
        }

        public void setItemTextResId(int itemTextResId) {
            mBuilder.itemTextResId = itemTextResId;
        }

        public Drawable getItemInactiveIcon() {
            return mBuilder.itemInactiveIcon;
        }

        public void setItemInactiveIcon(Drawable itemInactiveIcon) {
            mBuilder.itemInactiveIcon = itemInactiveIcon;
        }

        public int getItemInactiveIconResId() {
            return mBuilder.itemInactiveIconResId;
        }

        public void setItemInactiveIconResId(@DrawableRes int itemInactiveIconResId) {
            mBuilder.itemInactiveIconResId = itemInactiveIconResId;
        }

        public String getItemInactiveIconUrl() {
            return mBuilder.itemInactiveIconUrl;
        }

        public void setItemInactiveIconUrl(@Nullable String itemInactiveIconUrl) {
            mBuilder.itemInactiveIconUrl = itemInactiveIconUrl;
        }

        public Drawable getItemActiveIcon() {
            return mBuilder.itemActiveIcon;
        }

        public void setItemActiveIcon(Drawable itemActiveIcon) {
            mBuilder.itemActiveIcon = itemActiveIcon;
        }

        public int getItemActiveIconResId() {
            return mBuilder.itemActiveIconResId;
        }

        public void setItemActiveIconResId(@DrawableRes int itemActiveIconResId) {
            mBuilder.itemActiveIconResId = itemActiveIconResId;
        }

        public String getItemActiveIconUrl() {
            return mBuilder.itemActiveIconUrl;
        }

        public void setItemActiveIconUrl(@Nullable String itemActiveIconUrl) {
            mBuilder.itemActiveIconUrl = itemActiveIconUrl;
        }

        public boolean isHideText() {
            return mBuilder.hideText;
        }

        public void setHideText(boolean hideText) {
            mBuilder.hideText = hideText;
        }

        /**
         * 链式调用
         */
        public static class Builder {

            private String itemText;//文本

            private @StringRes
            int itemTextResId;//文本资源id

            private Drawable itemInactiveIcon;//未活跃图标

            private @DrawableRes
            int itemInactiveIconResId;//未活跃图标图标资源id

            private String itemInactiveIconUrl;//未活跃图标网址

            private Drawable itemActiveIcon;//活跃图标

            private @DrawableRes
            int itemActiveIconResId;//活跃图标资源id

            private String itemActiveIconUrl;//活跃图标网址

            private boolean hideText;//标记是否隐藏文字

            public BottomNavigationTabItem build() {
                return new BottomNavigationTabItem(this);
            }

            public String getItemText() {
                return itemText;
            }

            public Builder setItemText(String itemText) {
                this.itemText = itemText;
                return this;
            }

            public int getItemTextResId() {
                return itemTextResId;
            }

            public Builder setItemTextResId(int itemTextResId) {
                this.itemTextResId = itemTextResId;
                return this;
            }

            public Drawable getItemInactiveIcon() {
                return itemInactiveIcon;
            }

            public Builder setItemInactiveIcon(Drawable itemInactiveIcon) {
                this.itemInactiveIcon = itemInactiveIcon;
                return this;
            }

            public int getItemInactiveIconResId() {
                return itemInactiveIconResId;
            }

            public Builder setItemInactiveIconResId(@DrawableRes int itemInactiveIconResId) {
                this.itemInactiveIconResId = itemInactiveIconResId;
                return this;
            }

            public String getItemInactiveIconUrl() {
                return itemInactiveIconUrl;
            }

            public Builder setItemInactiveIconUrl(@Nullable String itemInactiveIconUrl) {
                this.itemInactiveIconUrl = itemInactiveIconUrl;
                return this;
            }

            public Drawable getItemActiveIcon() {
                return itemActiveIcon;
            }

            public Builder setItemActiveIcon(Drawable itemActiveIcon) {
                this.itemActiveIcon = itemActiveIcon;
                return this;
            }

            public int getItemActiveIconResId() {
                return itemActiveIconResId;
            }

            public Builder setItemActiveIconResId(@DrawableRes int itemActiveIconResId) {
                this.itemActiveIconResId = itemActiveIconResId;
                return this;
            }

            public String getItemActiveIconUrl() {
                return itemActiveIconUrl;
            }

            public Builder setItemActiveIconUrl(@Nullable String itemActiveIconUrl) {
                this.itemActiveIconUrl = itemActiveIconUrl;
                return this;
            }

            public boolean isHideText() {
                return hideText;
            }

            public Builder setHideText(boolean hideText) {
                this.hideText = hideText;
                return this;
            }
        }
    }

    /**
     * 当Tab选项选中监听器
     */
    public interface OnTabItemSelectedListener {

        void onTabItemSelected(BottomNavigationTabItem item, int position);

        void onTabItemUnselected(BottomNavigationTabItem item, int position);

        void onTabItemReselected(BottomNavigationTabItem item, int position);
    }
}
