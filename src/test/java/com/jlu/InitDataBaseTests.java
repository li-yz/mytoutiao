package com.jlu;

import com.jlu.dao.NewsDao;
import com.jlu.dao.UserDao;
import com.jlu.model.News;
import com.jlu.model.User;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MytoutiaoApplication.class)
@Sql("/init-schema.sql")
public class InitDataBaseTests {
	@Autowired
	UserDao userDao;

	@Autowired
	NewsDao newsDao;

	@Test
	public void initData() {
		Random random=new Random();
		for(int i=0;i < 10;i++){
			User user = new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			user.setName(String.format("USER%d", i+1));
			user.setPassword("");
			user.setSalt("");
			userDao.addUser(user);

			News news = new News();
			news.setCommentCount(i);
			Date date = new Date();
			date.setTime(date.getTime() + 1000*3600*5*i);
			news.setCreatedDate(date);
			news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
			news.setLikeCount(i+1);
			news.setUserId(i+1);
			news.setTitle(String.format("TITLE{%d}", i));
			news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
			newsDao.addNews(news);

			user.setPassword("123456");
			userDao.update(user);
		}
		userDao.deleteById(1);
		User user = userDao.selectUserById(2);
		user.setPassword("0000111");
		userDao.update(user);

		Assert.assertEquals("0000111",userDao.selectUserById(2).getPassword());
		Assert.assertNull(userDao.selectUserById(1));
		//String name = userDao.selectUserByNameAndUserId(2,"USER2").getName();//测试使用dao中的@param参数传递多个查询参数
		//System.out.println("结果："+name);

		List<News> list=newsDao.selectByUserIdAndOffset(1,0,2);
		print("用户发布的新闻条数"+list.size());
		newsDao.updateNews(10,100);
		News news = newsDao.getNewsById(10);
		print("测试newsdao的select与update;"+news.getCommentCount());
	}

	private static void print(Object obj){
		System.out.println("测试结果："+obj);
	}
}
