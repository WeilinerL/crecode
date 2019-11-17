package cn.crecode.blog.dao;

import cn.crecode.blog.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    @Select("select * from t_cre_user where str_username = #{userName}")
    User selectByUserName(String userName);

    int deleteByPrimaryKey(Integer intUserId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer intUserId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}