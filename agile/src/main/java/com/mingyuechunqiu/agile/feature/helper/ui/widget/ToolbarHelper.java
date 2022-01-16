package com.mingyuechunqiu.agile.feature.helper.ui.widget;

import static com.mingyuechunqiu.agile.constants.AgileCommonConstants.NO_RESOURCE_ID;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.mingyuechunqiu.agile.feature.helper.ui.common.ScreenHelper;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/10/19
 *     desc   : 活动条工具类
 *     version: 1.0
 * </pre>
 */
public final class ToolbarHelper {

    private ToolbarHelper() {
    }

    /**
     * 初始化活动条
     *
     * @param toolbar   工具条
     * @param actionBar 活动条
     * @param configure 工具条信息对象
     */
    public static void initToolbar(@Nullable Toolbar toolbar, @Nullable ActionBar actionBar, @Nullable ToolbarConfigure configure) {
        if (toolbar == null || configure == null) {
            return;
        }
        if (configure.getNavigationIcon() != null) {
            toolbar.setNavigationIcon(configure.getNavigationIcon());
        }
        if (configure.getNavigationIconResId() != NO_RESOURCE_ID) {
            toolbar.setNavigationIcon(configure.getNavigationIconResId());
        }
        if (configure.getLogoResId() != NO_RESOURCE_ID) {
            toolbar.setLogo(configure.getLogoResId());
        }
        if (configure.getLogoDrawable() != null) {
            toolbar.setLogo(configure.getLogoDrawable());
        }
        if (configure.getOnNavigationIconClickListener() != null) {
            toolbar.setNavigationOnClickListener(configure.getOnNavigationIconClickListener());
        }
        //判断是否显示toolbar自身的标题
        boolean isHasCustomTitle = false;
        if (!TextUtils.isEmpty(configure.getTitle())) {
            //因为在onCreate()中修改title的值，都会被重置成android:label的值
            toolbar.setTitle(configure.getTitle());
            if (configure.getTitleTextAppearance() == NO_RESOURCE_ID) {

                if (configure.getTitleColor() != NO_RESOURCE_ID) {
                    toolbar.setTitleTextColor(configure.getTitleColor());
                }

            } else {
                toolbar.setTitleTextAppearance(toolbar.getContext(), configure.getTitleTextAppearance());
            }
            isHasCustomTitle = true;
        }
        if (!TextUtils.isEmpty(configure.getSubTitle())) {
            toolbar.setSubtitle(configure.getSubTitle());
            if (configure.getSubTitleTextAppearance() == NO_RESOURCE_ID) {

                if (configure.getSubTitleColor() != NO_RESOURCE_ID) {
                    toolbar.setSubtitleTextColor(configure.getSubTitleColor());
                }

            } else {
                toolbar.setSubtitleTextAppearance(toolbar.getContext(), configure.getSubTitleTextAppearance());
            }
            isHasCustomTitle = true;
        }

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(configure.isEnableDisplayHomeAsUp());
            //禁止活动条自身的标题显示
            actionBar.setDisplayShowTitleEnabled(!configure.isHideDisplayTitle() || isHasCustomTitle);
        }

        if (configure.getOverflowIcon() != null) {
            toolbar.setOverflowIcon(configure.getOverflowIcon());
        }
        if (configure.isImmerse()) {
            //因为沉侵式布局会让活动条侵入到状态栏中，为了不影响活动条显示内容，
            //让活动条高度增加并且内容下移
            int statusBarHeight = ScreenHelper.getStatusBarHeight(toolbar.getContext());
            toolbar.getLayoutParams().height = toolbar.getLayoutParams().height + statusBarHeight;
            toolbar.setPadding(0, statusBarHeight, 0, 0);
        }
        if (configure.getOnMenuItemClickListener() != null) {
            toolbar.setOnMenuItemClickListener(configure.getOnMenuItemClickListener());
        }
    }

    /**
     * 应用菜单颜色混合
     *
     * @param menu             菜单
     * @param colorFilterColor 颜色混合颜色
     */
    public static void applyMenuColorFilter(@Nullable Menu menu, @ColorInt int colorFilterColor) {
        if (menu == null || colorFilterColor == 0) {
            return;
        }
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem == null) {
                continue;
            }
            Drawable drawable = menuItem.getIcon();
            if (drawable == null) {
                continue;
            }
            //避免其他加载此资源的代码，共用此颜色混合
            drawable.mutate();
            drawable.setColorFilter(new PorterDuffColorFilter(colorFilterColor, PorterDuff.Mode.SRC_IN));
        }
    }

    public static class ToolbarConfigure {

        private final Builder mBuilder;

        public ToolbarConfigure() {
            this(new Builder());
        }

        public ToolbarConfigure(@NonNull Builder builder) {
            mBuilder = builder;
        }

        @DrawableRes
        public int getNavigationIconResId() {
            return mBuilder.navigationIconResId;
        }

        public void setNavigationIconResId(@DrawableRes int navigationIconResId) {
            mBuilder.navigationIconResId = navigationIconResId;
        }

        @Nullable
        public Drawable getNavigationIcon() {
            return mBuilder.navigationIcon;
        }

        public void setNavigationIcon(Drawable navigationIcon) {
            mBuilder.navigationIcon = navigationIcon;
        }

        public boolean isEnableDisplayHomeAsUp() {
            return mBuilder.enableDisplayHomeAsUp;
        }

        public void setEnableDisplayHomeAsUp(boolean enableDisplayHomeAsUp) {
            mBuilder.enableDisplayHomeAsUp = enableDisplayHomeAsUp;
        }

        @DrawableRes
        public int getLogoResId() {
            return mBuilder.logoResId;
        }

        public void setLogoResId(@DrawableRes int logoResId) {
            mBuilder.logoResId = logoResId;
        }

        @Nullable
        public Drawable getLogoDrawable() {
            return mBuilder.logoDrawable;
        }

        public void setLogoDrawable(Drawable logoDrawable) {
            mBuilder.logoDrawable = logoDrawable;
        }

        @Nullable
        public View.OnClickListener getOnNavigationIconClickListener() {
            return mBuilder.onNavigationIconClickListener;
        }

        public void setOnNavigationIconClickListener(@Nullable View.OnClickListener listener) {
            mBuilder.onNavigationIconClickListener = listener;
        }

        public boolean isHideDisplayTitle() {
            return mBuilder.hideDisplayTitle;
        }

        public void setHideDisplayTitle(boolean hideDisplayTitle) {
            mBuilder.hideDisplayTitle = hideDisplayTitle;
        }

        @Nullable
        public String getTitle() {
            return mBuilder.title;
        }

        public void setTitle(@Nullable String title) {
            mBuilder.title = title;
        }

        @ColorInt
        public int getTitleColor() {
            return mBuilder.titleColor;
        }

        public void setTitleColor(@ColorInt int titleColor) {
            mBuilder.titleColor = titleColor;
        }

        @Nullable
        public String getSubTitle() {
            return mBuilder.subTitle;
        }

        public void setSubTitle(@Nullable String subTitle) {
            mBuilder.subTitle = subTitle;
        }

        @ColorInt
        public int getSubTitleColor() {
            return mBuilder.subTitleColor;
        }

        public void setSubTitleColor(@ColorInt int subTitleColor) {
            mBuilder.subTitleColor = subTitleColor;
        }

        @StyleRes
        public int getTitleTextAppearance() {
            return mBuilder.titleTextAppearance;
        }

        public void setTitleTextAppearance(@StyleRes int titleTextAppearance) {
            mBuilder.titleTextAppearance = titleTextAppearance;
        }

        @StyleRes
        public int getSubTitleTextAppearance() {
            return mBuilder.subTitleTextAppearance;
        }

        public void setSubTitleTextAppearance(@StyleRes int subTitleTextAppearance) {
            mBuilder.subTitleTextAppearance = subTitleTextAppearance;
        }

        public boolean isImmerse() {
            return mBuilder.immerse;
        }

        public void setImmerse(boolean immerse) {
            mBuilder.immerse = immerse;
        }

        @Nullable
        public Toolbar.OnMenuItemClickListener getOnMenuItemClickListener() {
            return mBuilder.onMenuItemClickListener;
        }

        public void setOnMenuItemClickListener(@Nullable Toolbar.OnMenuItemClickListener listener) {
            mBuilder.onMenuItemClickListener = listener;
        }

        @MenuRes
        public int getMenuResId() {
            return mBuilder.menuResId;
        }

        public void setMenuResId(@MenuRes int menuResId) {
            mBuilder.menuResId = menuResId;
        }

        @ColorInt
        public int getMenuColorFilterColor() {
            return mBuilder.menuColorFilterColor;
        }

        public void setMenuColorFilterColor(@ColorInt int menuColorFilterColor) {
            mBuilder.menuColorFilterColor = menuColorFilterColor;
        }

        public boolean isClearActivityMenu() {
            return mBuilder.clearActivityMenu;
        }

        public void setClearActivityMenu(boolean clearActivityMenu) {
            mBuilder.clearActivityMenu = clearActivityMenu;
        }

        @Nullable
        public Drawable getOverflowIcon() {
            return mBuilder.overflowIcon;
        }

        public void setOverflowIcon(@Nullable Drawable overflowIcon) {
            mBuilder.overflowIcon = overflowIcon;
        }

        //链式调用
        public static class Builder {

            @DrawableRes
            private int navigationIconResId;//左侧导航图标资源ID

            @Nullable
            private Drawable navigationIcon;//左侧导航图标

            private boolean enableDisplayHomeAsUp;//是否启用默认系统返回键

            @DrawableRes
            private int logoResId;//左侧logo图标资源ID

            @Nullable
            private Drawable logoDrawable;//左侧logo图片

            @Nullable
            private View.OnClickListener onNavigationIconClickListener;//导航图标点击事件

            private boolean hideDisplayTitle;//隐藏系统默认标题

            @Nullable
            private String title;//标题

            @ColorInt
            private int titleColor;//标题文本颜色

            @Nullable
            private String subTitle;//副标题

            @ColorInt
            private int subTitleColor;//副标题文本颜色

            @StyleRes
            private int titleTextAppearance;//标题文本样式

            @StyleRes
            private int subTitleTextAppearance;//副标题文本样式

            private boolean immerse;//标记是否是沉浸式

            @Nullable
            private Toolbar.OnMenuItemClickListener onMenuItemClickListener;//菜单监听器

            @MenuRes
            private int menuResId;//菜单资源ID

            @ColorInt
            private int menuColorFilterColor;//混合覆盖颜色

            private boolean clearActivityMenu;//用于fragment是否清楚activity的toolbar菜单资源

            @Nullable
            private Drawable overflowIcon;//溢出图标

            public Builder() {
                hideDisplayTitle = true;
                menuColorFilterColor = 0;//默认不使用颜色混合
            }

            @NonNull
            public ToolbarConfigure build() {
                return new ToolbarConfigure(this);
            }

            @DrawableRes
            public int getNavigationIconResId() {
                return this.navigationIconResId;
            }

            public Builder setNavigationIconResId(@DrawableRes int navigationIconResId) {
                this.navigationIconResId = navigationIconResId;
                return this;
            }

            @Nullable
            public Drawable getNavigationIcon() {
                return this.navigationIcon;
            }

            public Builder setNavigationIcon(@Nullable Drawable navigationIcon) {
                this.navigationIcon = navigationIcon;
                return this;
            }

            public boolean isEnableDisplayHomeAsUp() {
                return enableDisplayHomeAsUp;
            }

            public Builder setEnableDisplayHomeAsUp(boolean enableDisplayHomeAsUp) {
                this.enableDisplayHomeAsUp = enableDisplayHomeAsUp;
                return this;
            }

            @DrawableRes
            public int getLogoResId() {
                return this.logoResId;
            }

            public Builder setLogoResId(@DrawableRes int logoResId) {
                this.logoResId = logoResId;
                return this;
            }

            @Nullable
            public Drawable getLogoDrawable() {
                return this.logoDrawable;
            }

            public Builder setLogoDrawable(@Nullable Drawable logoDrawable) {
                this.logoDrawable = logoDrawable;
                return this;
            }

            @Nullable
            public View.OnClickListener getOnNavigationIconClickListener() {
                return this.onNavigationIconClickListener;
            }

            public Builder setOnNavigationIconClickListener(@Nullable View.OnClickListener listener) {
                this.onNavigationIconClickListener = listener;
                return this;
            }

            public boolean isHideDisplayTitle() {
                return hideDisplayTitle;
            }

            public Builder setHideDisplayTitle(boolean hideDisplayTitle) {
                this.hideDisplayTitle = hideDisplayTitle;
                return this;
            }

            @Nullable
            public String getTitle() {
                return this.title;
            }

            public Builder setTitle(@Nullable String title) {
                this.title = title;
                return this;
            }

            @ColorInt
            public int getTitleColor() {
                return this.titleColor;
            }

            public Builder setTitleColor(@ColorInt int titleColor) {
                this.titleColor = titleColor;
                return this;
            }

            @Nullable
            public String getSubTitle() {
                return this.subTitle;
            }

            public Builder setSubTitle(@Nullable String subTitle) {
                this.subTitle = subTitle;
                return this;
            }

            @ColorInt
            public int getSubTitleColor() {
                return this.subTitleColor;
            }

            public Builder setSubTitleColor(@ColorInt int subTitleColor) {
                this.subTitleColor = subTitleColor;
                return this;
            }

            @StyleRes
            public int getTitleTextAppearance() {
                return this.titleTextAppearance;
            }

            public Builder setTitleTextAppearance(@StyleRes int titleTextAppearance) {
                this.titleTextAppearance = titleTextAppearance;
                return this;
            }

            @StyleRes
            public int getSubTitleTextAppearance() {
                return this.subTitleTextAppearance;
            }

            public Builder setSubTitleTextAppearance(@StyleRes int subTitleTextAppearance) {
                this.subTitleTextAppearance = subTitleTextAppearance;
                return this;
            }

            public boolean isImmerse() {
                return this.immerse;
            }

            public Builder setImmerse(boolean immerse) {
                this.immerse = immerse;
                return this;
            }

            @Nullable
            public Toolbar.OnMenuItemClickListener getOnMenuItemClickListener() {
                return this.onMenuItemClickListener;
            }

            public Builder setOnMenuItemClickListener(@Nullable Toolbar.OnMenuItemClickListener listener) {
                this.onMenuItemClickListener = listener;
                return this;
            }

            @MenuRes
            public int getMenuResId() {
                return this.menuResId;
            }

            public Builder setMenuResId(@MenuRes int menuResId) {
                this.menuResId = menuResId;
                return this;
            }

            @ColorInt
            public int getMenuColorFilterColor() {
                return menuColorFilterColor;
            }

            public Builder setMenuColorFilterColor(@ColorInt int menuColorFilterColor) {
                this.menuColorFilterColor = menuColorFilterColor;
                return this;
            }

            public boolean isClearActivityMenu() {
                return this.clearActivityMenu;
            }

            public Builder setClearActivityMenu(boolean clearActivityMenu) {
                this.clearActivityMenu = clearActivityMenu;
                return this;
            }

            @Nullable
            public Drawable getOverflowIcon() {
                return overflowIcon;
            }

            public Builder setOverflowIcon(@Nullable Drawable overflowIcon) {
                this.overflowIcon = overflowIcon;
                return this;
            }
        }
    }
}
