package com.mingyuechunqiu.agile.data.remote.retrofit.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

/**
 * <pre>
 *      author : xyj
 *      e-mail : xiyujieit@163.com
 *      time   : 2019/10/23 9:32
 *      desc   : 所有自定义Retrofit处理响应数据的拦截器基类
 *               实现Interceptor
 *      version: 1.0
</pre> *
 */
abstract class BaseResponseBodyInterceptor : Interceptor {

    protected lateinit var url: String

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        url = request.url().toString()
        val response = chain.proceed(request)
        response.body()?.let { responseBody ->
            val contentLength = responseBody.contentLength()
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            var buffer = source.buffer()

            if ("gzip".equals(response.headers()["Content-Encoding"], ignoreCase = true)) {
                GzipSource(buffer.clone()).use { gzippedResponseBody ->
                    buffer = Buffer()
                    buffer.writeAll(gzippedResponseBody)
                }
            }

            val contentType = responseBody.contentType()
            val charset: Charset =
                    contentType?.charset(UTF_8) ?: UTF_8
            if (contentLength != 0L) {
                return intercept(response, buffer.clone().readString(charset))
            }
        }
        return response
    }

    abstract fun intercept(response: Response, body: String): Response
}
