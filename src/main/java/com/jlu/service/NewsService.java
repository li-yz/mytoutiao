package com.jlu.service;

import com.jlu.dao.NewsDao;
import com.jlu.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
