package cn.crecode.blog.pojo;

import org.springframework.stereotype.Component;

@Component
public class BlogTag {
    private Integer intTagId;

    private String strTagName;

    private Integer intBlogId;

    public Integer getIntTagId() {
        return intTagId;
    }

    public void setIntTagId(Integer intTagId) {
        this.intTagId = intTagId;
    }

    public String getStrTagName() {
        return strTagName;
    }

    public void setStrTagName(String strTagName) {
        this.strTagName = strTagName == null ? null : strTagName.trim();
    }

    public Integer getIntBlogId() {
        return intBlogId;
    }

    public void setIntBlogId(Integer intBlogId) {
        this.intBlogId = intBlogId;
    }
}