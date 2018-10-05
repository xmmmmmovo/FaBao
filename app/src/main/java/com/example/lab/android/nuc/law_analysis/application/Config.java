package com.example.lab.android.nuc.law_analysis.application;

public class Config {

    public static int PAGE_SIZE = 20;

    /**
     * 新闻 http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
     */
    public static final String NEWS_URL = "http://c.m.163.com/nc/article/";

    public static final String NEWS_END_URL = "-" + PAGE_SIZE + ".html";

    // 头条TYPE
    public static final String HEADLINE_TYPE = "headline";
    // 其他TYPE
    public static final String OTHER_TYPE = "list";

    // 头条id
    public static final String HEADLINE_ID = "T1348647909107";
    // 汽车id
    public static final String CAR_ID = "T1348654060988";
    // 足球id
    public static final String FOOTBALL_ID = "T1399700447917";
    // 娱乐id
    public static final String ENTERTAINMENT_ID = "T1348648517839";
    // 体育id
    public static final String SPORTS_ID = "T1348649079062";
    // 财经id
    public static final String FINANCE_ID = "T1348648756099";
    // 科技id
    public static final String TECH_ID = "T1348649580692";
    // 电影id
    public static final String MOVIE_ID = "T1348648650048";

    /**
     * 新闻详情 http://c.m.163.com/nc/article/CL7OKMIH000189FH/full.html
     */
    public static final String NEWS_DETAIL_URL = "http://c.m.163.com/nc/article/";

    public static final String NEWS_DETAIL_END_URL = "/full.html";

    /**
     * 视频 http://c.3g.163.com/nc/video/list/V9LG4CHOR/n/10-10.html
     */
    public static final String VIDEO_URL = "http://c.m.163.com/nc/video/list/";

    public static final String VIDEO_CENTER_URL = "/n/";

    public static final String VIDEO_END_URL = "-" + PAGE_SIZE + ".html";

    // 精品视频
    public static final String VIDEO_CHOICE_ID = "00850FRB";
    // 娱乐视频
    public static final String VIDEO_ENTERTAINMENT_ID = "V9LG4CHOR";
    // 搞笑视频
    public static final String VIDEO_FUN_ID = "V9LG4E6VR";
    // 热点视频
    public static final String VIDEO_HOT_ID = "V9LG4B3A0";

    // 天气预报
    public static final String WEATHER_URL = "https://free-api.heweather.com/v5/weather?city=";

    public static final String WEATHER_END_URL = "&key=33ebf4d7998a4548942adcad5582e503";

    // APP更新地址
    public static final String APP_UPDATE_URL = "http://api.fir.im/apps/latest/592e6d2d548b7a1ccf000135?&api_token=ad06cab6f35eb810c666a7b3936e9119";

    /**
     * 获取新闻网址
     *
     * @param id
     * @param index
     * @return
     */
    public static String getNewsUrl(String id, int index) {
        String url;
        switch (id) {
            case Config.HEADLINE_ID:
                url = Config.NEWS_URL + HEADLINE_TYPE + "/" + id + "/" + index + NEWS_END_URL;
                break;
            default:
                url = Config.NEWS_URL + OTHER_TYPE + "/" + id + "/" + index + NEWS_END_URL;
                break;
        }
        return url;
    }

    /**
     * 获取新闻详情地址
     *
     * @param postId
     * @return
     */
    public static String getNewsDetailUrl(String postId) {
        return Config.NEWS_DETAIL_URL + postId + Config.NEWS_DETAIL_END_URL;
    }

    /**
     * 获取视频网址
     *
     * @param id
     * @param index
     * @return
     */
    public static String getVideoUrl(String id, int index) {
        return Config.VIDEO_URL + id + Config.VIDEO_CENTER_URL + index + Config.VIDEO_END_URL;
    }

    /**
     * 获取天气地址
     *
     * @param city
     * @return
     */
    public static String getWeatherUrl(String city) {
        return WEATHER_URL + city + WEATHER_END_URL;
    }

    /* 自动更新配置*/
    //fire.im的token
    public static  String API_FIRE_TOKEN = "ad06cab6f35eb810c666a7b3936e9119";
    //fire.im的应用id
    public static  String APP_FIRE_ID = "592e6d2d548b7a1ccf000135";
}
