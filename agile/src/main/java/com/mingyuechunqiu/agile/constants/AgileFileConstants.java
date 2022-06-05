package com.mingyuechunqiu.agile.constants;

/**
 * <pre>
 *     author : xyj
 *     Github : https://github.com/MingYueChunQiu
 *     e-mail : xiyujieit@163.com
 *     time   : 2019/3/28
 *     desc   : 文件常量类
 *     version: 1.0
 * </pre>
 */
public interface AgileFileConstants {

    /**
     * 文件后缀格式常量
     */
    interface FileSuffix {

        /**
         * 通用后缀
         */
        interface CommonSuffix {

            String BACK_UP = ".backup";
        }

        /**
         * 图片后缀
         */
        interface PictureSuffix {

            String SUFFIX_PNG = ".png";
            String SUFFIX_JPG = ".jpg";
            String SUFFIX_JPEG = ".jpeg";
            String SUFFIX_WEBP = ".webp";
        }

        /**
         * 音频后缀
         */
        interface AudioSuffix {

            String SUFFIX_MP3 = ".mp3";
        }

        /**
         * 视频后缀
         */
        interface VideoSuffix {

            String SUFFIX_MP4 = ".mp4";
        }

        /**
         * 文档后缀
         */
        interface DocumentSuffix {

            String SUFFIX_TXT = ".txt";
            String SUFFIX_PNG = ".png";
            String SUFFIX_PDF = ".pdf";
        }
    }
}
