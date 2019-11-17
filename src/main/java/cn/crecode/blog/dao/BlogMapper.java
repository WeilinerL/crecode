package cn.crecode.blog.dao;

import cn.crecode.blog.pojo.Blog;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface BlogMapper {

    @Select("select * from t_cre_blog")
    List<Blog> selectALl();

    @Select("select * from t_cre_blog where int_blog_id = #{blogId}")
    Blog selectALlByBlogId(int blogId);

    @Select("select * from t_cre_blog where date_time = #{dateTime}")
    Blog selectByDateTime(Date dateTime);

    int deleteByPrimaryKey(Integer intBlogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer intBlogId);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);
}