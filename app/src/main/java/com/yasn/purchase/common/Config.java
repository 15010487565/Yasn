package com.yasn.purchase.common;

/**
 * Author: xcd
 * Time: 17/12/20
 * Email:xcd158454996@163.com
 */
public class Config {


//    public static final String IP = "http://shop.yasn.com:9100/api/composite-service/";//正式
//    public static final String URLCAIGOU = "http://caigou.yasn.com/";//正式服
//    public static final String URL = "http://shop.yasn.com/";//正式服
    public static final String IP = "http://shoptest.yasn.com:9100/api/composite-service/";//测试
    public static final String URLCAIGOU = "http://shoptt.yasn.com/";//测试首服
    public static final String URL = "http://shoptt.yasn.com:81/";//测试首服
    public final static String SHOPPCAR_1 = "http://192.168.50.166:9100/api/composite-service/cart/get-cart-list";//号哥本地
    //欢迎界面图片
    public static final String url = "http://caigou.yasn.com/";//正式服
    //搜索页面
//    public final static String SEARCH = "http://47.93.233.216:9998/search/solr/goods/list?";//正式服
    public final static String SEARCH = "http://47.93.192.134:9998/search/solr/goods/list?";//测试服
    //欢迎界面图片
    public final static String LAUNCHIMAGE = url+"api/base/app/geturl.do";
    //首页
            public static final String HOME = IP+"home/index";
    //分类页面
    public final static String CLASSIFY = IP+"goods/cat/list";
    //发现页面
    public final static String FIND = IP+"discovery/get-discovery-list/";
    //进货单WEB页面
    public final static String SHOPPCARWEBVIEW = URLCAIGOU+"cart.html";
    //进货单
    public final static String SHOPPCAR = IP+"cart/get-cart-list";
    //进货单单择
    public final static String SHOPPCARONECHECK = IP+"cart/check-product?";
    //进货单全部选择
    public final static String SHOPPCARCHECK = IP+"cart/check-all?";
    //进货单删除
    public final static String SHOPPCARDELETECART = IP+"cart/delete-cart?";
    //进货单总价
    public final static String SHOPPCARTOTALPRICE = IP+"cart/get-total-price";
    //进货单修改购物项数量
    public final static String SHOPPCARUPDATENUM = IP+"cart/update-num?";
    //热门搜索
    public final static String OFTENSEARCH = IP+"goods/search/KeyWord";
    //添加搜索字段
    public final static String SYNCHSEARCH = IP+"goods/search/add-search-record/";
    //搜索页面筛选车型
    public final static String SEARCHCARTYPE = IP+"goods/list/carTypes/1";
    //教秘买好和成功案例
    public final static String GOODSDETAILSOTHER = IP+"goods/intro/";
    //门店
    public final static String SHOP = IP+"member/index";
    //门店页面统计数据
    public final static String SHOPSTATISTICS = IP+"order/currentstatistics";
    //获取个人信息
    public final static String GETPERSONAGEINFO = IP+"member/memberInfo";
    //获取进货单数量
    public final static String CARTGOODSNUM = IP+"cart/goodsNum/";
    //加入收藏
    public final static String ADDCOLLECT = IP+"favorite/saveGoodsFavorite/";
    //删除收藏
    public final static String DELETECOLLECT = IP+"favorite/deleteBygoodsId/";
    //加入进货单
    public final static String ADDSHOPCAR = IP+"cart/add-product?";
    //同意邀请
    public final static String AGREEINVITE = IP+"employee/agree";
    //拒绝邀请
    public final static String REFUSEINVITE = IP+"employee/refuse";

    public final static int TYPE_FOOTVIEW = 10000;

    //    public static final String HOMEVIEW = "http://caigou.yasn.com";//首页
    public static final String HOMEVIEW = "http://shoptt.yasn.com/";//测试首页2
    public static final String HOMEVIEWDOMAIN = "caigou.yasn.com";//首页
    public static final String UPDATE = "http://caigou.yasn.com/android/update.txt";//正式更新
    public static final String HOMEVIEW2 = "http://caigou.yasn.com/classify.html";//产品分类
    public static final String HOMEVIEW3 = "http://caigou.yasn.com/cart.html";//购物车
    public static final String HOMEVIEW4 = "http://caigou.yasn.com/member.html";//会员中心

    //登录
    public static final String LOGINWEBVIEW = URLCAIGOU+"login.html";
    //注册
    public static final String REGISTERWEBVIEW = URLCAIGOU+"login_c.html?changehead=home";
    //退出登录
    public static final String LOGINOUTWEBVIEW = URLCAIGOU+"memberSet.html";
    //开通雅森帮
    public static final String DREDGEYASNHELP =  URLCAIGOU+"digital_member.html";
    //雅森帮
    public static final String YASNBANG =  URLCAIGOU+"goods_list2.html?publish_type=1";
    //去认证
    public static final String ATTESTATION =  URLCAIGOU+"verify_register.html";
    //收藏WebView
    public static final String HOMECOLLECT =  URL+"collectList.html";
    //常购清单
    public static final String SHOPLIST =  URL+"shopList.html";
    //我的订单
    public static final String MEORDER =  URLCAIGOU+"orderlist.html";
    //待付款
    public static final String MEORDERUNPAYMENT =  URLCAIGOU+"orderlist.html?status=1";
    //代发货
    public static final String MEORDERUNSHIPMENTS =  URLCAIGOU+"orderlist.html?status=2";
    //待收货
    public static final String MEORDERUNSIGNFOR =  URLCAIGOU+"orderlist.html?status=3";
    //首页更多
    public static final String ONCLICKTABMORE =  URL+"subject.html";
    //详情页
    public final static String GOODSDETAILS = IP+"goods/details/";
    //分享详情页
    public final static String GOODSDETAILSWEB = URL+"goods.html?id=";
    //积分
    public final static String SHOPINTEGRAL = URLCAIGOU+"member_point.html";
    //统计查看更多
    public final static String STATISTICSLOOKALL = URL+"Statistics.html";
    //门店收货地址
    public final static String SHOPPLACEOFRECEIPT  = URLCAIGOU+"addresslist.html?back=1";
    //门店帮助中心
    public final static String SHOPHELP  = URLCAIGOU+"help.html";
    //门店电话咨询
    public final static String SHOPPHONE  = URLCAIGOU+"service.html";
    //门店资质专票
    public final static String SHOPAPTITUDE  = URLCAIGOU+"specialInvoice.html";
    //门店创建员工
    public final static String SHOPCREATESTAFF = URL+"createStaff.html";
    //门店管理员工
    public final static String SHOPMEANAGESTAFF  = URL+"staffManage.html";
    //开通创客
    public final static String MAKERDREDGE  = URL+"ck_ck.html";
    //推广二维码
    public final static String MAKERQRCODE  = URL+"ck_wxcode.html";
    //收款账号
    public final static String MAKERRECEIPTACCOUNT  = URL+"ck_account.html";
    //开拓门店
    public final static String MAKEREXPLOITSHOP  = URL+"ck_addshop.html";
    //门店订单
    public final static String MAKERSHOPORDER  = URL+"ck_shoporder.html";
    //确认订单
    public final static String CHECKOUTSHOPCAR  = URLCAIGOU+"checkout.html";
}
