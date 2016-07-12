package com.jlu.controller;

import com.jlu.model.User;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2016/7/3.
 */
//@Controller
public class IndexController {
    private static final Logger logger= org.slf4j.LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(path = {"/index","/"})
    @ResponseBody
    public String index(HttpSession session){
        logger.info("visit Index");
        return "Hello JLU! "+session.getAttribute("msg")+"<br>";
    }

    @RequestMapping(value="/news")
    public String news(Model model){
        List<String> colors=new ArrayList<String>();
        Map<String,Integer>map=new HashMap<>();

        map.put("first",1);
        map.put("second",2);
        colors.add("red");
        colors.add("green");
        colors.add("blue");
        model.addAttribute("value1",1);
        model.addAttribute("value2",3);
        model.addAttribute("colors",colors);
        model.addAttribute("map",map);

        model.addAttribute("user",new User("Tom"));
        return "news";
    }

    //验证HttpServletRequest对象中的元素
    @RequestMapping("/request")
    @ResponseBody
    public String request(HttpServletRequest request, HttpServletResponse respose, HttpSession session){
        StringBuilder sb=new StringBuilder();
        Enumeration<String> headerNames=request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            sb.append("header name:"+request.getHeader(name).toString() + "<br>");
        }
        for(Cookie cookie : request.getCookies()){
            sb.append("cookie name:"+cookie.getName()+" value:"+cookie.getValue()+"<br>");
        }
        sb.append("getMethod:"+request.getMethod()+"<br>");
        sb.append("getPathInfo:"+request.getPathInfo()+"<br>");
        sb.append("getQueryString:"+request.getQueryString()+"<br>");
        return sb.toString();
    }

    //验证HttpServletResponse有什么方法，向客户端addCookie
    @RequestMapping("/response")
    @ResponseBody
    public String response(@CookieValue(value = "nowcoderId",defaultValue = "a") String nowcoderId
                           ,@CookieValue(value = "JSESSIONID") String JSESSIONID
                           ,@RequestParam(value = "key",defaultValue = "k")String key
                           ,@RequestParam(value = "value",defaultValue = "v")String value
                           , HttpServletResponse response){
        StringBuilder sb=new StringBuilder();
        sb.append("key: "+key+"<br>");
        sb.append("value: "+value+"<br>");
        sb.append("cookie nowcoderId:"+nowcoderId+"<br>");
        sb.append("cookie JSESSIONID:"+JSESSIONID+"<br>");
        response.addCookie(new Cookie(key,value));
        return sb.toString();
    }

    //学习重定向
    @RequestMapping("/redirect/{code}")
    public String redirect(@PathVariable("code")int code
    ,HttpSession session){
        RedirectView redirectView=new RedirectView("/",true);
        /*if(code == 301){
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }else if(code== 302){//302也是默认的转移方式
            redirectView.setStatusCode(HttpStatus.MOVED_TEMPORARILY);
        }*/
        session.setAttribute("msg","Jump From Redirect!");
        //return redirectView;//跳转实现方法一，return 一个redirectView定位到要跳转的页面
        return "redirect:/";//跳转实现方法二，velocity自己解析要跳转到的页面 redirect:/
    }

    //自定义异常信息，配合自定义的ExceptionHandler可以跳转到指定的固定页面
    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key",required = false)String key){
        if("admin".equals(key)){
            return "Hello admin";
        }
        throw new IllegalArgumentException("Key 错误！");
    }

    @ExceptionHandler
    public String error(Exception e,Model model){
        model.addAttribute("error",e.getMessage());
        return "error";
    }
}
