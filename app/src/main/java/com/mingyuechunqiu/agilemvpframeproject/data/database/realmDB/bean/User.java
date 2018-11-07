package com.mingyuechunqiu.agilemvpframeproject.data.database.realmDB.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * <pre>
 *     author : xyj
 *     e-mail : yujie.xi@ehailuo.com
 *     time   : 2018/06/20
 *     desc   : 用户信息类
 *              继承自RealmObject
 *     version: 1.0
 * </pre>
 */
public class User extends RealmObject {

    @PrimaryKey
    private String name;

    @Required
    private String password;

    private String headPortrait;//头像

    @Required
    private Long loginTime;

    @Required
    private Integer roleType;//用户角色类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}
