package com.duke.entity.mysql;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(10) COMMENT'id'",nullable = false)
    private Integer id;

    @Column(columnDefinition = "varchar(11) COMMENT'手机号'",nullable = false)
    private String phone;

    @Column(columnDefinition = "varchar(32) COMMENT'密码'",nullable = false)
    private String pwd;

    /**
     * 添加时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
