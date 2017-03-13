package com.bestteam.supermarket.bean;

/**
 * Created by WangJinRui on 2017/3/11.
 */

public class ShopCartInfo {
    private String commodityId;
    private int commodityCount;
    private double commodityPrice;

    public ShopCartInfo() {
    }

    public ShopCartInfo(String commodityId, int commodityCount, double commodityPrice) {
        this.commodityId = commodityId;
        this.commodityCount = commodityCount;
        this.commodityPrice = commodityPrice;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(int commodityCount) {
        this.commodityCount = commodityCount;
    }

    public double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }
}
