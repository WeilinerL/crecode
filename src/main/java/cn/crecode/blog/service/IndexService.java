package cn.crecode.blog.service;

import cn.crecode.blog.dao.IndexMapper;
import cn.crecode.blog.pojo.Index;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Transactional(readOnly = true)
    public Map<String,List<JSONObject>> carouselShow() {
        List<JSONObject> pictureList = new ArrayList<>();
        JSONObject picture = new JSONObject();
        for(int i = 1; i < 13; i ++) {
            Index index = indexMapper.selectByPrimaryKey(i);
            picture.put(index.getStrType(),index.getStrContent());
            if(i % 2 == 0) {
                picture.put("intIndexId",i);
                pictureList.add(picture);
                picture = new JSONObject();
            }
        }
        Map<String,List<JSONObject>> stringListMap = new HashMap<>();
        stringListMap.put("pictureList",pictureList);
        return stringListMap;
    }

    @Transactional(readOnly = true)
    public Map<String,List<JSONObject>> articleShow() {
        List<JSONObject> articleList = new ArrayList<>();
        JSONObject article = new JSONObject();
        for(int i = 13; i < 25; i ++) {
            Index index = indexMapper.selectByPrimaryKey(i);
            article.put(index.getStrType(),index.getStrContent());
            if((i - 12) % 3 == 0) {
                article.put("intIndexId",i);
                articleList.add(article);
                article = new JSONObject();
            }
        }
        Map<String,List<JSONObject>> stringListMap = new HashMap<>();
        stringListMap.put("articleList",articleList);
        return stringListMap;
    }

    public int editPicture(JSONObject picture) {
        int resultCount = 0;
        int intIndexId = picture.getIntValue("intIndexId");
        String picSrc = picture.getString("picSrc");
        String picContent = picture.getString("picContent");
        Index index = indexMapper.selectByPrimaryKey(intIndexId - 1);
        Index index1 = indexMapper.selectByPrimaryKey(intIndexId);
        index.setStrContent(picSrc);
        index1.setStrContent(picContent);
        resultCount += indexMapper.updateByPrimaryKeyWithBLOBs(index);
        resultCount += indexMapper.updateByPrimaryKeyWithBLOBs(index1);
        return resultCount;
    }

    public int editArticle(JSONObject article) {
        int resultCount = 0;
        int intIndexId = article.getIntValue("intIndexId");
        String articleTitle = article.getString("articleTitle");
        String articleContent = article.getString("articleContent");
        String articleAuthor = article.getString("articleAuthor");
        Index index = indexMapper.selectByPrimaryKey(intIndexId - 2);
        Index index1 = indexMapper.selectByPrimaryKey(intIndexId - 1);
        Index index2 = indexMapper.selectByPrimaryKey(intIndexId);
        index.setStrContent(articleTitle);
        index1.setStrContent(articleContent);
        index2.setStrContent(articleAuthor);
        resultCount += indexMapper.updateByPrimaryKeyWithBLOBs(index);
        resultCount += indexMapper.updateByPrimaryKeyWithBLOBs(index1);
        resultCount += indexMapper.updateByPrimaryKeyWithBLOBs(index2);
        return resultCount;
    }
}
