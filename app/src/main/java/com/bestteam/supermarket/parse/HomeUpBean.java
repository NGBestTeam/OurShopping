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
 * 获取具体的类 需要看 json
 *  List<HomeUpBean.Items> bean=HomeUpBean.getParseHomeUpBean(json).getResultData().getItems();
 *  List<HomeUpBean.Adverts> advertses=bean.get(0).getAdverts();
 *
 */
public class HomeUpBean {

    public static HomeUpBean getParseHomeUpBean(String strJson){

        return new Gson().fromJson(strJson,HomeUpBean.class);
    }

    private ResultData resultData;

    private String resultMsg;

    private int resultStatus;

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

    public HomeUpBean(ResultData resultData, String resultMsg, int resultStatus) {
        this.resultData = resultData;
        this.resultMsg = resultMsg;
        this.resultStatus = resultStatus;
    }

    @Override
    public String toString() {
        return "HomeUpBean{" +
                "resultData=" + resultData +
                ", resultMsg='" + resultMsg + '\'' +
                ", resultStatus=" + resultStatus +
                '}';
    }

    public class ResultData {

        private List<Items> items;

        public ResultData(List<Items> items) {
            this.items = items;
        }

        public List<Items> getItems() {
            return items;
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "ResultData{" +
                    "items=" + items +
                    '}';
        }
    }


    public class Items {
        private String id;

        private int index;

        private String name;

        private String number;

        private List<Adverts> adverts;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public List<Adverts> getAdverts() {
            return adverts;
        }

        public void setAdverts(List<Adverts> adverts) {
            this.adverts = adverts;
        }

        public Items(String id, int index, String name, String number, List<Adverts> adverts) {
            this.id = id;
            this.index = index;
            this.name = name;
            this.number = number;
            this.adverts = adverts;
        }

        @Override
        public String toString() {
            return "Items{" +
                    "id='" + id + '\'' +
                    ", index=" + index +
                    ", name='" + name + '\'' +
                    ", number='" + number + '\'' +
                    ", adverts=" + adverts +
                    '}';
        }
    }


    public class Adverts {

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

        public Adverts(String startTime, String id, int index, String title, String imgPath2, String imgPath, String objId, String number, String endTime, String type, String url) {
            this.startTime = startTime;
            this.id = id;
            this.index = index;
            this.title = title;
            this.imgPath2 = imgPath2;
            this.imgPath = imgPath;
            this.objId = objId;
            this.number = number;
            this.endTime = endTime;
            this.type = type;
            this.url = url;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgPath2() {
            return imgPath2;
        }

        public void setImgPath2(String imgPath2) {
            this.imgPath2 = imgPath2;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getObjId() {
            return objId;
        }

        public void setObjId(String objId) {
            this.objId = objId;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Adverts{" +
                    "startTime='" + startTime + '\'' +
                    ", id='" + id + '\'' +
                    ", index=" + index +
                    ", title='" + title + '\'' +
                    ", imgPath2='" + imgPath2 + '\'' +
                    ", imgPath='" + imgPath + '\'' +
                    ", objId='" + objId + '\'' +
                    ", number='" + number + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }


}
