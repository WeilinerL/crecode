package cn.crecode.blog.pojo;

import org.springframework.stereotype.Component;

@Component
public class User {
    private Integer intUserId;

    private String strUsername;

    private String strPassword;

    private String strNickname;

    private String strAutograph;

    private String strHeadPortrait;

    private String strUserType;

    public Integer getIntUserId() {
        return intUserId;
    }

    public void setIntUserId(Integer intUserId) {
        this.intUserId = intUserId;
    }

    public String getStrUsername() {
        return strUsername;
    }

    public void setStrUsername(String strUsername) {
        this.strUsername = strUsername == null ? null : strUsername.trim();
    }

    public String getStrPassword() {
        return strPassword;
    }

    public void setStrPassword(String strPassword) {
        this.strPassword = strPassword == null ? null : strPassword.trim();
    }

    public String getStrNickname() {
        return strNickname;
    }

    public void setStrNickname(String strNickname) {
        this.strNickname = strNickname == null ? null : strNickname.trim();
    }

    public String getStrAutograph() {
        return strAutograph;
    }

    public void setStrAutograph(String strAutograph) {
        this.strAutograph = strAutograph == null ? null : strAutograph.trim();
    }

    public String getStrHeadPortrait() {
        return strHeadPortrait;
    }

    public void setStrHeadPortrait(String strHeadPortrait) {
        this.strHeadPortrait = strHeadPortrait == null ? null : strHeadPortrait.trim();
    }

    public String getStrUserType() {
        return strUserType;
    }

    public void setStrUserType(String strUserType) {
        this.strUserType = strUserType == null ? null : strUserType.trim();
    }
}