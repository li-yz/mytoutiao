package com.jlu.service;

import com.jlu.dao.LoginTicketDao;
import com.jlu.dao.UserDao;
import com.jlu.model.LoginTicket;
import com.jlu.model.User;
import com.jlu.utils.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2016/7/5.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    public User getUser(int id){
        return userDao.selectUserById(id);
    }

    public Map<String,Object> register(String username, String password){
        Map<String,Object> map=new HashMap<String,Object>();
        Random random=new Random();
        //在后台进行注册验证，验证用户名和密码，虽然前台可以通过js来验证，
        // 但是为了安全起见还是要在后台验证，以防恶意的通过方法而非浏览器直接访问
        //暂时只加了这两个验证项，可以在此添加更多的验证项，验证用户名的格式、长度、特殊字符等,密码强度
        if(StringUtils.isBlank(username)){
            map.put("msgnm","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }

        User user=userDao.selectUserByUsername(username);
        if(user != null){
            map.put("msgusr","用户名已存在");
            return map;
        }
        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dm.png",random.nextInt()));
        user.setPassword(ToutiaoUtil.MD5(password+user.getSalt()));

        userDao.addUser(user);
        map.put("msg","注册成功");

        //注册以后直接登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public Map<String,Object> login(String username, String password ){
        Map<String,Object> map=new HashMap<String,Object>();
        Random random=new Random();
        //在后台进行注册验证，验证用户名和密码，虽然前台可以通过js来验证，
        // 但是为了安全起见还是要在后台验证，以防恶意的通过方法而非浏览器直接访问
        //暂时只加了这两个验证项，可以在此添加更多的验证项，验证用户名的格式、长度、特殊字符等,密码强度
        if(StringUtils.isBlank(username)){
            map.put("msgnm","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }

        User user=userDao.selectUserByUsername(username);
        if(user == null){
            map.put("msgusr","用户名不存在");
            return map;
        }else{
            map.put("msg","登录成功");
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    private String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date=new Date();
        date.setTime(date.getTime()+1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDao.addTicket(ticket);//把标记用户登录的ticket保存到服务器

        return ticket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);
    }

}
