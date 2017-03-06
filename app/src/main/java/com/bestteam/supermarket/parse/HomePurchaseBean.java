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
 * HomePurchaseBean  Bean类
 *  使用方法
 *   List<HomePurchaseBean.HomePurchase> 数据=   HomePurchaseBean.getParseHomePurchaseBean(json).getResultData().getItems();
 *
 */

public class HomePurchaseBean {
    public static HomePurchaseBean getParseHomePurchaseBean(String strJson){

        return new Gson().fromJson(strJson,HomePurchaseBean.class);
    }



    private ResultData resultData;

    private String resultMsg;

    private int resultStatus;

    public HomePurchaseBean(ResultData resultData, String resultMsg, int resultStatus) {
        this.resultData = resultData;
        this.resultMsg = resultMsg;
        this.resultStatus = resultStatus;
    }

    @Override
    public String toString() {
        return "HomePurchaseBean{" +
                "resultData=" + resultData +
                ", resultMsg='" + resultMsg + '\'' +
                ", resultStatus=" + resultStatus +
                '}';
    }

    public ResultData getResultData() {
        return resultData;
    }

    public void setResultData(ResultData resultData) {
        this.resultData = resultData;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public int getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }

    public  class ResultData {

        private String rangeDate;

        private int recordCount;

        private String rangeDateString;

        private String status;

        private List<HomePurchase> items;

        private String statusStr;

        private String prompt;

        private String endDateMillis;

        private String startDateMillis;

        public ResultData(String rangeDate, int recordCount, String rangeDateString, String status, List<HomePurchase> HomePurchase, String statusStr, String prompt, String endDateMillis, String startDateMillis) {
            this.rangeDate = rangeDate;
            this.recordCount = recordCount;
            this.rangeDateString = rangeDateString;
            this.status = status;
            this.items = HomePurchase;
            this.statusStr = statusStr;
            this.prompt = prompt;
            this.endDateMillis = endDateMillis;
            this.startDateMillis = startDateMillis;
        }

        @Override
        public String toString() {
            return "ResultData{" +
                    "rangeDate='" + rangeDate + '\'' +
                    ", recordCount=" + recordCount +
                    ", rangeDateString='" + rangeDateString + '\'' +
                    ", status='" + status + '\'' +
                    ", HomePurchase=" + items +
                    ", statusStr='" + statusStr + '\'' +
                    ", prompt='" + prompt + '\'' +
                    ", endDateMillis='" + endDateMillis + '\'' +
                    ", startDateMillis='" + startDateMillis + '\'' +
                    '}';
        }

        public String getRangeDate() {
            return rangeDate;
        }

        public void setRangeDate(String rangeDate) {
            this.rangeDate = rangeDate;
        }

        public int getRecordCount() {
            return recordCount;
        }

        public void setRecordCount(int recordCount) {
            this.recordCount = recordCount;
        }

        public String getRangeDateString() {
            return rangeDateString;
        }

        public void setRangeDateString(String rangeDateString) {
            this.rangeDateString = rangeDateString;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<HomePurchase> getItems() {
            return items;
        }

        public void setItems(List<HomePurchase> items) {
            this.items = items;
        }

        public String getStatusStr() {
            return statusStr;
        }

        public void setStatusStr(String statusStr) {
            this.statusStr = statusStr;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getEndDateMillis() {
            return endDateMillis;
        }

        public void setEndDateMillis(String endDateMillis) {
            this.endDateMillis = endDateMillis;
        }

        public String getStartDateMillis() {
            return startDateMillis;
        }

        public void setStartDateMillis(String startDateMillis) {
            this.startDateMillis = startDateMillis;
        }
    }

    public class HomePurchase {

        private String limit;

        private String goodsId;

        private String presaleCount;

        private String saleIncrease;

        private String panicsaleCount;

        private String categoryId;

        private String discountPrice;

        private String isRemind;

        private String idLimit;

        private String type;

        private String activityId;

        private String priceAmong;

        private String isAllowFlag;

        private String unit;

        private String price;

        private String activityImgPath;

        private String activityTitle;

        private String activityEndTime;

        private String remindCount;

        private String commodityNo;

        private String isPANICBUY;

        private String existExtra;

        private String activityTime;

        public HomePurchase(String limit, String goodsId, String presaleCount, String saleIncrease, String panicsaleCount, String categoryId, String discountPrice, String isRemind, String idLimit, String type, String activityId, String priceAmong, String isAllowFlag, String unit, String price, String activityImgPath, String activityTitle, String activityEndTime, String remindCount, String commodityNo, String isPANICBUY, String existExtra, String activityTime) {
            this.limit = limit;
            this.goodsId = goodsId;
            this.presaleCount = presaleCount;
            this.saleIncrease = saleIncrease;
            this.panicsaleCount = panicsaleCount;
            this.categoryId = categoryId;
            this.discountPrice = discountPrice;
            this.isRemind = isRemind;
            this.idLimit = idLimit;
            this.type = type;
            this.activityId = activityId;
            this.priceAmong = priceAmong;
            this.isAllowFlag = isAllowFlag;
            this.unit = unit;
            this.price = price;
            this.activityImgPath = activityImgPath;
            this.activityTitle = activityTitle;
            this.activityEndTime = activityEndTime;
            this.remindCount = remindCount;
            this.commodityNo = commodityNo;
            this.isPANICBUY = isPANICBUY;
            this.existExtra = existExtra;
            this.activityTime = activityTime;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getPresaleCount() {
            return presaleCount;
        }

        public void setPresaleCount(String presaleCount) {
            this.presaleCount = presaleCount;
        }

        public String getSaleIncrease() {
            return saleIncrease;
        }

        public void setSaleIncrease(String saleIncrease) {
            this.saleIncrease = saleIncrease;
        }

        public String getPanicsaleCount() {
            return panicsaleCount;
        }

        public void setPanicsaleCount(String panicsaleCount) {
            this.panicsaleCount = panicsaleCount;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        public String getIsRemind() {
            return isRemind;
        }

        public void setIsRemind(String isRemind) {
            this.isRemind = isRemind;
        }

        public String getIdLimit() {
            return idLimit;
        }

        public void setIdLimit(String idLimit) {
            this.idLimit = idLimit;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getPriceAmong() {
            return priceAmong;
        }

        public void setPriceAmong(String priceAmong) {
            this.priceAmong = priceAmong;
        }

        public String getIsAllowFlag() {
            return isAllowFlag;
        }

        public void setIsAllowFlag(String isAllowFlag) {
            this.isAllowFlag = isAllowFlag;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getActivityImgPath() {
            return activityImgPath;
        }

        public void setActivityImgPath(String activityImgPath) {
            this.activityImgPath = activityImgPath;
        }

        public String getActivityTitle() {
            return activityTitle;
        }

        public void setActivityTitle(String activityTitle) {
            this.activityTitle = activityTitle;
        }

        public String getActivityEndTime() {
            return activityEndTime;
        }

        public void setActivityEndTime(String activityEndTime) {
            this.activityEndTime = activityEndTime;
        }

        public String getRemindCount() {
            return remindCount;
        }

        public void setRemindCount(String remindCount) {
            this.remindCount = remindCount;
        }

        public String getCommodityNo() {
            return commodityNo;
        }

        public void setCommodityNo(String commodityNo) {
            this.commodityNo = commodityNo;
        }

        public String getIsPANICBUY() {
            return isPANICBUY;
        }

        public void setIsPANICBUY(String isPANICBUY) {
            this.isPANICBUY = isPANICBUY;
        }

        public String getExistExtra() {
            return existExtra;
        }

        public void setExistExtra(String existExtra) {
            this.existExtra = existExtra;
        }

        public String getActivityTime() {
            return activityTime;
        }

        public void setActivityTime(String activityTime) {
            this.activityTime = activityTime;
        }

        @Override
        public String toString() {
            return "HomePurchase{" +
                    "limit='" + limit + '\'' +
                    ", goodsId='" + goodsId + '\'' +
                    ", presaleCount='" + presaleCount + '\'' +
                    ", saleIncrease='" + saleIncrease + '\'' +
                    ", panicsaleCount='" + panicsaleCount + '\'' +
                    ", categoryId='" + categoryId + '\'' +
                    ", discountPrice='" + discountPrice + '\'' +
                    ", isRemind='" + isRemind + '\'' +
                    ", idLimit='" + idLimit + '\'' +
                    ", type='" + type + '\'' +
                    ", activityId='" + activityId + '\'' +
                    ", priceAmong='" + priceAmong + '\'' +
                    ", isAllowFlag='" + isAllowFlag + '\'' +
                    ", unit='" + unit + '\'' +
                    ", price='" + price + '\'' +
                    ", activityImgPath='" + activityImgPath + '\'' +
                    ", activityTitle='" + activityTitle + '\'' +
                    ", activityEndTime='" + activityEndTime + '\'' +
                    ", remindCount='" + remindCount + '\'' +
                    ", commodityNo='" + commodityNo + '\'' +
                    ", isPANICBUY='" + isPANICBUY + '\'' +
                    ", existExtra='" + existExtra + '\'' +
                    ", activityTime='" + activityTime + '\'' +
                    '}';
        }
    }
}
