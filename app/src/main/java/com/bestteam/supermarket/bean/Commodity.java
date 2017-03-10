package com.bestteam.supermarket.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by WangJinRui on 2017/3/10.
 */

public class Commodity extends BmobObject {
    private String name;
    private String price;
    private String buycount;
    private String limitcount;

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

    public String getBuycount() {
        return buycount;
    }

    public void setBuycount(String buycount) {
        this.buycount = buycount;
    }

    public String getLimitcount() {
        return limitcount;
    }

    public void setLimitcount(String limitcount) {
        this.limitcount = limitcount;
    }
}
