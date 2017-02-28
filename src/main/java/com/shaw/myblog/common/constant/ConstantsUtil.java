package com.shaw.myblog.common.constant;

public class ConstantsUtil {


    // 系统配置
    public static final String common_config_default_password_key = "sys_boss_password";
    public static final String common_config_default_password_value = "000000";
    public static final String PROTOCOL_PREFIX = "http://";
    public static final String REGEX_SPLIT_DOMAIN= "@";
    
    /*************************** session key, 命名规则建议： SESSION_KEY_XXX ***************************************/
    /*************************** cookie key, 命名规则建议： COOKIE_KEY_XXX ***************************************/
    public static final String COOKIE_KEY_DOMAIN = "_adm";
    /**
     * 商城首页 页眉 导航 页脚 DOM 缓存
     */
    public static final String FRONT_NAV_REDIS_FIELD_KEY = "globalNav";
    public static final String FRONT_FOOTER_REDIS_FIELD_KEY = "globalFooter";
    public static final String FRONT_SHOPVO_REDIS_FIELD_KEY = "globalShopVo";
    public static final String FRONT_STYLE_REDIS_KEY_PREFIX = "shopId_";
    public static final String DOMAIN_CACHE_KEY = "domain_cache";
    /**
     * 详情页描述图片中的oss样式名
     */
    public static final String DETAILS_IMAGE_STYLE = "@!s20";
    public static final String DETAILS_MOBILE_IMAGE_STYLE = "@!m20";
    /**
     * 品牌商城自定义页面 OSS存储 bucketName
     */
    public static final String PREVIEW_BUCKET_NAME = "shopcmd-preview";
    public static final String SPACE_BUCKET_NAME = "vmall-project";
	public static final String MOBILE_VERIFYCODE_KEY = "mobileVerifyCodeKEY";
	public static final int OFFIC_SHOP_CATEGORY_MAX_COUNT = 8;	//官网商城(一级)分类最大个数
	public static final int DMS_HOME_GOODS_MAX_COUNT = 8;		//微分销商城首页一级分类显示商品最大数量
    public static final String isChild = "1"; // 子账户
    
    // ======================redis 实例==========================
    public static final Integer REDIS_SYSCONFIG_INDEX = 0;
    public static final Integer REDIS_SESSION_INDEX = 1;
    public static final Integer REDIS_JSONP_INDEX = 2;
    public static final Integer REDIS_DISPLAY_INDEX = 3;
    public static final Integer REDIS_VERIFYCODE_INDEX = 4;
    public static final Integer REDIS_City_INDEX = 5;
    public static final Integer REDIS_BARGAIN_GOODS_INDEX = 6;// 限时限量商品缓存
    public static final Integer REDIS_BARGAIN_CART_INDEX = 7;// 秒杀，限量活动队列
    public static final Integer REDIS_STYLE_LIBRARY_INDEX = 8;// 样式库
    public static final Integer REDIS_FRONT_DETAILS_GOODS_INDEX = 9;//前端商品详情
    public static final Integer REDIS_FRONT_RECOMMEND_INDEX = 10;// 首页推荐缓存
    public static final Integer REDIS_HEADER_STYLES_INDEX = 11;	//数据中心
    public static final Integer REDIS_DMS_HOME_INDEX = 12;
    public static final Integer REDIS_OFFI_GOODS_DETAIL_INDEX = 13;
    public static final Integer REDIS_INDUSTRY_INDEX = 14;
    //============ ROP config =========================
    public static final String ROP_REDIS_DOMAIN = "vmall_rop_config";
    //============ rabbitmq routing_key config =========================
    public static final String ROUTING_KEY_GOODS_INSERT = "goods.insert";
    public static final String ROUTING_KEY_GOODS_DELETE = "goods.delete";
    public static final String ROUTING_KEY_ORDER_SUBMIT = "order.submit";
	public static final String holder_mark = "^";
	public static final String split_holder_mark = "\\^";
    public static final String VMALL_ID = "vmallId";
    public static final String CASTGC = "_tgc";
    public static final String SERVICE = "_svc";
    public static final String CAS_TGC_PREFIX = "TGT-";
    public static final String CAS_TICKET_PREFIX = "ST-";
    public static final String HTTP_PREFIX = "http://";
    public static final String CASTICKETS_REDIS_KEY = "casTickets";
    public static final String AUTHENTICATED_MEMBERS_REDIS_KEY = "authenticatedMembers";
    public static final String MANAGED_SESSIONSID_REDIS_KEY = "managedSessionsId";
    /**
     * 后台
     */
    public static String BACK_CODE_SESSION_KEY = "web_manager_cpa_key";
    /**
     * boss 后台 验证码
     */
    public static String BOSS_CODE_SESSION_KEY = "web_boss_cap_key";
    
    
    /* ***************************************************************************************************/
    /* ***********************	                            云电商相关 						**********************************/
    /* ****************************************************************************************************/
    /**
     * boss 系统session 用户key
     */
    public static String BOSS_USER_SESSION_KEY = "web_boss_user";
    /**
     * member  Id  user name
     */
    public static String MEMBER_SESSION_KEY = "member_session";
    public static String CORPORATE_CODE_SESSION_KEY = "corporate_code";
    public static String MEMBERID_SESSION_KEY = "member_Id";
    public static String MEMBERNAME_SESSION_KEY = "member_name";
    public static String REMEMBER_ME_KEY = "_mp";
    public static String SERVICE_TICKET = "_st";
    public static String VMALL_DOMAIN_KEY = "vmall_domain";
    /**
     * 购物车cookie 的 name
     */
    public static String SHOPPING_CART_KEY = "shopcmd_cart";
    public static Integer SHOPPING_CART_NUM = 10;
    public static String SHOPPING_CART_REVIEWED_SPLIT = "-";
    /**
     * 热销榜单
     */
    public static Integer HOT_SELL_LIST_SIZE = 5;
    public static Integer HOT_SELL_EXPIRE_TIME = 60 * 60 * 24 * 30;// 一个月
    public static String HOT_SELL_REDIS_KEY = "hot_sell_goods_";
    /**
     * 商城首页 精品推荐缓存
     */
    public static String FRONT_RECOMMEND_HOME_PAGECODE = "index";
    public static String FRONT_RECOMMEND_REDIS_KEY_PREFIX = "frontRec_";
    public static String FRONT_AREA_POSITION_REDIS_KEY_PREFIX = "Area&Position_";
    /**
     * 商城首页 页眉 导航 页脚  默认模版
     */
    public static String PROPETIES_KEY = "baseLocation";
    public static String DEFAULT_MODEL_PATH = "/resources/style_library/ftl/";
    public static String DEFAULT_BASE_PATH = "/resources/style_library/";
    public static String DEFAULT_OSS_BASE_PATH = "resources/style_library/";
    public static String DEFAULT_MODEL_SUFFIX = ".ftl";
    public static String DEFAULT_CSS_SUFFIX = ".css";
    public static String DEFAULT_STYLECODE = "nav01";
    /**
     * 普通验证码(图片)
     */
    public static int ERROR_LOGIN_COOKIE_MAX_AGE = 60 * 60 * 24;
	/**
	 * 普通验证码(图片)
	 */
	public static String SECURITYCODE_KEY = "securityCodeKEY";
	public static String ERROR_LOGIN_COOKIE = "errorLogin";
	public static String ERROR_LOGIN_COOKIE_PATH = "/";
	public static String BACK_USER_SESSION_KEY = "bossUserKey";
	
	
    /*********************************************************************************/
    public static String LOGIN_USER_SESSION_KEY = "loginUserSessionKey";

    // 账号消息
    public abstract class UserMessage {
        public static final String ERROR_USERNAME = "账户名不合法";
        public static final String ERROR_PASSWORD = "密码不合法";
        public static final String ERROR_USERNAM_NOT_EXSIST = "账户名不存在";
        public static final String ERROR_LOGIN_PASSWORD = "密码错误";
        public static final String IS_EXIST = "账号已存在";
        public static final String ERROR_CODE = "验证码错误";
        public static final String ERROR_MOBILE_CODE = "手机验证码错误";
    }
    
    // 通用的消息
    public abstract class CommonMessage {
        public static final String FAILED_MESSAGE = "获取数据失败!"; // 获取数据失败
        public static final String SUCCESS_MESSAGE = "请求数据成功!"; // 获取数据失败
        public static final String ERROR_MESSAGE = "请求数据出错!!"; // 获取数据出错!
        public static final String PARAM_ERROR_MESSAGE = "请求参数传递错误!!"; // 参数传递错误
        public static final String JSON_FORMAT_ERROR = "json格式错误";
        public static final String IS_EXIST = "记录已存在";
    }

    // 账号消息
    public abstract class ShopMessage {
        public static final String ERROR_NAME = "商城名称不得超过5个字";
        public static final String ERROR_SECONDDOMAIN = "二级域名已存在";
        public static final String ERROR_FAVORITETITLE = "收藏夹标题不得超过30个字";
        public static final String ERROR_INTRODUCTION = "网站简介不得超过200个字";
        public static final String ERROR_KEYWORD = "搜索关键字过多";

        public static final String ERROR_SHOPCATEGORY_NAME_NULL = "商城分类名称为空";
        public static final String ERROR_SHOPCATEGORYE_ID_NULL = "商城分类ID为空";
        public static final String ERROR_SHOP_NOT_CREATE = "该用户还未创建商城";
        public static final String ERROR_SHOPCATEGORY_COUNT_EXCEED = "已超过商城分类最大个数";
        public static final String ERROR_SHOPBRAND_CONTENT_NULL = "商城品牌内容为空";
        public static final String ERROR_SHOPBRAND_TYPE_NULL = "商城品牌内容类型为空";
        public static final String ERROR_SHOP_ID_NULL = "商城ID为空";
    }

}
