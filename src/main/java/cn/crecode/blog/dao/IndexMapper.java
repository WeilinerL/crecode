package cn.crecode.blog.dao;

import cn.crecode.blog.pojo.Index;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IndexMapper {

    int deleteByPrimaryKey(Integer intIndexId);

    int insert(Index record);

    int insertSelective(Index record);

    Index selectByPrimaryKey(Integer intIndexId);

    int updateByPrimaryKeySelective(Index record);

    int updateByPrimaryKeyWithBLOBs(Index record);

    int updateByPrimaryKey(Index record);
}