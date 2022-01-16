package com.mingyuechunqiu.agile.feature.helper.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.feature.helper.ui.hint.ToastHelper;

/**
 * <pre>
 *     author : 明月春秋
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/12
 *     desc   : 对话框工具类
 *     version: 1.0
 * </pre>
 */
public final class DialogHelper {

    private DialogHelper() {
    }

    /**
     * 显示警示对话框
     *
     * @param context          上下文
     * @param title            标题
     * @param message          内容
     * @param positiveListener 确认按钮监听器
     * @param negativeListener 取消按钮监听器
     */
    public static void showAlertDialog(@NonNull Context context, @NonNull String title, @Nullable String message,
                                       @NonNull DialogInterface.OnClickListener positiveListener,
                                       @Nullable DialogInterface.OnClickListener negativeListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.agile_confirm, positiveListener)
                .setNegativeButton(R.string.agile_cancel, negativeListener)
                .create()
                .show();
    }

    /**
     * 获取加载对话框
     *
     * @param context    上下文
     * @param hint       加载提示文字
     * @param cancelable 点击界面对话框是否消失
     * @return 返回生成的对话框
     */
    public static Dialog getLoadingDialog(@NonNull Context context, @Nullable String hint, boolean cancelable) {
        if (TextUtils.isEmpty(hint)) {
            hint = context.getString(R.string.agile_prompt_loading);
        }
        View view = View.inflate(context, R.layout.agile_dialog_fragment_status_view, null);
        AppCompatTextView tvMsg = view.findViewById(R.id.tv_agile_dfg_status_view_content);
        tvMsg.setText(hint);
        return new AlertDialog.Builder(context, R.style.DialogLoading)
                .setView(view)
                .setCancelable(cancelable)
                .create();
    }

    /**
     * 让对话框消失
     *
     * @param dialog 对话框
     */
    public static void disappearDialog(@Nullable Dialog dialog) {
        if (dialog == null) {
            return;
        }
        dialog.dismiss();
    }

    /**
     * 显示权限设置对话框
     *
     * @param context 上下文
     */
    public static void showSetPermissionsDialog(@Nullable Context context, @StringRes int rationaleResId) {
        if (context == null) {
            return;
        }
        new AlertDialog.Builder(context)
                .setTitle(R.string.agile_set_permission)
                .setMessage(rationaleResId)
                .setPositiveButton(R.string.agile_set, (dialog, which) -> startDetailSettingsActivity(context))
                .setNegativeButton(R.string.agile_cancel, (dialog, which) -> {
                })
                .create()
                .show();
    }

    private static void startDetailSettingsActivity(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.fromParts("package", context.getPackageName(), null));
        if (context.getPackageManager().resolveActivity(intent, 0) != null) {
            context.startActivity(intent);
        } else {
            ToastHelper.showToast(R.string.agile_error_not_found_app_permissions_page);
        }
    }
}
