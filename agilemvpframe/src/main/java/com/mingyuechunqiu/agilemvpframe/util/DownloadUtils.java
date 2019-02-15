package com.mingyuechunqiu.agilemvpframe.util;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.mingyuechunqiu.agilemvpframe.agile.AgileMVPFrame;
import com.mingyuechunqiu.agilemvpframe.R;

/**
 * <pre>
 *     author : xyj
 *     e-mail : xiyujieit@163.com
 *     time   : 2018/05/29
 *     desc   : 下载管理工具类
 *     version: 1.0
 * </pre>
 */
public class DownloadUtils {

    public static final int DOWNLOAD_ERROR = -1;//下载请求失败

    //下载apk的mime类型
    private static final String TYPE_APK = "application/vnd.android.package-archive";

    /**
     * 进行文件下载请求
     *
     * @param manager 下载管理器
     * @param url     下载地址
     * @param name    文件存储名称
     * @return 返回下载文件id
     */
    public static long requestDownload(DownloadManager manager, String url, String name) {
        Context context = AgileMVPFrame.getAppContext();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        if (TextUtils.isEmpty(name)) {
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS,
                    AgileMVPFrame.getAppContext().getPackageName() + ".apk");
        } else {
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, name);
        }
        request.setTitle(context.getString(R.string.update_application));
        request.setDescription(context.getString(R.string.installation_package_is_downloading));
        request.setMimeType(TYPE_APK);
        return manager.enqueue(request);
    }

    /**
     * 查询下载数据库指针，并转换成对象
     *
     * @param manager 下载管理器
     * @param id      下载文件id
     * @return 如果解析指针成功，则返回解析对象，否则返回null
     */
    public static DownloadCursorBean queryDownload(DownloadManager manager, long id) {
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
    public static int getProgress(DownloadCursorBean bean) {
        if (bean == null) {
            return DOWNLOAD_ERROR;
        }
        //乘以100时，可能会溢出int的数值范围，导致结果变成负数
        long downloadBytes = bean.getDownloadedBytes();
        long totalBytes = bean.getTotalBytes();
        if (downloadBytes > 0 && totalBytes > 0 && downloadBytes >= totalBytes) {
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
    public static int getProgress(DownloadManager manager, long id) {
        return getProgress(queryDownload(manager, id));
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
