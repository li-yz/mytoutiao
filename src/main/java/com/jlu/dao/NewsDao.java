package com.jlu.dao;

import com.jlu.model.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
@Repository
@Mapper
public interface NewsDao {
    String TABLE_NAME="news";
    String INSERT_FIElDS=" title,link,image,like_count,comment_count,created_date,user_id ";
    String SELECT_FIELDS=" id, "+INSERT_FIElDS;

    @Insert({"insert ",TABLE_NAME,"(",INSERT_FIElDS,") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})",})
    public void addNews(News news);

    List<News> selectByUserIdAndOffset(@Param("userId")int userId,@Param("offset")int offset
            ,@Param("limit")int limit);

    @Select({"select ", SELECT_FIELDS," from ",TABLE_NAME," where id = #{id}"})
    News getNewsById(int id);

    void updateNews(@Param("id")int id,@Param("commentCount")int commentCount);
}
