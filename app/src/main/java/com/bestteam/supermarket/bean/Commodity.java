package com.bestteam.supermarket.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by WangJinRui on 2017/3/10.
 */

public class Commodity extends BmobObject {
    private String name;
    private String price;
    private String count;
    private User mUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
