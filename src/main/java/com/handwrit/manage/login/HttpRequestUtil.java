package com.handwrit.manage.login;

import com.sun.deploy.net.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
public class HttpRequestUtil {

    /**
     * post请求
     * @param url         url地址
     * @return
     */
    public static String httpPost(String url){
        //post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost method = new HttpPost(url);
        String str = "";
        try {
            HttpResponse result = (HttpResponse) httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusCode() == 200) {
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    InputStream inputStream = result.getInputStream();
                    str = stream2String(inputStream);
//                    str = EntityUtils.toString(,"UTF-8");

                } catch (Exception e) {
//                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
//            logger.error("post请求提交失败:" + url, e);
        }
        return str;
    }


    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static String httpGet(String url){
        //get请求返回结果
        String strResult = null;
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = (HttpResponse) httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                InputStream inputStream = response.getInputStream();
                strResult = stream2String(inputStream);
                /**读取服务器返回过来的json字符串数据**/
//                strResult = EntityUtils.toString(response.getInputStream(),"UTF-8");
            } else {
//                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
//            logger.error("get请求提交失败:" + url, e);
        }
        return strResult;
    }
    public static String stream2String(InputStream is) {
        // TODO Auto-generated method stub
        //在读取的过程中，将读取的内容存储在缓存中，然后一次性的转化成字符串返回
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //读流
        byte[] buffer = new byte[1024];
        int temp = -1;
        try {
            while ((temp = is.read(buffer)) != -1) {
                bos.write(buffer, 0, temp);
            }
            return bos.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;

    }
}
