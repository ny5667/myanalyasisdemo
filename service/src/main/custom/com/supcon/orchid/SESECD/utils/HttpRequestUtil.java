package com.supcon.orchid.SESECD.utils;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;


public class HttpRequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
    // utf-8字符编码
    public static final String CHARSET_UTF_8 = "utf-8";
    // HTTP内容类型。
    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";
    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";
    // HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";
    // 连接管理器
    private static PoolingHttpClientConnectionManager pool;
    // 请求配置
    private static RequestConfig requestConfig;

    static {
        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslConnectionSocket = new SSLConnectionSocketFactory(builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocket).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 将最大连接数增加到200
            pool.setMaxTotal(200);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(2);
            // 根据默认超时限制初始化requestConfig
            int socketTimeout = 10000;
            int connectTimeout = 10000;
            int connectionRequestTimeout = 10000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                    .setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

        }catch (Exception e){
            logger.error(e.getMessage());
        }

        // 设置请求超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000)
                .setConnectionRequestTimeout(50000).build();
    }


    /**
     * 设置连接池管理
     * 设置请求配置
     * 设置重试次数
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(pool)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
        return httpClient;
    }


    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private static String sendHttpPost(HttpPost httpPost) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpPost.setConfig(requestConfig);

            // 执行请求
            response = httpClient.execute(httpPost);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }

            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 发送Get请求
     *
     * @param httpGet
     * @return
     */
    private static String sendHttpGet(HttpGet httpGet) {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            // 得到响应实例
            HttpEntity entity = response.getEntity();

            // 可以获得响应头
            // Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            // for (Header header : headers) {
            // System.out.println(header.getName());
            // }

            // 得到响应类型
            // System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());

            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /**
     * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
     * @param parameterMap     需要转化的键值对集合
     * @return 字符串
     */
    public static String convertStringParam(Map parameterMap) {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }

    /**
     * 发送 post请求
     *
     * @param maps
     *            参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps,String contentType) {
        String param = convertStringParam(maps);
        return sendHttpPost(httpUrl, param,contentType);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param params 参数(格式:key1=value1&key2=value2)
     *
     */
    public static String sendHttpPost(String httpUrl, String params,String contentType) {
        return sendHttpPost(httpUrl,params,null,contentType);
    }

    /**
     *
     * @param httpUrl
     * @param params    参数
     * @param map       请求头
     * @return
     */
    public static String sendHttpPost(String httpUrl, String params,Map<String,String> map,String contentType){
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            if (params != null && params.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(params, "UTF-8");
                stringEntity.setContentType(contentType);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(map != null && !map.isEmpty()){
            for (String key : map.keySet()) {
                httpPost.setHeader(key,map.get(key));
            }
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl
     */
    public static String sendHttpGet(String httpUrl) {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet);
    }
}
