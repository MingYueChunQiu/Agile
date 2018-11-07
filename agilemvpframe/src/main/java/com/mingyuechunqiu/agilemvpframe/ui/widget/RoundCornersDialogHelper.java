package com.mingyuechunqiu.agilemvpframe.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mingyuechunqiu.agilemvpframe.R;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/9/26
 *     desc   : 圆角对话框帮助类
 *     version: 1.0
 * </pre>
 */
public class RoundCornersDialogHelper implements View.OnClickListener {

    private Context context;
    private String title;
    private String content;
    private String leftButton;
    private String rightButton;
    private OnClickButtonListener listener;

    public RoundCornersDialogHelper(Context context) {
        this.context = context;
    }

    public Dialog getDialog() {
        if (context == null) {
            return null;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_round_corners_loading, null);
        setViewText(view, title, R.id.tv_dialog_round_corners_loading_title);
        setViewText(view, content, R.id.tv_dialog_round_corners_loading_content);
        setViewText(view, leftButton, R.id.btn_dialog_round_corners_left);
        setViewText(view, rightButton, R.id.btn_dialog_round_corners_right);
        AppCompatButton acbtnLeft = view.findViewById(R.id.btn_dialog_round_corners_left);
        AppCompatButton acbtnRight = view.findViewById(R.id.btn_dialog_round_corners_right);
        acbtnLeft.setOnClickListener(this);
        acbtnRight.setOnClickListener(this);
        return new AlertDialog.Builder(context, R.style.Dialog_Loading_Transparent_Animation)
                .setView(view).create();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLeftButton() {
        return leftButton;
    }

    public void setLeftButton(String leftButton) {
        this.leftButton = leftButton;
    }

    public String getRightButton() {
        return rightButton;
    }

    public void setRightButton(String rightButton) {
        this.rightButton = rightButton;
    }

    public OnClickButtonListener getOnClickButtonListener() {
        return listener;
    }

    public void setOnClickButtonListener(OnClickButtonListener listener) {
        this.listener = listener;
    }

    /**
     * 设置view的文本
     *
     * @param view      文本控件
     * @param text      文本
     * @param viewResId 控件资源ID
     */
    private void setViewText(View view, String text, int viewResId) {
        if (!TextUtils.isEmpty(text)) {
            TextView textView = view.findViewById(viewResId);
            textView.setText(text);
        }
    }

    @Override
    public void onClick(View v) {
        //在library中不能使用switch来判断id，因为它们不被当成常量
        if (v.getId() == R.id.btn_dialog_round_corners_left) {
            if (listener != null) {
                listener.onClickLeftButton(this, v);
            }
        } else if (v.getId() == R.id.btn_dialog_round_corners_right) {
            if (listener != null) {
                listener.onClickRightButton(this, v);
            }
        }
    }

    public static class Builder {

        private RoundCornersDialogHelper mHelper;

        public Builder(Context context) {
            mHelper = new RoundCornersDialogHelper(context);
        }

        public RoundCornersDialogHelper build() {
            return mHelper;
        }

        public Context getContext() {
            return mHelper.context;
        }

        public Builder setContext(Context context) {
            mHelper.context = context;
            return this;
        }

        public String getTitle() {
            return mHelper.title;
        }

        public Builder setTitle(String title) {
            mHelper.title = title;
            return this;
        }

        public String getContent() {
            return mHelper.content;
        }

        public Builder setContent(String content) {
            mHelper.content = content;
            return this;
        }

        public String getLeftButton() {
            return mHelper.leftButton;
        }

        public Builder setLeftButton(String leftButton) {
            mHelper.leftButton = leftButton;
            return this;
        }

        public String getRightButton() {
            return mHelper.rightButton;
        }

        public Builder setRightButton(String rightButton) {
            mHelper.rightButton = rightButton;
            return this;
        }

        public OnClickButtonListener getOnClickButtonListener() {
            return mHelper.listener;
        }

        public Builder setOnClickButtonListener(OnClickButtonListener listener) {
            mHelper.listener = listener;
            return this;
        }
    }

    public interface OnClickButtonListener {

        void onClickLeftButton(RoundCornersDialogHelper helper, View view);

        void onClickRightButton(RoundCornersDialogHelper helper, View view);
    }
}
