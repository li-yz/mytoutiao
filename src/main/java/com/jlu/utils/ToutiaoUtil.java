package com.jlu.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/6.
 *
 * 自定义的，业务无关的工具类
 * 菜鸟注：工具类的方法都做成static的，这样就不需要new一个对象来调用这个方法了，节省内存（时刻有此意识）。
 */
public class ToutiaoUtil {
    private static final Logger logger = LoggerFactory.getLogger(ToutiaoUtil.class);

    /* MD5加密算法，自定义成工具类的一个方法
    * */
    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }

    //生成JSON串，JSON串是：{"code":0,"msg":"成功"..}这样格式的键值对字符串，方便前台页面解析
    //我在pom中引入的是阿里巴巴的fastjson这个JSON工具包，使用这个来组成json串很方便
    public static String getJSONString(int code){
        JSONObject json = new JSONObject();
        json.put("code",code);

        return json.toJSONString();
    }
    public static String getJSONString(int code, Map<String,Object>map){
        JSONObject json = new JSONObject();
        json.put("code",code);
        for(Map.Entry entry : map.entrySet()){
            json.put((String) entry.getKey(),entry.getValue());
        }

        return json.toJSONString();
    }
    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }
}
