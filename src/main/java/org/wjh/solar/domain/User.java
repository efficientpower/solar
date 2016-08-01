package org.wjh.solar.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author wangjihui
 *
 */
public class User implements Serializable {

    private static final long serialVersionUID = -3674105634919189554L;

    /* 性别 0|女 1|男 */
    public static final Integer GENDER_MALE = 1;
    public static final Integer GENDER_FEMALE = 0;

    private Integer id;
    private String userId;
    private String nickName;
    private String passWord;
    private Integer gender;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
