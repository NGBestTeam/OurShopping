package com.bestteam.supermarket.parse;/**
 * Created by Xu on 2017/3/6.
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
//      大卖场的
//down（下  精品特卖+每日更新） 解析类： HypermarketDownBean

public class HypermarketDownBean {

    public static HypermarketDownBean  getParseHypermarketDownBean(String str){

        return new Gson().fromJson(str,HypermarketDownBean.class);
    }

    public class Items
    {
        private String activityId;

        private String activityTitle;

        private String status;

        private String imagePath;

        private String statusDesc;

        public void setActivityId(String activityId){
            this.activityId = activityId;
        }
        public String getActivityId(){
            return this.activityId;
        }
        public void setActivityTitle(String activityTitle){
            this.activityTitle = activityTitle;
        }
        public String getActivityTitle(){
            return this.activityTitle;
        }
        public void setStatus(String status){
            this.status = status;
        }
        public String getStatus(){
            return this.status;
        }
        public void setImagePath(String imagePath){
            this.imagePath = imagePath;
        }
        public String getImagePath(){
            return this.imagePath;
        }
        public void setStatusDesc(String statusDesc){
            this.statusDesc = statusDesc;
        }
        public String getStatusDesc(){
            return this.statusDesc;
        }
    }

    public class ResultData
    {
        private int recordCount;

        private List<Items> items;

        private int page;

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
        public void setPageSize(int pageSize){
            this.pageSize = pageSize;
        }
        public int getPageSize(){
            return this.pageSize;
        }
    }

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

}
