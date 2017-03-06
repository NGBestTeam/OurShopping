package com.bestteam.supermarket.parse;/**
 * Created by Xu on 2017/3/4.
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
 * 今日精选
 *
 */
public class TabSelectionBean {


    public static TabSelectionBean getParseTabSelectionBean(String strJson){

        return new Gson().fromJson(strJson,TabSelectionBean.class);
    }

    private ResultData resultData;

    private String resultMsg;

    private int resultStatus;

    public TabSelectionBean(ResultData resultData, String resultMsg, int resultStatus) {
        this.resultData = resultData;
        this.resultMsg = resultMsg;
        this.resultStatus = resultStatus;
    }

    public void setResultData(ResultData resultData){
        this.resultData = resultData;
    }
    public ResultData getResultData(){
        return this.resultData;
    }
    public void setResultMsg(String resultMsg){
        this.resultMsg = resultMsg;
    }
    public String getResultMsg(){
        return this.resultMsg;
    }
    public void setResultStatus(int resultStatus){
        this.resultStatus = resultStatus;
    }

    @Override
    public String toString() {
        return "TabSelectionBean{" +
                "resultData=" + resultData +
                ", resultMsg='" + resultMsg + '\'' +
                ", resultStatus=" + resultStatus +
                '}';
    }

    ///
    public class ResultData{
        private int recordCount;

        private List<HomeTabSelection> items;

        private int page;

        private String categoryId;

        private int pageSize;

        public ResultData(int recordCount, List<HomeTabSelection> items, int page, String categoryId, int pageSize) {
            this.recordCount = recordCount;
            this.items = items;
            this.page = page;
            this.categoryId = categoryId;
            this.pageSize = pageSize;
        }

        public int getRecordCount() {
            return recordCount;
        }

        public void setRecordCount(int recordCount) {
            this.recordCount = recordCount;
        }

        public List<HomeTabSelection> getItems() {
            return items;
        }

        public void setItems(List<HomeTabSelection> items) {
            this.items = items;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        @Override
        public String toString() {
            return "ResultData{" +
                    "recordCount=" + recordCount +
                    ", items=" + items +
                    ", page=" + page +
                    ", categoryId='" + categoryId + '\'' +
                    ", pageSize=" + pageSize +
                    '}';
        }
    }
    public class HomeTabSelection{
        private String goodsId;

        private String title1;

        private int giveType;

        private String categoryId;

        private int buycount;

        private String discountPrice;

        private String maxPrice;

        private String type;

        private String lowPrice;

        private String activityId;

        private int storageNum;

        private String price;

        private String imgPath;

        private String skuId;

        private String existExtra;

        public HomeTabSelection(String goodsId, String title1, int giveType, String categoryId, int buycount, String discountPrice, String maxPrice, String type, String lowPrice, String activityId, int storageNum, String price, String imgPath, String skuId, String existExtra) {
            this.goodsId = goodsId;
            this.title1 = title1;
            this.giveType = giveType;
            this.categoryId = categoryId;
            this.buycount = buycount;
            this.discountPrice = discountPrice;
            this.maxPrice = maxPrice;
            this.type = type;
            this.lowPrice = lowPrice;
            this.activityId = activityId;
            this.storageNum = storageNum;
            this.price = price;
            this.imgPath = imgPath;
            this.skuId = skuId;
            this.existExtra = existExtra;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public int getGiveType() {
            return giveType;
        }

        public void setGiveType(int giveType) {
            this.giveType = giveType;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public int getBuycount() {
            return buycount;
        }

        public void setBuycount(int buycount) {
            this.buycount = buycount;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(String maxPrice) {
            this.maxPrice = maxPrice;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLowPrice() {
            return lowPrice;
        }

        public void setLowPrice(String lowPrice) {
            this.lowPrice = lowPrice;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public int getStorageNum() {
            return storageNum;
        }

        public void setStorageNum(int storageNum) {
            this.storageNum = storageNum;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getExistExtra() {
            return existExtra;
        }

        public void setExistExtra(String existExtra) {
            this.existExtra = existExtra;
        }

        @Override
        public String toString() {
            return "HomeTabSelection{" +
                    "goodsId='" + goodsId + '\'' +
                    ", title1='" + title1 + '\'' +
                    ", giveType=" + giveType +
                    ", categoryId='" + categoryId + '\'' +
                    ", buycount=" + buycount +
                    ", discountPrice='" + discountPrice + '\'' +
                    ", maxPrice='" + maxPrice + '\'' +
                    ", type='" + type + '\'' +
                    ", lowPrice='" + lowPrice + '\'' +
                    ", activityId='" + activityId + '\'' +
                    ", storageNum=" + storageNum +
                    ", price='" + price + '\'' +
                    ", imgPath='" + imgPath + '\'' +
                    ", skuId='" + skuId + '\'' +
                    ", existExtra='" + existExtra + '\'' +
                    '}';
        }
    }


}
