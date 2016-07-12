package com.jlu.dao;

import com.jlu.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import static com.jlu.dao.UserDao.TABLE_NAME;


/**
 * Created by Administrator on 2016/7/4.
 */
@Mapper
@Repository
public interface UserDao {
    String TABLE_NAME="user";
    String INSERT_FIElDS=" name,password,salt,head_url ";
    String SELECT_FIELDS=" id,name,password,salt,head_url ";
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIElDS,
            ") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select",SELECT_FIELDS, "from ",TABLE_NAME," where id = #{id}"})
    User selectUserById(int id);

    @Delete("delete from "+TABLE_NAME+" where id = #{id} ")
    void deleteById(int id);

    @Update({"update ",TABLE_NAME," set password = #{password} where id = #{id} "})
    void update(User user);

    @Select({"select",SELECT_FIELDS, "from ",TABLE_NAME," where name = #{name}"})
    User selectUserByUsername(@Param("name")String name);

    //使用@Param注解来传递多个查询参数，@Param实现使用的也是map结构来保存查询参数 Paramname : Paramvalue
    @Select({"select",SELECT_FIELDS, "from ",TABLE_NAME," where id = #{userId} and name = #{name}"})
    User selectUserByNameAndUserId(@Param("userId") int userId,@Param("name")String name);

}
