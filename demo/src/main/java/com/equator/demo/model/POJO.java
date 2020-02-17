package com.equator.demo.model;

import java.util.Date;

/**
 * @Author: Equator
 * @Date: 2020/2/13 7:48
 **/

public class POJO {
    private String name;
    private Date birthday;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
