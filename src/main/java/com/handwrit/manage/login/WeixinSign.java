package com.handwrit.manage.login;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WeixinSign {
//    https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx139f5cf23e08bcb4&secret=80e4c609ab39f87da8174efc7408895e
    /**
     * 网页
     */
    public static String wy_appid = "wx79896f24508951b9";
    public static String wy_secret = "85a6c0d7fdb86f59c386dc99b8a5f3ec";

    public static JSONObject getAccessToken(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
        String params = "appid="+wy_appid+"&secret="+wy_secret+"&code="+code+"&grant_type=authorization_code";
        String result = HttpRequestUtil.httpGet(url + params);
        JSONObject data = JSON.parseObject(result);

        return data;
    }

    public static JSONObject getValidateData(String access_token,String openid){
        String url = "https://api.weixin.qq.com/sns/auth?access_token=" + access_token + "&openid=" + openid;
        String result = HttpRequestUtil.httpGet(url);
        JSONObject data = JSON.parseObject(result);

        return data;
    }

    public static JSONObject getRefreshToken(String refresh_token){
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + wy_appid + "&grant_type=refresh_token&refresh_token=" + refresh_token;
        String result = HttpRequestUtil.httpGet(url);
        JSONObject data = JSON.parseObject(result);

        return data;
    }

    public static JSONObject getUserInfo(String access_token,String openid){
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        String result = HttpRequestUtil.httpGet(url);
        JSONObject data = JSON.parseObject(result);

        return data;
    }
}
