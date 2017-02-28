define(function () {
	  var global = (function () {
          return this;
      })(),

      location = global.location,
      localStorage = global.localStorage,
      apiHost = location.protocol + '//' + location.host+"/myblog-webapp";
      imageHost = 'http://vmall-project.img-cn-hangzhou.aliyuncs.com/',
      webNotify = 'http://notify.shopcmd.com',
      webHost = "http://localhost:8080/shopcmd-vmall-portal";
      passportHost = "http://localhost:8080/shopcmd-vmall-passport";
      searchHost = "http://localhost:8080/shopcmd-web-search";
      detailsHost = "http://localhost:8080/shopcmd-web-details";


  var URLS = {
      common: {
          C1001: apiHost + "/common/captcha.shtml",				//图片验证码
         
          C1002: apiHost + "/common/getSmsCode.shtml",			//检测手机号是否注册&短信验证码
//        C1003: webNotify + "/notify/getLog.do",					//获取物流信息
          C1004: apiHost + "/common/LogicList.shtml",				//获取物流公司信息
          C1005: apiHost + "/common/getProvinceList.shtml",		//获取省
          C1006: apiHost + "/common/getCityList.shtml",			//获取市
          C1007: apiHost + "/common/getAreaList.shtml",			//获取区
          C1008: apiHost + "/common/uploadImage.shtml",			//图片上传
          C1009: apiHost + "/common/deleteImage.shtml",
          C1010: apiHost + "/common/vldCaptch.shtml",			//图片验证码验证
      },
      passport: {
          P1001: apiHost + "/passport/login.shtml",				//登录
          P1002: apiHost + "/passport/resetPwd.shtml",			//密码重置
          P1003: apiHost + "/passport/isPinEngaged.shtml",		//判断用户
          P1004: apiHost + "/passport/register.shtml",			//注册用户
          P1005: apiHost + "/passport/logout.shtml",			//退出
          P1006: apiHost + "/passport/getMemberName.shtml",	//获取登录用户名信息
          P1007: apiHost + "/passport/tologin.shtml",			//跳转到登录页面
          P1008: apiHost + "/passport/regist/agree.shtml",		//获取注册协议
          P1009: apiHost + "/passport/isPhoneExit.shtml",		//检测手机号是否注册
      },
      user: {
          U1001: apiHost + "/user/order/getOrderListByPage.shtml",	//订单分页
          U1002: apiHost + "/user/order/sureDelivery.shtml",	//确认收货
          U1003: apiHost + "/user/order/getGoodsScore.shtml",	//获得评分
          U1004: apiHost + "/user/order/comment.shtml",	//评论
          U1005: apiHost + "/user/order/getShopReturnInfo.shtml",	//获取退货信息
          U1006: apiHost + "/user/order/returnGoods.shtml",	//退货
          U1007: apiHost + "/user/order/cancelOrder.shtml",	//取消订单
          U1008: apiHost + "/user/order/toDetail.shtml",	//订单详情页
          U1009: apiHost + "/user/account/refund.shtml",	//退款相关
          U1010: apiHost + "/user/account/getMemberById.shtml",	//获取会员相关信息
          U1011: apiHost + "/user/account/recharge.shtml",	//会员充值
          U1012: apiHost + "/user/account/toZhifubao.shtml",	//跳转至支付宝
          U1013: apiHost + "/user/account/getMonthAccountRecord.shtml",	//获取一个月的记录
          U1014: apiHost + "/user/account/updatePassword.shtml",	//修改密码
          U1015: apiHost + "/user/order/deleteOrder.shtml",	//删除订单
          U1016: apiHost + "/user/order/returnApply.shtml",	//退货申请
          U1017: apiHost + "/user/order/getBuyerOrderInfoByOgrId.shtml",	//获取订单信息(包括单品信息和卖家退货地址信息)
          U1018: apiHost + "/user/order/getCommentInfo.shtml",		//获取商品点评信息
          U1019: apiHost + "/user/order/getBuyerCommentByPage.shtml",		//分页获取商品评论信息
          U1020: apiHost + "/user/order/cancelApply.shtml",		//取消退货申请
      },
  };

	
    return {
    	global: global,
        location: location,
        localStorage: localStorage,
        imageHost: imageHost,
        URLS: URLS,
        webHost: webHost
    }
});