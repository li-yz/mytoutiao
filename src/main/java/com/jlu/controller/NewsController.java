package com.jlu.controller;

import com.jlu.model.HostHolder;
import com.jlu.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Administrator on 2016/7/11.
 */
@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    HostHolder hostHolder;


}
