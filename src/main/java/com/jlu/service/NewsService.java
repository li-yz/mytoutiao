package com.jlu.service;

import com.jlu.dao.NewsDao;
import com.jlu.model.News;
import com.jlu.utils.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/5.
 */
@Service
public class NewsService {
    @Autowired
    private NewsDao newsDao;

    public List<News> getNewsByUserIdAndOffset(int userId,int offset,int limit){
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }

    public String saveImage(MultipartFile file) throws IOException{
        int doPos=file.getOriginalFilename().lastIndexOf(".");
        String fileExt=file.getOriginalFilename().substring(doPos+1).toLowerCase();
        if(!ToutiaoUtil.isFileAllowed(fileExt)){//先判断不符合，退出
            return null;
        }

        String fileName = UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;
        Files.copy(file.getInputStream(),new File(ToutiaoUtil.IMAGE_DIR+fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
        String url="";
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }
}
