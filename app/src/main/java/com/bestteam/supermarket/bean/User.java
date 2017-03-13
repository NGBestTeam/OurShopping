package com.bestteam.supermarket.bean;

import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by WangJinRui on 2017/3/8.
 */

public class User extends BmobUser {
    private BmobFile photo;

    private String sex;

    private BmobRelation commodity;

    private HashMap<String, Integer> shopCount;

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public BmobRelation getCommodity() {
        return commodity;
    }

    public void setCommodity(BmobRelation commodity) {
        this.commodity = commodity;
    }

    public HashMap<String, Integer> getShopCount() {
        return shopCount;
    }

    public void setShopCount(HashMap<String, Integer> shopCount) {
        this.shopCount = shopCount;
    }
}
