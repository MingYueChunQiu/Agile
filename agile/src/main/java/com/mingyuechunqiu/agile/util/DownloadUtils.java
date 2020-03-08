package com.mingyuechunqiu.agile.util;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.mingyuechunqiu.agile.R;
import com.mingyuechunqiu.agile.frame.Agile;

import java.io.File;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/29
 *     desc   : 下载管理工具类
 *     version: 1.0
 * </pre>
 */
public final class DownloadUtils {

    public static final int DOWNLOAD_ERROR = -1;//下载请求失败

    //下载apk的mime类型
    private static final String TYPE_APK = "application/vnd.android.package-archive";

    private DownloadUtils() {
    }

    /**
     * 进行文件下载请求
     *
     * @param manager 下载管理器
     * @param url     下载地址
     * @param name    文件存储名称
     * @return 返回下载文件id
     */
    public static long requestDownload(@Nullable DownloadManager manager, @Nullable String url, @Nullable String name) {
        if (manager == null || TextUtils.isEmpty(url)) {
            return DOWNLOAD_ERROR;
        }
        Context context = Agile.getAppContext();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        String fileName = name;
        if (TextUtils.isEmpty(fileName)) {
            fileName = Agile.getAppContext().getPackageName() + ".apk";
        }
        File destFile = null;//存储的目的地址文件
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File parentFile = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            if (parentFile != null) {
                File file = new File(parentFile.getAbsolutePath() + File.separator + "Apk");
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        return DOWNLOAD_ERROR;
                    }
                }
                destFile = new File(file.getAbsolutePath() + File.separator + fileName);
            }
        }
        if (destFile == null) {
            destFile = new File(context.getCacheDir().getAbsolutePath() + File.separator + fileName);
        }
        if (!deleteResidualOldFile(destFile)) {
            return DOWNLOAD_ERROR;
        }
        request.setDestinationUri(Uri.fromFile(destFile))
                .setTitle(context.getString(R.string.agile_update_application))
                .setDescription(context.getString(R.string.agile_installation_package_is_downloading))
                .setMimeType(TYPE_APK);
        return manager.enqueue(request);
    }

    /**
     * 查询下载数据库指针，并转换成对象
     *
     * @param manager 下载管理器
     * @param id      下载文件id
     * @return 如果解析指针成功，则返回解析对象，否则返回null
     */
    public static DownloadCursorBean queryDownload(@Nullable DownloadManager manager, long id) {
        if (manager == null || id == DOWNLOAD_ERROR) {
            return null;
        }
        DownloadManager.Query query = new DownloadManager.Query();
        Cursor cursor = manager.query(query.setFilterById(id));
        if (cursor != null && cursor.moveToFirst()) {
            DownloadCursorBean bean = new DownloadCursorBean();
            bean.setAddress(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
            bean.setDownloadedBytes(cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
            bean.setTotalBytes((cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))));
            bean.setTitle(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE)));
            bean.setDescription(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION)));
            bean.setUrl(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI)));
            cursor.close();
            return bean;
        }
        return null;
    }

    /**
     * 获取下载进度百分比
     *
     * @param bean 存储指针解析的对象
     * @return 返回百分比进度
     */
    public static int getProgress(@Nullable DownloadCursorBean bean) {
        if (bean == null) {
            return DOWNLOAD_ERROR;
        }
        //乘以100时，可能会溢出int的数值范围，导致结果变成负数
        long downloadBytes = bean.getDownloadedBytes();
        long totalBytes = bean.getTotalBytes();
        if (totalBytes > 0 && downloadBytes >= totalBytes) {
            return 100;
        } else {
            return (int) (downloadBytes * 100 / totalBytes);
        }
    }

    /**
     * 获取下载进度百分比
     *
     * @param manager 下载管理器
     * @param id      下载文件id
     * @return 返回百分比进度
     */
    public static int getProgress(@Nullable DownloadManager manager, long id) {
        return getProgress(queryDownload(manager, id));
    }

    /**
     * 获取下载的apk包所在文件夹
     *
     * @return 返回文件夹所在绝对路径字符串
     */
    @Nullable
    public static String getDownloadApkDirectory() {
        File parentFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            parentFile = Agile.getAppContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        }
        if (parentFile == null) {
            parentFile = Agile.getAppContext().getFilesDir();
        }
        if (parentFile != null) {
            return parentFile.getAbsolutePath() + File.separator + "Apk";
        }
        return null;
    }

    /**
     * 删除残留的旧文件
     *
     * @param file 存储目的文件的父文件
     * @return 如果删除成功返回true，否则返回false
     */
    private static boolean deleteResidualOldFile(@Nullable File file) {
        if (file != null && file.exists() && !file.delete()) {
            ToastUtils.showToast("删除残留旧版本apk失败！");
            return false;
        }
        return true;
    }

    /**
     * 下载指针解析对象
     */
    public static class DownloadCursorBean {
        private String address;//下载文件的本地目录
        private int downloadedBytes;//已下载字节数
        private int totalBytes;//总共要下载字节数
        private String title;//Notification的标题
        private String description;//Notification的描述
        private long id;//下载id
        private String url;//下载文件URI

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDownloadedBytes() {
            return downloadedBytes;
        }

        public void setDownloadedBytes(int downloadedBytes) {
            this.downloadedBytes = downloadedBytes;
        }

        public int getTotalBytes() {
            return totalBytes;
        }

        public void setTotalBytes(int totalBytes) {
            this.totalBytes = totalBytes;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
