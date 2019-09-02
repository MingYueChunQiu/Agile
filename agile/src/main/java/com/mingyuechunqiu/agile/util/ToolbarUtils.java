package com.mingyuechunqiu.agile.util;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import static com.mingyuechunqiu.agile.constants.CommonConstants.NO_RESOURCE_ID;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/10/19
 *     desc   : 活动条工具类
 *     version: 1.0
 * </pre>
 */
public class ToolbarUtils {

    /**
     * 初始化活动条
     *
     * @param toolbar     工具条
     * @param actionBar   活动条
     * @param toolbarBean 工具条信息对象
     */
    public static void initToolbar(Toolbar toolbar, ActionBar actionBar, ToolbarBean toolbarBean) {
        if (toolbar == null || toolbarBean == null) {
            return;
        }
        if (toolbarBean.getNavigationIcon() != null) {
            toolbar.setNavigationIcon(toolbarBean.getNavigationIcon());
        }
        if (toolbarBean.getNavigationIconResId() != NO_RESOURCE_ID) {
            toolbar.setNavigationIcon(toolbarBean.getNavigationIconResId());
        }
        if (toolbarBean.getLogoResId() != NO_RESOURCE_ID) {
            toolbar.setLogo(toolbarBean.getLogoResId());
        }
        if (toolbarBean.getLogoDrawable() != null) {
            toolbar.setLogo(toolbarBean.getLogoDrawable());
        }
        if (toolbarBean.getOnIconClickListener() != null) {
            toolbar.setNavigationOnClickListener(toolbarBean.getOnIconClickListener());
        }
        //判断是否显示toolbar自身的标题
        boolean isHasCustomTitle = true;
        if (!TextUtils.isEmpty(toolbarBean.getTitle())) {
            //因为在onCreate()中修改title的值，都会被重置成android:label的值
            toolbar.setTitle(toolbarBean.getTitle());
            if (toolbarBean.getTitleTextAppearance() == NO_RESOURCE_ID) {

                if (toolbarBean.getTitleColor() != NO_RESOURCE_ID) {
                    toolbar.setTitleTextColor(toolbarBean.getTitleColor());
                }

            } else {
                toolbar.setTitleTextAppearance(toolbar.getContext(), toolbarBean.getTitleTextAppearance());
            }
            isHasCustomTitle = false;
        }
        if (!TextUtils.isEmpty(toolbarBean.getSubTitle())) {
            toolbar.setSubtitle(toolbarBean.getSubTitle());
            if (toolbarBean.getSubTitleTextAppearance() == NO_RESOURCE_ID) {

                if (toolbarBean.getSubTitleColor() != NO_RESOURCE_ID) {
                    toolbar.setSubtitleTextColor(toolbarBean.getSubTitleColor());
                }

            } else {
                toolbar.setSubtitleTextAppearance(toolbar.getContext(), toolbarBean.getSubTitleTextAppearance());
            }
            isHasCustomTitle = false;
        }
        if (isHasCustomTitle) {
            //禁止活动条自身的标题显示
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false);
            }
        }
        if (toolbarBean.isImmerse()) {
            //因为沉侵式布局会让活动条侵入到状态栏中，为了不影响活动条显示内容，
            //让活动条高度增加并且内容下移
            int statusBarHeight = ScreenUtils.getStatusBarHeight(toolbar.getContext());
            toolbar.getLayoutParams().height = toolbar.getLayoutParams().height + statusBarHeight;
            toolbar.setPadding(0, statusBarHeight, 0, 0);
        }
        if (toolbarBean.getOnMenuItemClickListener() != null) {
            toolbar.setOnMenuItemClickListener(toolbarBean.getOnMenuItemClickListener());
        }
    }

    public static class ToolbarBean {

        private int navigationIconResId;//左侧导航图标资源ID

        private Drawable navigationIcon;//左侧导航图标

        private int logoResId;//左侧logo图标资源ID

        private Drawable logoDrawable;//左侧logo图片

        private View.OnClickListener onIconClickListener;//图标点击事件

        private String title;//标题

        private int titleColor;//标题文本颜色

        private String subTitle;//副标题

        private int subTitleColor;//副标题文本颜色

        private @StyleRes
        int titleTextAppearance;//标题文本样式

        private @StyleRes
        int subTitleTextAppearance;//副标题文本样式

        private boolean immerse;//标记是否是沉浸式

        private Toolbar.OnMenuItemClickListener onMenuItemClickListener;//菜单监听器

        private int menuResId;//菜单资源ID

        private boolean clearActivityMenu;//用于fragment是否清楚activity的toolbar菜单资源

        public int getNavigationIconResId() {
            return navigationIconResId;
        }

        public void setNavigationIconResId(int navigationIconResId) {
            this.navigationIconResId = navigationIconResId;
        }

        public Drawable getNavigationIcon() {
            return navigationIcon;
        }

        public void setNavigationIcon(Drawable navigationIcon) {
            this.navigationIcon = navigationIcon;
        }

        public int getLogoResId() {
            return logoResId;
        }

        public void setLogoResId(int logoResId) {
            this.logoResId = logoResId;
        }

        public Drawable getLogoDrawable() {
            return logoDrawable;
        }

        public void setLogoDrawable(Drawable logoDrawable) {
            this.logoDrawable = logoDrawable;
        }

        public View.OnClickListener getOnIconClickListener() {
            return onIconClickListener;
        }

        public void setOnIconClickListener(View.OnClickListener onIconClickListener) {
            this.onIconClickListener = onIconClickListener;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTitleColor() {
            return titleColor;
        }

        public void setTitleColor(int titleColor) {
            this.titleColor = titleColor;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public int getSubTitleColor() {
            return subTitleColor;
        }

        public void setSubTitleColor(int subTitleColor) {
            this.subTitleColor = subTitleColor;
        }

        public int getTitleTextAppearance() {
            return titleTextAppearance;
        }

        public void setTitleTextAppearance(@StyleRes int titleTextAppearance) {
            this.titleTextAppearance = titleTextAppearance;
        }

        public int getSubTitleTextAppearance() {
            return subTitleTextAppearance;
        }

        public void setSubTitleTextAppearance(@StyleRes int subTitleTextAppearance) {
            this.subTitleTextAppearance = subTitleTextAppearance;
        }

        public boolean isImmerse() {
            return immerse;
        }

        public void setImmerse(boolean immerse) {
            this.immerse = immerse;
        }

        public Toolbar.OnMenuItemClickListener getOnMenuItemClickListener() {
            return onMenuItemClickListener;
        }

        public void setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
            this.onMenuItemClickListener = onMenuItemClickListener;
        }

        public int getMenuResId() {
            return menuResId;
        }

        public void setMenuResId(int menuResId) {
            this.menuResId = menuResId;
        }

        public boolean isClearActivityMenu() {
            return clearActivityMenu;
        }

        public void setClearActivityMenu(boolean clearActivityMenu) {
            this.clearActivityMenu = clearActivityMenu;
        }

        //链式调用
        public static class Builder {

            private ToolbarBean mBean;

            public Builder() {
                mBean = new ToolbarBean();
            }

            public ToolbarBean build() {
                return mBean;
            }

            public int getNavigationIconResId() {
                return mBean.navigationIconResId;
            }

            public Builder setNavigationIconResId(int navigationIconResId) {
                mBean.navigationIconResId = navigationIconResId;
                return this;
            }

            public Drawable getNavigationIcon() {
                return mBean.navigationIcon;
            }

            public Builder setNavigationIcon(Drawable navigationIcon) {
                mBean.navigationIcon = navigationIcon;
                return this;
            }

            public int getLogoResId() {
                return mBean.logoResId;
            }

            public Builder setLogoResId(int logoResId) {
                mBean.logoResId = logoResId;
                return this;
            }

            public Drawable getLogoDrawable() {
                return mBean.logoDrawable;
            }

            public Builder setLogoDrawable(Drawable logoDrawable) {
                mBean.logoDrawable = logoDrawable;
                return this;
            }

            public View.OnClickListener getOnIconClickListener() {
                return mBean.onIconClickListener;
            }

            public Builder setOnIconClickListener(View.OnClickListener iconClickListener) {
                mBean.onIconClickListener = iconClickListener;
                return this;
            }

            public String getTitle() {
                return mBean.title;
            }

            public Builder setTitle(String title) {
                mBean.title = title;
                return this;
            }

            public int getTitleColor() {
                return mBean.titleColor;
            }

            public Builder setTitleColor(int titleColor) {
                mBean.titleColor = titleColor;
                return this;
            }

            public String getSubTitle() {
                return mBean.subTitle;
            }

            public Builder setSubTitle(String subTitle) {
                mBean.subTitle = subTitle;
                return this;
            }

            public int getSubTitleColor() {
                return mBean.subTitleColor;
            }

            public Builder setSubTitleColor(int subTitleColor) {
                mBean.subTitleColor = subTitleColor;
                return this;
            }

            public int getTitleTextAppearance() {
                return mBean.titleTextAppearance;
            }

            public Builder setTitleTextAppearance(@StyleRes int titleTextAppearance) {
                mBean.titleTextAppearance = titleTextAppearance;
                return this;
            }

            public int getSubTitleTextAppearance() {
                return mBean.subTitleTextAppearance;
            }

            public Builder setSubTitleTextAppearance(@StyleRes int subTitleTextAppearance) {
                mBean.subTitleTextAppearance = subTitleTextAppearance;
                return this;
            }

            public boolean isImmerse() {
                return mBean.immerse;
            }

            public Builder setImmerse(boolean immerse) {
                mBean.immerse = immerse;
                return this;
            }

            public Toolbar.OnMenuItemClickListener getOnMenuItemClickListener() {
                return mBean.onMenuItemClickListener;
            }

            public Builder setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener listener) {
                mBean.onMenuItemClickListener = listener;
                return this;
            }

            public int getMenuResId() {
                return mBean.menuResId;
            }

            public Builder setMenuResId(int menuResId) {
                mBean.menuResId = menuResId;
                return this;
            }

            public boolean isClearActivityMenu() {
                return mBean.clearActivityMenu;
            }

            public Builder setClearActivityMenu(boolean clearActivityMenu) {
                mBean.clearActivityMenu = clearActivityMenu;
                return this;
            }
        }
    }
}
