package com.mingyuechunqiu.agilemvpframe.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2017/12/29
 *     desc   : 所有文件相关操作的基类
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFile {

    /**
     * 检查传入的文件名，判断文件名是否为空，或文件是否存在
     * @param fileName
     *          传入的文件名
     * @return
     *          返回判断的结果
     */
    protected boolean checkFile(String fileName){
        if (fileName == null || fileName.equals("")){
            return false;
        }
        File file = new File(fileName);
        if (file.isDirectory()){
            return false;
        }
        if (!file.exists()){
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 关闭流的操作，供子类调用
     * @param stream
     *          需要关闭的流
     */
    protected void closeStream(Closeable stream){
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
