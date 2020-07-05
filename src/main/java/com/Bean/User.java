package com.Bean;

import java.util.Date;

public class User {
    private int userId;
    private String userName;
    private String userIcon;
    private String userCode;
    private String userPassword;
    private String userPhoneNumber;
    private String userSecurityCode;
    private Date userRegistrationtime;
    private int userLevel;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserSecurityCode() {
        return userSecurityCode;
    }

    public void setUserSecurityCode(String userSecurityCode) {
        this.userSecurityCode = userSecurityCode;
    }

    public Date getUserRegistrationtime() {
        return userRegistrationtime;
    }

    public void setUserRegistrationtime(Date userRegistrationtime) {
        this.userRegistrationtime = userRegistrationtime;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
}
