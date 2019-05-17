package com.xxl.job.admin.core.model;

public class XxlJobUser {

    private String userName;
    private String password;
    private boolean authority;
    private String lastLoginTime;

    public XxlJobUser() {
    }

    public XxlJobUser(String userName, String password, boolean authority, String lastLoginTime) {
        this.userName = userName;
        this.password = password;
        this.authority = authority;
        this.lastLoginTime = lastLoginTime;
    }

    public String getUserName() {
        return userName;
    }

    public XxlJobUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public XxlJobUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isAuthority() {
        return authority;
    }

    public XxlJobUser setAuthority(boolean authority) {
        this.authority = authority;
        return this;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public XxlJobUser setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    @Override
    public String toString() {
        return "XxlJobUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", authority=" + authority +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                '}';
    }
}
