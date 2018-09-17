package com.yasn.purchase.common;

/**
 * Author: xcd
 * Time: 17/12/20
 * Email:xcd158454996@163.com
 */
public class Config {

//    public static final String IP = "http://shop.yasn.com";//正式
//    public static final String URLCAIGOU = "http://caigou.yasn.com/";//正式服
//    public final static String SEARCH = "http://47.93.233.216:9998/search/solr/goods/list?";//正式服搜索页面
//    public final static String IPPORTPAY = "http://39.106.213.117:10800/";//正式服支付接口
//    public static final String APP_ID = "2b3b07943f";// 替换成bugly上注册的appid

    public static final String IP = "http://shoptest.yasn.com";//测试
    public static final String URLCAIGOU = "http://shoptt.yasn.com";//测试首服
    public final static String SEARCH = "http://47.93.192.134:9998/search/solr/goods/list?";//测试服搜索页面
    public final static String IPPORTPAY = "http://47.93.192.134:10800/";//测试服支付接口
    public static final String APP_ID = "4408947e15";  // 测试appid
    //端口
    public static final String IPPORT = IP+":9100/api/composite-service/";//9100端口
    //城市列表所在地起
    public static final String REGIONLIST = IPPORT +"region/listRegions?";
    //首页
    public static final String HOME = IPPORT +"home/index";
    //首页更多
    public static final String ONCLICKTABMORE =  IPPORT+"goods/subject?";
    //加入收藏
    public final static String ADDCOLLECT = IPPORT +"favorite/saveGoodsFavorite/";
    //删除收藏
    public final static String DELETECOLLECT = IPPORT +"favorite/deleteBygoodsId/";
    //收藏删除全部
    public static final String COLLECTDELETEALL =  IPPORT+"favorite/deleteByMemberId";
    //收藏列表
    public static final String COLLECT =  IPPORT+"favorite/listGoodsFavoriteByMemberId?";
    //常购清单
//    public static final String SHOPLIST =  URLCAIGOU+"/shopList.html";
    public static final String SHOPLIST =  IPPORT+"order/regularpurchase";
    public static final String SHOPLISTGET =  IPPORT+"order/getRegularpurchase?";
    //分类页面
    public final static String CLASSIFY = IPPORT +"goods/cat/list";
    //分类页面推荐品牌
    public final static String CLASSIFYBRAND = IPPORT +"goods//brand/list";
    //发现页面
    public final static String FIND = IPPORT +"discovery/get-discovery-list/";
    //进货单WEB页面
//    public final static String SHOPPCARWEBVIEW = URLCAIGOU+"/cart.html";
    //进货单阶梯价
    public final static String SHOPPCARWHOLELIST = IPPORT +"cart/get-whole-list";
    //进货单
    public final static String SHOPPCAR = IPPORT +"cart/get-cart-list";
    //进货单单择
    public final static String SHOPPCARONECHECK = IPPORT +"cart/check-cancel?";
    //结算页前判断是否有失效商品
    public final static String SHOPPCARINVALIDGOODS = IPPORT +"cart/invalid-goods";
    //收货地址
    public final static String ADDRESS = IPPORT +"MemberAddress/list";
    //当前订单使⽤用的收货地址
    public final static String ADDRESSNOW = IPPORT +"MemberAddress/update-in-use/";
    //默认地址收货地址
    public final static String ADDRESSDEFAULT  = IPPORT +"MemberAddress/isdefaddr/";
    //当删除收货地址
    public final static String ADDRESSNOWDELETE = IPPORT +"MemberAddress/delete/";
    //新增或编辑收货地址
    public final static String  ADDRESSUPDATA= IPPORT +"MemberAddress/editOrAdd/";
    //进货单结算页
    public final static String SHOPPCARCLOSEANACCOUNT = IPPORT +"order/list-to-create-orders";
    //积分抵现接
    public final static String SHOPPCARUSEPOINT = IPPORT +"order/list-to-create-orders/use-point/";
    //结算按钮创建订单
    public final static String SHOPPCARCREATEORDER = IPPORT +"order/create";
    //发票获取接
    public final static String GETINVOICE = IPPORT +"order/get-invoice/";
    //发票获取接
    public final static String SAVERECEIPT = IPPORT +"order/save-invoice";
    //门片专票资质
    public final static String SHOPINVOICE = IPPORT +"Invoice/changeInvoice?";
    //门片获取专票资质
    public final static String SHOPGETINVOICE = IPPORT +"Invoice/getInvoice";
    //进货单删除
    public final static String SHOPPCARDELETECART = IPPORT +"cart/delete-cart?";
    //进货单总价
    public final static String SHOPPCARTOTALPRICE = IPPORT +"cart/get-total-price";
    //进货单修改购物项数量
    public final static String SHOPPCARUPDATENUM = IPPORT +"cart/update-num?";
    //热门搜索
    public final static String OFTENSEARCH = IPPORT +"goods/search/KeyWord";
    //添加搜索字段
    public final static String SYNCHSEARCH = IPPORT +"goods/search/add-search-record/";
    //搜索页面筛选车型
    public final static String SEARCHCARTYPE = IPPORT +"goods/list/carTypes/1";
    //教秘买好和成功案例
    public final static String GOODSDETAILSOTHER = IPPORT +"goods/intro/";
    //门店
    public final static String SHOP = IPPORT +"member/index";
    //门店积分明细
    public final static String SHOPINTEGALDETAILS = IPPORT +"member/getPointDetails/";
    //门店冻结积分
    public final static String SHOPINTEGALFREEZE = IPPORT +"member/getPointFreeze/";
    //门店页面统计数据
    public final static String SHOPSTATISTICS = IPPORT +"order/statistics?";
    //用油查询联动
    public final static String SHOPOIL = IPPORT +"rowe/params/";
    //用油查询
    public final static String SHOPOILQUERY = IPPORT +"rowe/";
    //获取个人信息
    public final static String GETPERSONAGEINFO = IPPORT +"member/memberInfo";
    //获取进货单数量
    public final static String CARTGOODSNUM = IPPORT +"cart/goodsNum/";
    //加入进货单
    public final static String ADDSHOPCAR = IPPORT +"cart/add-product?";
    //创建员工
    public final static String STAFFCREATE = IPPORT +"/employee/sendMessage/";
    //管理员工列表
    public final static String STAFFMESSAGE = IPPORT +"employee/listEmployee";
    //修改管理员工
    public final static String STAFFMESSAGEUPDATE = IPPORT +"employee/updateEmployee/";
    //删除员工
    public final static String STAFFMESSAGEDELETE = IPPORT +"employee/deleteEmployee/";
    //同意邀请
    public final static String AGREEINVITE = IPPORT +"employee/agree";
    //拒绝邀请
    public final static String REFUSEINVITE = IPPORT +"employee/refuse";
    //查看物流
    public final static String ORDERLOOKDISTR = IPPORT +"order/deliverys/";
    //支付列表接口
    public final static String PAYTYPE = IPPORTPAY +"payment-cfg/list-by-client-type";
    //支付
    public static final String PAY = IPPORTPAY+"pay/get-pay-html-info?";
    //验证码
    public static final String VERIFICATIONCODE = IPPORT+"member/getRandomCode";
    //登录
    public static final String LOGIN = IPPORT+"member/login";
    //快速注册检测手机号是否已注册
    public static final String DETECTIONPHONE = IPPORT+"member/checkMobile/";
    //快速注册获取验证码
    public static final String SENDPHONECODE = IPPORT+"member/sendSmsCode/";
    //快速注册校验手机验证码
    public static final String REGISTERQUICKDETECTIONPHONECODE = IPPORT+"member/checkCode/";
    //快速注册
    public static final String REGISTER = IPPORT+"member/regMobile";
    //重置忘记密码
    public static final String RESETPASSWORD = IPPORT+"member/resetPassword";
    //修改密码
    public static final String UPDATAPASSWORD = IPPORT+"member/changePassword";
    //获取认证信息
    public static final String AUTHORMEMBERINFO =  IPPORT+"member/getMemberDataInfo";
    //提交认证基本信息
    public static final String AUTHORMEMBERSUBMIT =  IPPORT+"member/changeRolePartOne";
    //检查注册的会员是否直接审核
    public static final String AUTHORMEMBERISAUDIT =  IPPORT+"member/ifCanApprove";
    //提交认证基本信息
    public static final String AUTHORMEMBERSUBMITIMAGE =  IPPORT+"member/changeRolePartTwo";
    //我的订单
    public static final String MEORDER =  IPPORT +"order/list?";
    //订单详情
    public static final String ORDERDETAILS =  IPPORT +"order/details?";
    //多买多送赠品
    public static final String ORDERDETAILSGIFT = IPPORT + "goods/activityGift/";
    //取消订单
    public static final String ORDERCANCEL =  IPPORT +"order/cancel?";
    //订单提醒
    public static final String ORDERHURRY =  IPPORT +"order/hurry/";
    //确认收货
    public static final String ORDERCONFIRM =  IPPORT +"order/rogConfirm/";
    //详情页
    public final static String GOODSDETAILS = IPPORT +"goods/details/";
    //推广二维码
    public final static String MAKERQRCODE  = IPPORT+"qrcode/maker";
    //获取收款帐号信息
    public final static String GETMAKERRECEIPTACCOUNT  = IPPORT+"createGuest/getProceedsInfor";
    //提价收款信息
    public final static String SUBMITMAKERRECEIPTACCOUNT  = IPPORT+"createGuest/insertProceeds?";
    //开拓门店
    public final static String MAKEREXPLOITSHOPAPP  = IPPORT+"createGuest/exploreStoresList/";
    //门店订单
    public final static String MAKERSHOPORDERAPP  = IPPORT+"order/createGuest/";
    //帮助中心
    public final static String HELP  = IPPORT+"member/help-data";

    //登录
    public static final String LOGINWEBVIEW = URLCAIGOU+"/login.html";
    //开通雅森帮
    public static final String DREDGEYASNHELP =  URLCAIGOU+"/digital_member.html";
    //雅森帮
    public static final String YASNBANG =  URLCAIGOU+"/yasn.html?publish_type=1";
    //分享详情页
    public final static String GOODSDETAILSWEB = URLCAIGOU+"/goods.html?id=";
    //欢迎界面图片
    public final static String LAUNCHIMAGE = URLCAIGOU+":8080/api/base/app/geturl.do";
    //去凑单
//    public final static String SHOPCARADDONITEM  = URLCAIGOU+"/collecting_home.html?show_c=4&store_id=1&keyword=&end_price=&key=2&order=asc&start_price=";
    //创客支付
    public final static String MAKERPAYMENT = URLCAIGOU+"/ck_payment.html";
    //订单支付
    public final static String ORDERPAY = URLCAIGOU+"/payment.html?orderid=";
    //是否使用网页支付
    public final static boolean isWebViewPay = true;

    //测试首页2
    public static final String HOMEVIEW = URLCAIGOU+"/";
    //用于处理底部导航回退
    public static final String HOMEVIEWDOMAIN = "caigou.yasn.com";//首页
    public static final String HOMEVIEW2 = URLCAIGOU+"/classify.html";//产品分类
    public static final String HOMEVIEW3 = URLCAIGOU+"/cart.html";//购物车
    public static final String HOMEVIEW4 = URLCAIGOU+"/member.html";//会员中心
}
