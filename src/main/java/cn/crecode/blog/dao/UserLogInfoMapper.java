package cn.crecode.blog.dao;

import cn.crecode.blog.pojo.UserLogInfo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Component;

@Component
public interface UserLogInfoMapper {

    @Delete("delete from t_cre_log_info where str_token = #{token}")
    int deleteByToken(String token);

    int deleteByPrimaryKey(String strUsername);

    int insert(UserLogInfo record);

    int insertSelective(UserLogInfo record);

    UserLogInfo selectByPrimaryKey(String strUsername);

    int updateByPrimaryKeySelective(UserLogInfo record);

    int updateByPrimaryKey(UserLogInfo record);
}