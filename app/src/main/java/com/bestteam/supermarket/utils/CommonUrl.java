package com.bestteam.supermarket.utils;

/**
 * Created by LJWE on 2017/3/4.
 * 所有API接口
 */

public class CommonUrl {

    // 检查新版本的Url：
    public static final String UPDATE_APP_URL = "http://10.10.156.21/fzdmcupdate.json";

    //1.	进入广告位(服务器返回数据)
    public static final String returnAdvert = "http://app-client.ffzxnet.com/app-client-web/advert/startupPage.do?params={%22imgSize%22:%221080X1920%22,%22sysType%22:1}";
    public static final String returnAdvert02 = "http://app-client.ffzxnet.com/app-client-web/advert/getBaseTab.do";

    //2,首页:
    //轮播: 包括头部轮播+分类+居家小能手
    public static final String url1 = "http://app-client.ffzxnet.com/app-client-web/advert/getAdvertsNew.do?params={%22number%22:%22AD_INDEX%22}";

    //限时抢购
    public static final String url2 = "http://app-client.ffzxnet.com/app-client-web/activity/findPanicList.do?params={%22activityId%22:%22%22,%22cityId%22:%22%22,%22defaultPanic%22:%221%22,%22page%22:1,%22pageSize%22:10,%22startDate%22:%22%22}";

    //今日精选
    public static final String url3 = "http://app-client.ffzxnet.com/app-client-web/commodity/recommendCommodities.do?params={%22areaStr%22:%22INDEX_SELECTED%22,%22categoryId%22:%22%22,%22cityId%22:%22%22,%22page%22:1,%22pageSize%22:10}";

    //今日精选分类
    public static final String url4 = "http://app-client.ffzxnet.com/app-client-web/category/commendCategory.do";

    //今日精选内容
    public static final String url5 = "http://app-client.ffzxnet.com/app-client-web/commodity/recommendCommodities.do?params={%22areaStr%22:%22INDEX_SELECTED%22,%22categoryId%22:%22%22,%22cityId%22:%22%22,%22page%22:1,%22pageSize%22:10}";

    //食品酒饮
    public static final String url6 = "http://app-client.ffzxnet.com/app-client-web/commodity/recommendCommodities.do?params={%22areaStr%22:%22INDEX_SELECTED%22,%22categoryId%22:%22bd8d74d19dde49dc9c0965784c6a5c18%22,%22cityId%22:%2228f99b2e47f64e0a8a39b26f107d6a52%22,%22page%22:2,%22pageSize%22:10}";

    //生鲜冷藏
    public static final String  url7 = "http://app-client.ffzxnet.com/app-client-web/commodity/recommendCommodities.do?params={%22areaStr%22:%22INDEX_SELECTED%22,%22categoryId%22:%22eebe31404b464eccbfdbc853c8a8b2f6%22,%22cityId%22:%2228f99b2e47f64e0a8a39b26f107d6a52%22,%22page%22:1,%22pageSize%22:10}";

    //家电汽配
    public static final String  url8 = "http://app-client.ffzxnet.com/app-client-web/commodity/recommendCommodities.do?params={%22areaStr%22:%22INDEX_SELECTED%22,%22categoryId%22:%22cc72725dc95e44b88312c95dee43c4fd%22,%22cityId%22:%2228f99b2e47f64e0a8a39b26f107d6a52%22,%22page%22:1,%22pageSize%22:10}";

    //3,分类
    //左边分类
    public static final String  url9 = "http://app-client.ffzxnet.com/app-client-web/category/commendCategory.do";

    //右边内容
    public static final String  url10 = "http://app-client.ffzxnet.com/app-client-web/category/commendCategory.do?params={%22id%22:%22bd8d74d19dde49dc9c0965784c6a5c18%22}";

    //4,大麦场  共两个接口  分为 up（上   限时抢购+食品+纸巾） down（下  精品特卖+每日更新）
    //限时抢购+食品+纸巾   解析类： HypermarketUpBean
    public static final String  url11 = "http://app-client.ffzxnet.com/app-client-web/advert/getAdvertsNew.do?params={%22number%22:%22DMC%22}";
    //down（下  精品特卖+每日更新） 解析类： HypermarketDownBean
    public static final String url12 = "http://app-client.ffzxnet.com/app-client-web/activity/findDMCActivity.do?params={\"page\":1,\"pageSize\":20}";

    //二级页面
    public static final  String url13="http://app-client.ffzxnet.com/app-client-web/activity/findActivityBanner.do?params={\"activityId\":\"a3b52573831b4d82864a21c562f762f1\"}";
    public static String replaceImgUrl(String imgUrl){
        return imgUrl.replace("size","origin");
    }
}
