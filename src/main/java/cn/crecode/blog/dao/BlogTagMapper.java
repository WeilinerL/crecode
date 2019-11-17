package cn.crecode.blog.dao;

import cn.crecode.blog.pojo.BlogTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BlogTagMapper {

    @Select("select * from t_cre_blog_tags where int_blog_id = #{blogId}")
    List<BlogTag> selectAllByBlogId(int blogId);

    @Delete("delete from t_cre_blog_tags where int_blog_id = #{blogId}")
    int deleteByBlogId(int blogId);

    int deleteByPrimaryKey(Integer intTagId);

    int insert(BlogTag record);

    int insertSelective(BlogTag record);

    BlogTag selectByPrimaryKey(Integer intTagId);

    int updateByPrimaryKeySelective(BlogTag record);

    int updateByPrimaryKey(BlogTag record);
}