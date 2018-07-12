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
//    public final static String IPPORTPAY = "http://39.106.213.117:10800/";//测试服支付接口
//    public static final String IP = "http://192.168.50.166";//本地测试
    public static final String IP = "http://shoptest.yasn.com";//测试
    public static final String URLCAIGOU = "http://shoptt.yasn.com/";//测试首服
//    public static final String URLCAIGOU81 = "http://shoptt.yasn.com:81/";//测试首服
    public final static String SEARCH = "http://47.93.192.134:9998/search/solr/goods/list?";//测试服搜索页面
    public final static String IPPORTPAY = "http://47.93.192.134:10800/";//测试服支付接口
    //端口
    public static final String IPPORT = IP+":9100/api/composite-service/";//9100端口
    //欢迎界面图片
    public final static String LAUNCHIMAGE = URLCAIGOU+"api/base/app/geturl.do";
    //城市列表
    public static final String REGIONLIST = IPPORT +"region/listRegions?";
    //首页
    public static final String HOME = IPPORT +"home/index";
    //首页更多
    public static final String ONCLICKTABMORE =  IPPORT+"goods/subject?";
    //分类页面
    public final static String CLASSIFY = IPPORT +"goods/cat/list";
    //分类页面推荐品牌
    public final static String CLASSIFYBRAND = IPPORT +"goods//brand/list";
    //发现页面
    public final static String FIND = IPPORT +"discovery/get-discovery-list/";
    //进货单WEB页面
    public final static String SHOPPCARWEBVIEW = URLCAIGOU+"cart.html";
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
    //结算按钮创建订单
    public final static String SHOPPCARCREATEORDER = IPPORT +"order/create";
    //发票获取接
    public final static String GETINVOICE = IPPORT +"order/get-invoice/";
    //发票获取接
    public final static String SAVERECEIPT = IPPORT +"order/save-invoice";
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
    //门店页面统计数据
    public final static String SHOPSTATISTICS = IPPORT +"order/currentstatistics";
    //获取个人信息
    public final static String GETPERSONAGEINFO = IPPORT +"member/memberInfo";
    //获取进货单数量
    public final static String CARTGOODSNUM = IPPORT +"cart/goodsNum/";
    //加入收藏
    public final static String ADDCOLLECT = IPPORT +"favorite/saveGoodsFavorite/";
    //删除收藏
    public final static String DELETECOLLECT = IPPORT +"favorite/deleteBygoodsId/";
    //加入进货单
    public final static String ADDSHOPCAR = IPPORT +"cart/add-product?";
    //同意邀请
    public final static String AGREEINVITE = IPPORT +"employee/agree";
    //拒绝邀请
    public final static String REFUSEINVITE = IPPORT +"employee/refuse";
    //查看物流
    public final static String ORDERLOOKDISTR = IPPORT +"order/deliverys/";
    public final static String ORDERQUERY = "https://poll.kuaidi100.com/poll/query.do";
    //支付列表接口
    public final static String PAYTYPE = IPPORTPAY +"payment-cfg/list-by-client-type";
    //支付
    public static final String PAY = IPPORTPAY+"pay/get-pay-html-info?";
    public final static int TYPE_FOOTVIEW = 10000;

    //    public static final String HOMEVIEW = "http://caigou.yasn.com";//首页
    public static final String HOMEVIEW = "http://shoptt.yasn.com:81/";//测试首页2
    public static final String HOMEVIEWDOMAIN = "caigou.yasn.com";//首页
    public static final String UPDATE = "http://caigou.yasn.com/android/update.txt";//正式更新
    public static final String HOMEVIEW2 = "http://caigou.yasn.com/classify.html";//产品分类
    public static final String HOMEVIEW3 = "http://caigou.yasn.com/cart.html";//购物车
    public static final String HOMEVIEW4 = "http://caigou.yasn.com/member.html";//会员中心

    //登录
//    public static final String LOGINWEBVIEW = URLCAIGOU+"login.html";
    public static final String LOGINWEBVIEW = URLCAIGOU+"login.html";
    //注册
    public static final String REGISTERWEBVIEW = URLCAIGOU+"login_c.html?key=register";
    //退出登录
    public static final String LOGINOUTWEBVIEW = URLCAIGOU+"memberSet.html";
    //开通雅森帮
    public static final String DREDGEYASNHELP =  URLCAIGOU+"digital_member.html";
    //雅森帮
    public static final String YASNBANG =  IP+"/yasn.html?publish_type=1";
    //去认证
    public static final String ATTESTATION =  URLCAIGOU+"verify_register.html";
    //收藏WebView
    public static final String HOMECOLLECT =  IP+"/collectList.html";
    //常购清单
    public static final String SHOPLIST =  IP+"/shopList.html";
    //我的订单
    public static final String MEORDERWEB =  URLCAIGOU+"orderlist.html";
    public static final String MEORDER =  IPPORT +"order/list?";
    //订单详情
    public static final String ORDERDETAILS =  IPPORT +"order/details?";
    //取消订单
    public static final String ORDERCANCEL =  IPPORT +"order/cancel?";
    //订单提醒
    public static final String ORDERHURRY =  IPPORT +"order/hurry/";
    //确认收货
    public static final String ORDERCONFIRM =  IPPORT +"order/rogConfirm/";
    //待付款
    public static final String MEORDERUNPAYMENTWEB =  URLCAIGOU+"orderlist.html?status=1";
    //代发货
    public static final String MEORDERUNSHIPMENTSWEB =  URLCAIGOU+"orderlist.html?status=2";
    //待收货
    public static final String MEORDERUNSIGNFORWEB =  URLCAIGOU+"orderlist.html?status=3";
    //首页更多
//    public static final String ONCLICKTABMORE =  IP+"/subject.html";
    //详情页
    public final static String GOODSDETAILS = IPPORT +"goods/details/";
    //分享详情页
    public final static String GOODSDETAILSWEB = IP+"/goods.html?id=";
    //积分
    public final static String SHOPINTEGRAL = URLCAIGOU+"member_point.html";
    //统计查看更多
    public final static String STATISTICSLOOKALL = IP+"/Statistics.html";
    //门店收货地址
    public final static String SHOPPLACEOFRECEIPT  = IP+"/address_list.html?back=1";
    //门店帮助中心
    public final static String SHOPHELP  = URLCAIGOU+"help.html";
    //门店电话咨询
    public final static String SHOPPHONE  = URLCAIGOU+"service.html";
    //门店资质专票
    public final static String SHOPAPTITUDE  = URLCAIGOU+"specialInvoice.html";
    //门店创建员工
    public final static String SHOPCREATESTAFF = IP+"/createStaff.html";
    //门店管理员工
    public final static String SHOPMEANAGESTAFF  = IP+"/staffManage.html";
    //开通创客
    public final static String MAKERDREDGE  = IP+"/ck_ck.html";
    //推广二维码
    public final static String MAKERQRCODE  = IP+"/ck_wxcode.html";
    //收款账号
    public final static String MAKERRECEIPTACCOUNT  = IP+"/ck_account.html";
    //开拓门店
    public final static String MAKEREXPLOITSHOP  = IP+"/ck_addshop.html";
    //门店订单
    public final static String MAKERSHOPORDER  = IP+"/ck_shoporder.html";
    //确认订单
    public final static String CHECKOUTSHOPCAR  = URLCAIGOU+"checkout.html";
    //去凑单
    public final static String SHOPCARADDONITEM  = URLCAIGOU+"collecting_home.html?show_c=4&store_id=1&keyword=&end_price=&key=2&order=asc&start_price=";

}
