package com.mingyuechunqiu.agilemvpframe.util;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/10/19
 *     desc   : 活动条工具类
 *     version: 1.0
 * </pre>
 */
public class ToolbarUtils {

    public static final int NO_RESOURCE_ID = -1;//没有资源id

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
        if (toolbarBean.getIconClickListener() != null) {
            toolbar.setNavigationOnClickListener(toolbarBean.getIconClickListener());
        }
        if (toolbarBean.isHasCustomTitle()) {
            //禁止活动条自身的标题显示
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false);
            }
        } else {
            //因为在onCreate()中修改title的值，都会被重置成android:label的值
            toolbar.setTitle(toolbarBean.getTitle());
            toolbar.setTitleTextColor(toolbarBean.getTitleColor());
            toolbar.setSubtitleTextColor(toolbarBean.getSubTitleColor());
            toolbar.setSubtitle(toolbarBean.getSubTitle());
        }
        if (toolbarBean.isImmerse()) {
            //因为沉侵式布局会让活动条侵入到状态栏中，为了不影响活动条显示内容，
            //让活动条高度增加并且内容下移
            int statusBarHeight = ScreenUtils.getStatusBarHeight(toolbar.getContext());
            toolbar.getLayoutParams().height = toolbar.getLayoutParams().height + statusBarHeight;
            toolbar.setPadding(0, statusBarHeight, 0, 0);
        }
        if (toolbarBean.getListener() != null) {
            toolbar.setOnMenuItemClickListener(toolbarBean.getListener());
        }
    }

    public static class ToolbarBean {

        private int navigationIconResId;//左侧导航图标资源ID

        private Drawable navigationIcon;//左侧导航图标

        private View.OnClickListener iconClickListener;//图标点击事件

        private Drawable drawable;//活动条的logo

        private String title;//标题

        private int titleColor;//标题文本颜色

        private String subTitle;//副标题

        private int subTitleColor;//副标题文本颜色

        private boolean hasCustomTitle;//标记是否是设置自定义文本

        private boolean isImmerse;//标记是否是沉浸式

        private Toolbar.OnMenuItemClickListener listener;//菜单监听器

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

        public View.OnClickListener getIconClickListener() {
            return iconClickListener;
        }

        public void setIconClickListener(View.OnClickListener iconClickListener) {
            this.iconClickListener = iconClickListener;
        }

        public Drawable getDrawable() {
            return drawable;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
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

        public boolean isHasCustomTitle() {
            return hasCustomTitle;
        }

        public void setHasCustomTitle(boolean hasCustomTitle) {
            this.hasCustomTitle = hasCustomTitle;
        }

        public boolean isImmerse() {
            return isImmerse;
        }

        public void setImmerse(boolean immerse) {
            isImmerse = immerse;
        }

        public Toolbar.OnMenuItemClickListener getListener() {
            return listener;
        }

        public void setListener(Toolbar.OnMenuItemClickListener listener) {
            this.listener = listener;
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

            public View.OnClickListener getIconClickListener() {
                return mBean.iconClickListener;
            }

            public Builder setIconClickListener(View.OnClickListener iconClickListener) {
                mBean.iconClickListener = iconClickListener;
                return this;
            }

            public Drawable getDrawable() {
                return mBean.drawable;
            }

            public Builder setDrawable(Drawable drawable) {
                mBean.drawable = drawable;
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

            public boolean isHasCustomTitle() {
                return mBean.hasCustomTitle;
            }

            public Builder setHasCustomTitle(boolean hasCustomTitle) {
                mBean.hasCustomTitle = hasCustomTitle;
                return this;
            }

            public boolean isImmerse() {
                return mBean.isImmerse;
            }

            public Builder setImmerse(boolean immerse) {
                mBean.isImmerse = immerse;
                return this;
            }

            public Toolbar.OnMenuItemClickListener getListener() {
                return mBean.listener;
            }

            public Builder setListener(Toolbar.OnMenuItemClickListener listener) {
                mBean.listener = listener;
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
