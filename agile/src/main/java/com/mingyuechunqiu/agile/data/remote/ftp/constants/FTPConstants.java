package com.mingyuechunqiu.agile.data.remote.ftp.constants;

/**
 * <pre>
 *       Project:    Agile
 *       author :    MingYueChunQiu
 *       Github :    <a href="https://github.com/MingYueChunQiu">仓库地址</a>
 *       e-mail :    xiyujieit@163.com
 *       Time:       2019/10/25 14:16
 *       Desc:       FTP常量类
 *       Version:    1.0
 * </pre>
 */
public class FTPConstants {

    public static final int DEFAULT_FTP_CONNECT_TIMEOUT = 15 * 1000;//默认连接超时时间（毫秒）
    public static final int DEFAULT_FTP_DATA_TIMEOUT = 15 * 1000;//默认数据超时时间（毫秒）

    public static final String PATH_CURRENT_DIRECTORY = ".";//当前目录
    public static final String PATH_PREVIOUS_DIRECTORY = "..";//上一级目录
}
