package com.jlu.controller;

import com.jlu.model.HostHolder;
import com.jlu.model.News;
import com.jlu.model.ViewObject;
import com.jlu.service.NewsService;
import com.jlu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;

    @Autowired
    HostHolder hostHolder;//把拦截器获取到的用户信息共享给所有类，方便做一些判断

    @RequestMapping(path = {"/home","/"},method = {RequestMethod.GET,RequestMethod.POST})
    public String home(Model model){

        model.addAttribute("vos",getNews(0,0,10));
        //model.addAttribute("date",new DateTool());
        return "home";
    }

    private List<ViewObject> getNews(int userId, int offset, int limit){
        List<News> newsList=newsService.getNewsByUserIdAndOffset(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<ViewObject>();
        for(News news : newsList){
            ViewObject vo = new ViewObject();
            vo.set("news",news);
            vo.set("user",userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/setting"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String setting(Model model){

        //model.addAttribute("date",new DateTool());
        return "setting page ,only login users can see this!!";
    }

}
