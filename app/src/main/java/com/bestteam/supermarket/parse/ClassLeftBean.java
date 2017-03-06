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
 *      分类：
 *   左侧标签    Items
 *
 */
public class ClassLeftBean {
    public static ClassLeftBean getParseClassLeftBean(String strJson){

        return new Gson().fromJson(strJson,ClassLeftBean.class);
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
}
