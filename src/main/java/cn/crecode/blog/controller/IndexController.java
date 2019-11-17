package cn.crecode.blog.controller;

import cn.crecode.blog.service.IndexService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/")
public class IndexController {

    private final static Log log = LogFactory.getLog(IndexController.class);

    @Autowired
    private IndexService indexService;

    @GetMapping(value = "index/carousel_show")
    public ResponseEntity<Map<String, Object>> getPictureList() {
        HashMap<String, Object> result = new HashMap<>();
        if(log.isTraceEnabled()) {
            log.trace("获取crecode主页轮播图图片源地址及内容");
        }
        result.put("status",1);
        result.put("data",indexService.carouselShow());
        if(log.isTraceEnabled()) {
            log.trace("获取crecode主页轮播图图片源地址及内容完成!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "index/article_show")
    public ResponseEntity<Map<String, Object>> getArticleList() {
        HashMap<String, Object> result = new HashMap<>();
        if(log.isTraceEnabled()) {
            log.trace("获取crecode主页诗歌诗集内容");
        }
        result.put("status",1);
        result.put("data",indexService.articleShow());
        if(log.isTraceEnabled()) {
            log.trace("获取crecode主页诗歌诗集内容完成!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping(value = "/admin/index/carousel_edit")
    public ResponseEntity<Map<String, Object>> editPicture(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("intIndexId") && JSONCONTENT.contains("picSrc") && JSONCONTENT.contains("picContent")) {
            if(log.isTraceEnabled()) {
                log.trace("编辑crecode主页轮播图图片及内容: " + JSONCONTENT);
            }
            result.put("status",1);
            result.put("data",indexService.editPicture(JSON.parseObject(JSONCONTENT)));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result.put("status",0);
            result.put("msg","缺失必要属性");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @PutMapping(value = "/admin/index/article_edit")
    public ResponseEntity<Map<String, Object>> editArticle(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("intIndexId") && JSONCONTENT.contains("articleTitle") && JSONCONTENT.contains("articleContent") && JSONCONTENT.contains("articleAuthor")) {
            if(log.isTraceEnabled()) {
                log.trace("编辑crecode主页诗歌诗集内容: " + JSONCONTENT);
            }
            result.put("status",1);
            result.put("data",indexService.editArticle(JSON.parseObject(JSONCONTENT)));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result.put("status",0);
            result.put("msg","缺失必要属性");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

}
