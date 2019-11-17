package cn.crecode.blog.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Blog {
    private Integer intBlogId;

    private String strBlogTitle;

    private String strBlogAuthor;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dateTime;

    private Integer intReadCount;

    private Integer intLike;

    private String strBlogContent;

    public Integer getIntBlogId() {
        return intBlogId;
    }

    public void setIntBlogId(Integer intBlogId) {
        this.intBlogId = intBlogId;
    }

    public String getStrBlogTitle() {
        return strBlogTitle;
    }

    public void setStrBlogTitle(String strBlogTitle) {
        this.strBlogTitle = strBlogTitle == null ? null : strBlogTitle.trim();
    }

    public String getStrBlogAuthor() {
        return strBlogAuthor;
    }

    public void setStrBlogAuthor(String strBlogAuthor) {
        this.strBlogAuthor = strBlogAuthor == null ? null : strBlogAuthor.trim();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getIntReadCount() {
        return intReadCount;
    }

    public void setIntReadCount(Integer intReadCount) {
        this.intReadCount = intReadCount;
    }

    public Integer getIntLike() {
        return intLike;
    }

    public void setIntLike(Integer intLike) {
        this.intLike = intLike;
    }

    public String getStrBlogContent() {
        return strBlogContent;
    }

    public void setStrBlogContent(String strBlogContent) {
        this.strBlogContent = strBlogContent == null ? null : strBlogContent.trim();
    }
}