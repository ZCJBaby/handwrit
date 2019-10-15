package com.handwrit.manage.utils.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

public class RequestUtil
{
  public static void commonOut(HttpServletResponse response, String msg)
    throws IOException
  {
    response.setContentType("text/plain");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = null;
    out = response.getWriter();
    out.print(msg);
    out.flush();
    out.close();
  }

  public static JSONObject transitionJSONObject(HttpServletRequest request)
  {
    try
    {
      if ((request.getAttribute("body") == null) || (((String)request.getAttribute("body")).isEmpty()))
      {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
          sb.append(line);
        }
        request.setAttribute("body", sb.toString());
      }
      String body = (String)request.getAttribute("body");
      if ((!StringUtils.isEmpty(body)) && 
        (!StringUtils.isEmpty(body.toString()))) {
        return JSON.parseObject(body.toString());
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return new JSONObject();
  }
  
  public static int getIntParameter(Map<String, Object> map, String paramName, Integer defaultValue)
  {
    try
    {
      if (map.containsKey(paramName))
      {
        String temp = map.get(paramName).toString().trim();
        if (!temp.trim().equals("")) {
          return Integer.parseInt(temp);
        }
      }
      if (defaultValue == null) {
        return 0;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return defaultValue.intValue();
  }
  
  public static String getStringParameter(Map<String, Object> map, String paramName, String defaultValue)
  {
    if (map.containsKey(paramName))
    {
      String temp = map.get(paramName).toString().trim();
      if (!temp.trim().equals("")) {
        return xssEncode(temp);
      }
    }
    return defaultValue;
  }
  
  private static String xssEncode(String s)
  {
    if ((s == null) || (s.isEmpty())) {
      return s;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++)
    {
      char c = s.charAt(i);
      switch (c)
      {
      case '\'': 
        break;
      default: 
        sb.append(c);
      }
    }
    return sb.toString();
  }
  
  public static long getLongParameter(Map<String, Object> map, String paramName, Long defaultValue)
  {
    try
    {
      if (map.containsKey(paramName))
      {
        String temp = map.get(paramName).toString().trim();
        if (!temp.equals("")) {
          return Long.parseLong(temp);
        }
      }
      if (defaultValue == null) {
        return 0L;
      }
    }
    catch (Exception localException) {}
    return defaultValue.longValue();
  }
  
  public static float getFloatParameter(Map<String, Object> map, String paramName, Float defaultValue)
  {
    try
    {
      if (map.containsKey(paramName))
      {
        String temp = map.get(paramName).toString().trim();
        if (!temp.equals("")) {
          return Float.parseFloat(temp);
        }
      }
      if (defaultValue == null) {
        return 0.0F;
      }
    }
    catch (Exception localException) {}
    return defaultValue.floatValue();
  }
  
  public static double getDoubleParameter(Map<String, Object> map, String paramName, Double defaultValue)
  {
    try
    {
      if (map.containsKey(paramName))
      {
        String temp = map.get(paramName).toString().trim();
        if (!temp.equals("")) {
          return Double.parseDouble(temp);
        }
      }
      if (defaultValue == null) {
        return 0.0D;
      }
    }
    catch (Exception localException) {}
    return defaultValue.doubleValue();
  }
  
  public static int getIntParameter(HttpServletRequest request, String paramName, Integer defaultValue)
  {
    try
    {
      String temp = request.getParameter(paramName);
      if ((temp != null) && (!temp.trim().equalsIgnoreCase(""))) {
        return Integer.parseInt(temp);
      }
      if (defaultValue == null) {
        return 0;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return defaultValue.intValue();
  }
  
  public static String getStringParameter(HttpServletRequest request, String paramName, String defaultValue)
  {
    if ((request.getParameter(paramName) != null) && (!request.getParameter(paramName).trim().equalsIgnoreCase(""))) {
      return xssEncode(request.getParameter(paramName));
    }
    return defaultValue;
  }
  
  public static long getLongParameter(HttpServletRequest request, String paramName, Long defaultValue)
  {
    try
    {
      String temp = "";
      if ((request.getParameter(paramName) != null) && (!request.getParameter(paramName).trim().equalsIgnoreCase(""))) {
        return Long.parseLong(request.getParameter(paramName).trim());
      }
      if (defaultValue == null) {
        return 0L;
      }
    }
    catch (Exception localException) {}
    return defaultValue.longValue();
  }
  
  public static float getFloatParameter(HttpServletRequest request, String paramName, Float defaultValue)
  {
    try
    {
      String temp = "";
      if ((request.getParameter(paramName) != null) && (!request.getParameter(paramName).trim().equalsIgnoreCase(""))) {
        return Float.parseFloat(request.getParameter(paramName));
      }
      if (defaultValue == null) {
        return 0.0F;
      }
    }
    catch (Exception localException) {}
    return defaultValue.floatValue();
  }
  
  public static String[] getParameterValues(HttpServletRequest request, String paramName, String[] arr)
  {
    String[] tmp = request.getParameterValues(paramName);
    if (tmp == null) {
      if (arr == null) {
        tmp = new String[0];
      } else {
        tmp = arr;
      }
    }
    return tmp;
  }
  
  public static String getIpAddr(HttpServletRequest request)
  {
    String ip = request.getHeader("X-Forwarded-For");
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("X-real-ip");
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getRemoteAddr();
    }
    if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
      ip = request.getHeader("Host");
    }
    if ((ip != null) && (ip.indexOf(",") > -1)) {
      ip = ip.split(",")[1];
    }
    return ip;
  }
  
  public static Map<String, Object> getWeatherData(String ip)
    throws Exception
  {
    String cityUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
    Map<String, Object> cityData = getData(cityUrl);
    
    cityData = (Map)cityData.get("data");
    
    String regionId = (String)cityData.get("region_id");
    
    String searchArea = StringUtils.isEmpty(regionId) ? (String)cityData.get("city_id") : regionId;
    String url = "https://restapi.amap.com/v3/weather/weatherInfo?key=acf9ec011bc29f3ea6f58c34a1fbbe25&city=" + searchArea;
    Map<String, Object> weatherData = getData(url);
    return weatherData;
  }
  
  public static Map<String, Object> getData(String urlString)
    throws Exception
  {
    String res = "";
    BufferedReader reader = null;
    try
    {
      URL url = new URL(urlString);
      URLConnection conn = url.openConnection();
      reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      String line;
      while ((line = reader.readLine()) != null) {
        res = res + line;
      }
    }
    catch (Exception e)
    {
      e.getMessage();
    }
    finally
    {
      if (null != reader) {
        reader.close();
      }
    }
    return (Map)JsonUtil.jsonToObject(res, Map.class);
  }
  
  public static String getReqReferer(HttpServletRequest request)
  {
    String referer = request.getHeader("Referer");
    try
    {
      URL url = new URL(referer);
      return url.getPath();
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
    return "";
  }
}
