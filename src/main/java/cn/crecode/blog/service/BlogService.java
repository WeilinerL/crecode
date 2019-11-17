package cn.crecode.blog.service;

import cn.crecode.blog.dao.BlogMapper;
import cn.crecode.blog.dao.BlogTagMapper;
import cn.crecode.blog.dao.UserMapper;
import cn.crecode.blog.pojo.Blog;
import cn.crecode.blog.pojo.BlogTag;
import cn.crecode.blog.pojo.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional(readOnly = true)
    public Map<String,List<JSONObject>> getBlogList() {
        List<JSONObject> blogList = new ArrayList<>();
        List<Blog> blogs = blogMapper.selectALl();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Blog blg : blogs) {
            JSONObject blog = new JSONObject();
            blog.put("intBlogId",blg.getIntBlogId());
            blog.put("strBlogTitle",blg.getStrBlogTitle());
            blog.put("strBlogContent",blg.getStrBlogContent());
            blog.put("strBlogAuthor",blg.getStrBlogAuthor());
            blog.put("dateTime",df.format(blg.getDateTime()));
            blog.put("intReadCount",blg.getIntReadCount());
            List<BlogTag> blogTags = blogTagMapper.selectAllByBlogId(blg.getIntBlogId());
            String[] tagName = new String[blogTags.size()];
            int i = 0;
            for(BlogTag blogTag : blogTags) {
                tagName[i] = blogTag.getStrTagName();
                i ++;
            }
            blog.put("strBlogTags",tagName);
            blogList.add(blog);
        }
        Map<String,List<JSONObject>> stringListMap = new HashMap<>();
        stringListMap.put("blogList",blogList);
        return stringListMap;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addBlog(String JSONCONTENT) {
        JSONObject blog = JSON.parseObject(JSONCONTENT);
        String strBlogTitle = blog.getString("strBlogTitle");
        String strBlogContent = blog.getString("strBlogContent");
        String strBlogAuthor = blog.getString("strBlogAuthor");
        List<String> stringBlogTags = (List<String>) blog.get("strBlogTags");
        Blog blog1 = new Blog();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
        blog1.setStrBlogTitle(strBlogTitle);
        blog1.setStrBlogContent(strBlogContent);
        blog1.setStrBlogAuthor(strBlogAuthor);
        blog1.setIntReadCount(0);
        blog1.setIntLike(0);
        Date dateTime = null;
        try {
            dateTime = df.parse(time);
        } catch (Exception e) {e.printStackTrace();}
        blog1.setDateTime(dateTime);
        blogMapper.insert(blog1);
        return addBlogTags(stringBlogTags,blogMapper.selectByDateTime(dateTime).getIntBlogId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private int addBlogTags(List<String> tags, int blogId) {
        int resultCount = 0;
        for(String tag : tags) {
            BlogTag blogTag = new BlogTag();
            blogTag.setStrTagName(tag);
            blogTag.setIntBlogId(blogId);
            resultCount += blogTagMapper.insert(blogTag);
        }
        return resultCount;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int editBlog(String JSONCONTENT) {
        JSONObject blog = JSON.parseObject(JSONCONTENT);
        int intBlogid = blog.getIntValue("intBlogId");
        String strBlogTitle = blog.getString("strBlogTitle");
        String strBlogContent = blog.getString("strBlogContent");
        String strBlogAuthor = blog.getString("strBlogAuthor");
        List<String> stringBlogTags = (List<String>) blog.get("strBlogTags");
        if(blogMapper.selectALlByBlogId(intBlogid).getStrBlogAuthor().equals(strBlogAuthor)) {
            Blog blogOriginal = blogMapper.selectByPrimaryKey(intBlogid);
            blogOriginal.setStrBlogTitle(strBlogTitle);
            blogOriginal.setStrBlogContent(strBlogContent);
            blogOriginal.setStrBlogAuthor(strBlogAuthor);
            editBlogTags(stringBlogTags,intBlogid);
            return blogMapper.updateByPrimaryKeyWithBLOBs(blogOriginal);
        }
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private int editBlogTags(List<String> tags, int blogId) {
        int resultCount = 0;
        blogTagMapper.deleteByBlogId(blogId); // 删除原来的标签
        // 添加新的标签
        for(String tag : tags) {
            BlogTag blogTag = new BlogTag();
            blogTag.setStrTagName(tag);
            blogTag.setIntBlogId(blogId);
            resultCount += blogTagMapper.insert(blogTag);
        }
        return resultCount;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteBlog(int blogId, String author) {
        if(blogMapper.selectALlByBlogId(blogId).getStrBlogAuthor().equals(author)) {
            deleteBlogTags(blogId);
            return blogMapper.deleteByPrimaryKey(blogId);
        }
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private int deleteBlogTags(int blogId) {
        return blogTagMapper.deleteByBlogId(blogId);
    }

    @Transactional(readOnly = true)
    public Map<String,JSONObject> getSingleBlog(int intBlogid) {
        List<BlogTag> blogTags = blogTagMapper.selectAllByBlogId(intBlogid);
        Blog blog = blogMapper.selectByPrimaryKey(intBlogid);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject data = new JSONObject();
        String[] tags = new String[blogTags.size()];
        int i = 0;
        for(BlogTag tag : blogTags) {
            tags[i] = tag.getStrTagName();
            i ++;
        }
        data.put("intBlogId",blog.getIntBlogId());
        data.put("strBlogTitle",blog.getStrBlogTitle());
        data.put("strBlogContent",blog.getStrBlogContent());
        data.put("strBlogAuthor",blog.getStrBlogAuthor());
        data.put("dateTime",df.format(blog.getDateTime()));
        data.put("StringBlogTags",tags);
        data.put("intReadCount",blog.getIntReadCount());
        data.put("intLike",blog.getIntLike());
        Map<String,JSONObject> stringJSONMap = new HashMap<>();
        stringJSONMap.put("blog",data);
        return stringJSONMap;
    }

    @Transactional(readOnly = true)
    public Map<String,JSONObject> getBlogAuthorDetail(int intBlogid) {
        Blog blog = blogMapper.selectByPrimaryKey(intBlogid);
        if(blog != null) {
            JSONObject data = new JSONObject();
            String authorName = blog.getStrBlogAuthor();
            User author = userMapper.selectByUserName(authorName);
            data.put("strNickname",author.getStrNickname());
            data.put("strAutograph",author.getStrAutograph());
            data.put("strHeadPortrait",author.getStrHeadPortrait());
            Map<String,JSONObject> stringJSONMap = new HashMap<>();
            stringJSONMap.put("author",data);
            return stringJSONMap;
        }
        return null;
    }

    public int blogReadCountPlusOne(int intBlogId) {
        Blog blog = blogMapper.selectByPrimaryKey(intBlogId);
        if(blog != null) {
            blog.setIntReadCount(blog.getIntReadCount() + 1);
            return blogMapper.updateByPrimaryKey(blog);
        }
        return 0;
    }

    public int blogLikePlusOne(int intBlogId) {
        Blog blog = blogMapper.selectByPrimaryKey(intBlogId);
        if(blog != null) {
            blog.setIntLike(blog.getIntLike() + 1);
            return blogMapper.updateByPrimaryKey(blog);
        }
        return 0;
    }

}
