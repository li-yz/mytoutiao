package com.jlu;

import com.jlu.dao.LoginTicketDao;
import com.jlu.dao.NewsDao;
import com.jlu.dao.UserDao;
import com.jlu.model.HostHolder;
import com.jlu.model.LoginTicket;
import com.jlu.model.News;
import com.jlu.model.User;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MytoutiaoApplication.class)

public class HostHoldertest {
	@Autowired
	UserDao userDao;

	@Autowired
	NewsDao newsDao;

	@Autowired
	HostHolder hostHolder;

	@Autowired
	LoginTicketDao loginTicketDao;

	@Test
	public void testThreadLocal() {
		for(int i=0;i < 2;i++){
			final int no=i;
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					User user = new User();
					user.setName("user"+no);
					user.setPassword("pwd"+no);
					hostHolder.setUser(user);
					print(Thread.currentThread().getName()+" "+hostHolder.getUser().getName());
				}
			};
			new Thread(runnable).start();
		}
	}

	@Test
	public void testLoginModule(){
		String t="637284d125934055b0ddabc2b3103557";
		LoginTicket ticket = loginTicketDao.selectByTicket(t);
		if(ticket != null)
			print(ticket.getTicket());
//		loginTicketDao.updateStatus(t,1);
	}

	private static void print(Object obj){
		System.out.println("测试结果："+obj);
	}
}
