package com.bestteam.supermarket.parse;/**
 * Created by Xu on 2017/3/4.
 */

/**
 * 代码之神
 * 保佑不出BUG
 * ！！！
 * author:Mr_Chen
 * email:853431405@qq.com
 */

import com.google.gson.Gson;

import java.util.List;

/**
 *
 *           底部TabLayout标签分类
 *
 * ----注意 获取 tab标签 必须 在数据源头部 添加 “今日精选”
 *
 * 底部TabLayout标签分类: 需要添加  “今日精选”+数据源
 *
 * Items
 */
public class TabLabelBean {

    public static TabLabelBean getParseTabLabelBean(String strJson){

        return new Gson().fromJson(strJson,TabLabelBean.class);
    }


    public class Items
    {
        private String aliasName;

        private String id;

        private int sort;

        private String code;

        public void setAliasName(String aliasName){
            this.aliasName = aliasName;
        }
        public String getAliasName(){
            return this.aliasName;
        }
        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setSort(int sort){
            this.sort = sort;
        }
        public int getSort(){
            return this.sort;
        }
        public void setCode(String code){
            this.code = code;
        }
        public String getCode(){
            return this.code;
        }
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


