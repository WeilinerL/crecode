package cn.crecode.blog.controller;

import cn.crecode.blog.service.BlogService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class BlogController {

    private final static Log log = LogFactory.getLog(BlogController.class);

    @Autowired
    private BlogService blogService;

    @GetMapping(value = "/blog_center/blog_list")
    public ResponseEntity<Map<String, Object>> getBlogList() {
        HashMap<String, Object> result = new HashMap<>();
        if (log.isTraceEnabled()) {
            log.trace("获取crecode博客中心博客列表");
        }
        result.put("status", 1);
        result.put("data", blogService.getBlogList());
        if (log.isTraceEnabled()) {
            log.trace("获取crecode博客中心博客列表完成!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "/blog_center/blog_show")
    public ResponseEntity<Map<String, Object>> getSingleBlog(@RequestParam(value = "intBlogId") Integer intBlogId) {
        HashMap<String, Object> result = new HashMap<>();
        if(intBlogId != null) {
            if(log.isTraceEnabled()) {
                log.trace("获取单个博客: " + intBlogId);
            }
            result.put("status",1);
            result.put("data",blogService.getSingleBlog(intBlogId));
            if(log.isTraceEnabled()) {
                log.trace("获取单个博客成功!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result.put("status",0);
            result.put("msg","缺少必要属性!");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @GetMapping(value = "/blog_center/blog/author")
    public ResponseEntity<Map<String, Object>> getBlogAuthorDetail(@RequestParam(value = "intBlogId") Integer intBlogId) {
        HashMap<String, Object> result = new HashMap<>();
        if(intBlogId != null) {
            if(log.isTraceEnabled()) {
                log.trace("获取单个博客的作者信息: " + intBlogId);
            }
            result.put("status",1);
            result.put("data",blogService.getBlogAuthorDetail(intBlogId));
            if(log.isTraceEnabled()) {
                log.trace("获取单个博客作者信息成功!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result.put("status",0);
            result.put("msg","缺少必要属性!");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @DeleteMapping(value = "/admin/blog_center/blog_delete")
    public ResponseEntity<Map<String, Object>> deleteBlog(@RequestParam(value = "intBlogId") Integer intBlogId, @RequestParam(value = "strBlogAuthor") String author) {
        HashMap<String, Object> result = new HashMap<>();
        if(intBlogId != null) {
            if(log.isTraceEnabled()) {
                log.trace("删除博客: " + intBlogId);
            }
            if(blogService.deleteBlog(intBlogId,author) != 0) {
                if(log.isTraceEnabled()) {
                    log.trace("删除博客成功!");
                }
                result.put("status",1);
                result.put("data","删除博客成功!");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                if(log.isTraceEnabled()) {
                    log.trace("删除博客失败!");
                }
                result.put("status",0);
                result.put("msg","删除博客失败!");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("status",0);
            result.put("msg","缺少必要属性!");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @PostMapping(value="/admin/blog_center/pic_upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> addBlogPic(@RequestParam("picture") MultipartFile imgFile) throws Exception{
        if(log.isTraceEnabled()) {
            log.trace("收到文件：" + imgFile.getOriginalFilename());
        }
        //保存文件
        File folder = new File("/wocloud/tomcat_cluster/tomcat1/apache-tomcat-9.0.26/webapps/crecode/static/blog_img");
        if(!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = imgFile.getOriginalFilename();
        if(fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".JPG") || fileName.endsWith(".PNG")) {
            FileOutputStream fos = new FileOutputStream(new File(folder, fileName));
            IOUtils.copy(imgFile.getInputStream(), fos);
            fos.close();
            Map<String, Object> result = new HashMap<>();
            Map<String, String> data = new HashMap<>();
            data.put("blogPicSrc", "/static/blog_img/" + fileName);
            result.put("status", 1);
            result.put("data", data);
            //存储路径到数据库
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            throw new RuntimeException("不支持的格式，仅支持jpg和png格式");
        }
    }

    @PostMapping(value = "/admin/blog_center/blog_add")
    public ResponseEntity<Map<String, Object>> addBlog(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("strBlogTitle")
                && JSONCONTENT.contains("strBlogContent")
                && JSONCONTENT.contains("strBlogAuthor")
                && JSONCONTENT.contains("strBlogTags")) {
            if(log.isTraceEnabled()) {
                log.trace("添加博客: " + JSONCONTENT);
            }
            if(blogService.addBlog(JSONCONTENT) != 0) {
                if(log.isTraceEnabled()) {
                    log.trace("添加博客成功!");
                }
                result.put("status",1);
                result.put("data","添加博客成功!");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                if(log.isTraceEnabled()) {
                    log.trace("添加博客失败!");
                }
                result.put("status",0);
                result.put("msg","添加博客失败!");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("status",0);
            result.put("msg","缺少必要属性!");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @PutMapping(value = "/admin/blog_center/blog_edit")
    public ResponseEntity<Map<String, Object>> editBlog(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("intBlogId") && JSONCONTENT.contains("strBlogTitle")
                && JSONCONTENT.contains("strBlogContent")
                && JSONCONTENT.contains("strBlogAuthor")
                && JSONCONTENT.contains("strBlogTags")) {
            if(log.isTraceEnabled()) {
                log.trace("修改博客: " + JSONCONTENT);
            }
            if(blogService.editBlog(JSONCONTENT) != 0) {
                if(log.isTraceEnabled()) {
                    log.trace("修改博客成功!");
                }
                result.put("status",1);
                result.put("data","修改博客成功!");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                if(log.isTraceEnabled()) {
                    log.trace("修改博客失败!");
                }
                result.put("status",0);
                result.put("msg","修改博客失败!");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("status",0);
            result.put("msg","缺少必要属性!");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @PutMapping(value = "/blog_center/blog/read")
    public ResponseEntity<Map<String, Object>> blogReadCountPlusOne(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("intBlogId")) {
            int intBlogId = JSON.parseObject(JSONCONTENT).getIntValue("intBlogId");
            if(log.isTraceEnabled()) {
                log.trace("博客阅读数加一: " + intBlogId);
            }
            int addResult = blogService.blogReadCountPlusOne(intBlogId);
            if(addResult != 0) {
                result.put("status",1);
                result.put("data",addResult);
                if(log.isTraceEnabled()) {
                    log.trace("博客阅读数加一成功!");
                }
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                result.put("status",0);
                result.put("msg","博客阅读数加一失败!");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("status",0);
            result.put("msg","缺少必要属性!");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @PutMapping(value = "/blog_center/blog/like")
    public ResponseEntity<Map<String, Object>> blogLikePlusOne(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        int intBlogId = JSON.parseObject(JSONCONTENT).getIntValue("intBlogId");
        if(JSONCONTENT.contains("intBlogId")) {
            if(log.isTraceEnabled()) {
                log.trace("博客点赞数加一: " + intBlogId);
            }
            int addResult = blogService.blogLikePlusOne(intBlogId);
            if(addResult != 0) {
                result.put("status",1);
                result.put("data",addResult);
                if(log.isTraceEnabled()) {
                    log.trace("博客点赞数加一成功!");
                }
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                result.put("status",0);
                result.put("msg","博客点赞数加一失败!");
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("status",0);
            result.put("msg","缺少必要属性!");
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

}
