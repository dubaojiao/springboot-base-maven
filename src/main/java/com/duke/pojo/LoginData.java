package com.duke.pojo;

import com.duke.annotation.Verification;

import java.io.Serializable;

public class LoginData implements Serializable {

    @Verification(nullable = false,maxLength = 11,minLength = 11,desc = "手机号")
    private String phone;
    @Verification(nullable = false,desc = "密码")
    private String pwd;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
