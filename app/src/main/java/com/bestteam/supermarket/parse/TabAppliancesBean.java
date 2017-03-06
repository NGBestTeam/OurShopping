package com.bestteam.supermarket.parse;/**
 * Created by Xu on 2017/3/5.
 */

import java.util.List;

/**
 * 代码之神
 * 保佑不出BUG
 * ！！！
 * author:Mr_Chen
 * email:853431405@qq.com
 */

/**
 * 家电汽配
 */
public class TabAppliancesBean {
    private ResultData resultData;

    private String resultMsg;

    private int resultStatus;

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
    public int getResultStatus(){
        return this.resultStatus;
    }
    public class ResultData
    {
        private int recordCount;

        private List<Items> items;

        private int page;

        private String categoryId;

        private int pageSize;

        public void setRecordCount(int recordCount){
            this.recordCount = recordCount;
        }
        public int getRecordCount(){
            return this.recordCount;
        }
        public void setItems(List<Items> items){
            this.items = items;
        }
        public List<Items> getItems(){
            return this.items;
        }
        public void setPage(int page){
            this.page = page;
        }
        public int getPage(){
            return this.page;
        }
        public void setCategoryId(String categoryId){
            this.categoryId = categoryId;
        }
        public String getCategoryId(){
            return this.categoryId;
        }
        public void setPageSize(int pageSize){
            this.pageSize = pageSize;
        }
        public int getPageSize(){
            return this.pageSize;
        }
    }
    public class Items
    {
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

        public void setGoodsId(String goodsId){
            this.goodsId = goodsId;
        }
        public String getGoodsId(){
            return this.goodsId;
        }
        public void setTitle1(String title1){
            this.title1 = title1;
        }
        public String getTitle1(){
            return this.title1;
        }
        public void setGiveType(int giveType){
            this.giveType = giveType;
        }
        public int getGiveType(){
            return this.giveType;
        }
        public void setCategoryId(String categoryId){
            this.categoryId = categoryId;
        }
        public String getCategoryId(){
            return this.categoryId;
        }
        public void setBuycount(int buycount){
            this.buycount = buycount;
        }
        public int getBuycount(){
            return this.buycount;
        }
        public void setDiscountPrice(String discountPrice){
            this.discountPrice = discountPrice;
        }
        public String getDiscountPrice(){
            return this.discountPrice;
        }
        public void setMaxPrice(String maxPrice){
            this.maxPrice = maxPrice;
        }
        public String getMaxPrice(){
            return this.maxPrice;
        }
        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
        public void setLowPrice(String lowPrice){
            this.lowPrice = lowPrice;
        }
        public String getLowPrice(){
            return this.lowPrice;
        }
        public void setActivityId(String activityId){
            this.activityId = activityId;
        }
        public String getActivityId(){
            return this.activityId;
        }
        public void setStorageNum(int storageNum){
            this.storageNum = storageNum;
        }
        public int getStorageNum(){
            return this.storageNum;
        }
        public void setPrice(String price){
            this.price = price;
        }
        public String getPrice(){
            return this.price;
        }
        public void setImgPath(String imgPath){
            this.imgPath = imgPath;
        }
        public String getImgPath(){
            return this.imgPath;
        }
        public void setSkuId(String skuId){
            this.skuId = skuId;
        }
        public String getSkuId(){
            return this.skuId;
        }
        public void setExistExtra(String existExtra){
            this.existExtra = existExtra;
        }
        public String getExistExtra(){
            return this.existExtra;
        }
    }
}
