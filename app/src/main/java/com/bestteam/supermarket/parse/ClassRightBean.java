package com.bestteam.supermarket.parse;/**
 * Created by Xu on 2017/3/5.
 */

import com.google.gson.Gson;

import java.util.List;

/**
 * 代码之神
 * 保佑不出BUG
 * ！！！
 * author:Mr_Chen
 * email:853431405@qq.com
 */

/**
 * 分类：
 * 右边内容   解析类： ClassRightBean
 * <p>
 * items             是右侧的整页数据
 * DetailedItems     每一个item的类
 */

public class ClassRightBean {

    public static ClassRightBean getParseClassRightBean(String strJson) {

        return new Gson().fromJson(strJson, ClassRightBean.class);
    }

    private ResultData resultData;

    private String resultMsg;

    private int resultStatus;

    public void setResultData(ResultData resultData) {
        this.resultData = resultData;
    }

    public ResultData getResultData() {
        return this.resultData;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultMsg() {
        return this.resultMsg;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }

    public int getResultStatus() {
        return this.resultStatus;
    }

    public class ResultData {
        private List<Items> items;

        private String categoryId;

        public void setItems(List<Items> items) {
            this.items = items;
        }

        public List<Items> getItems() {
            return this.items;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryId() {
            return this.categoryId;
        }
    }

    public class Items {
        private String categoryName;

        private List<DetailedItems> items;

        private String categoryId;

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryName() {
            return this.categoryName;
        }

        public List<DetailedItems> getItems() {
            return items;
        }

        public void setItems(List<DetailedItems> items) {
            this.items = items;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryId() {
            return this.categoryId;
        }
    }

    public class DetailedItems {
        private String aliasName;

        private String id;

        private String parentId;

        private int sort;

        private String imgPath;

        private String code;

        public void setAliasName(String aliasName) {
            this.aliasName = aliasName;
        }

        public String getAliasName() {
            return this.aliasName;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getParentId() {
            return this.parentId;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getSort() {
            return this.sort;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getImgPath() {
            return this.imgPath;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

}
