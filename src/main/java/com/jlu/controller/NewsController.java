package com.jlu.controller;

import com.jlu.model.HostHolder;
import com.jlu.service.NewsService;
import com.jlu.utils.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/7/11.
 */
@Controller
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    HostHolder hostHolder;

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @RequestMapping(path = {"/uploadimage/"},method = {RequestMethod.POST})
    @ResponseBody
    public String home(@RequestParam("file") MultipartFile file){
        try{
            String fileUrl=newsService.saveImage(file);
            //file.transferTo();了解下这是做什么的
            if (fileUrl == null){
                return ToutiaoUtil.getJSONString(1,"上传图片失败");
            }
            return ToutiaoUtil.getJSONString(0,fileUrl);
        }catch (Exception e){
            logger.error("上传图片失败"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"上传图片失败");
        }
    }
}
