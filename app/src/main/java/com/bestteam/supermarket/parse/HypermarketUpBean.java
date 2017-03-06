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
 *      大麦场: 上半部分
 *  限时抢购+食品+纸巾
 */
public class HypermarketUpBean {

    public static HypermarketUpBean getParseHypermarketUpBean(String strJson){

        return new Gson().fromJson(strJson,HypermarketUpBean.class);
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
    public class ResultData
    {
        private List<Items> items;

        public void setItems(List<Items> items){
            this.items = items;
        }
        public List<Items> getItems(){
            return this.items;
        }
    }
    public class Items
    {
        private String id;

        private int index;

        private String name;

        private String number;

        private List<Adverts> adverts;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setIndex(int index){
            this.index = index;
        }
        public int getIndex(){
            return this.index;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setNumber(String number){
            this.number = number;
        }
        public String getNumber(){
            return this.number;
        }
        public void setAdverts(List<Adverts> adverts){
            this.adverts = adverts;
        }
        public List<Adverts> getAdverts(){
            return this.adverts;
        }
    }
    public class Adverts
    {
        private String startTime;

        private String id;

        private int index;

        private String title;

        private String imgPath2;

        private String imgPath;

        private String objId;

        private String number;

        private String endTime;

        private String type;

        private String url;

        public void setStartTime(String startTime){
            this.startTime = startTime;
        }
        public String getStartTime(){
            return this.startTime;
        }
        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setIndex(int index){
            this.index = index;
        }
        public int getIndex(){
            return this.index;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public String getTitle(){
            return this.title;
        }
        public void setImgPath2(String imgPath2){
            this.imgPath2 = imgPath2;
        }
        public String getImgPath2(){
            return this.imgPath2;
        }
        public void setImgPath(String imgPath){
            this.imgPath = imgPath;
        }
        public String getImgPath(){
            return this.imgPath;
        }
        public void setObjId(String objId){
            this.objId = objId;
        }
        public String getObjId(){
            return this.objId;
        }
        public void setNumber(String number){
            this.number = number;
        }
        public String getNumber(){
            return this.number;
        }
        public void setEndTime(String endTime){
            this.endTime = endTime;
        }
        public String getEndTime(){
            return this.endTime;
        }
        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
        public void setUrl(String url){
            this.url = url;
        }
        public String getUrl(){
            return this.url;
        }
    }


}
