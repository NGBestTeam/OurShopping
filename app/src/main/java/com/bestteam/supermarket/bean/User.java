package com.bestteam.supermarket.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangJinRui on 2017/3/8.
 */

public class User extends BmobUser {
    BmobFile photo;

    public BmobFile getPhoto() {
        return photo;
    }

    public void setPhoto(BmobFile photo) {
        this.photo = photo;
    }
}
