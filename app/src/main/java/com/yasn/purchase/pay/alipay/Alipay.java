package com.yasn.purchase.pay.alipay;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class Alipay {
	
//	/** 支付宝支付业务：入参app_id */
//	public static final String APPID = "2018042002584802";
//
//	/** 支付宝账户登录授权业务：入参pid值 */
//	public static final String PID = "2088801989346310";
//	/** 支付宝账户登录授权业务：入参target_id值 */
//	public static final String TARGET_ID = "dasdasdadadasdasdads";
//
//	/** 商户私钥，pkcs8格式 */
//	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
//	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
//	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
//	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
//	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
//	public static final String RSA_PRIVATE = "";
//	public final static String RSA2_PRIVATE ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCegSFFY38X6LG19iLy9DeJkn849lwBZMLq/WSzxtYj5WPQuEEkgt5gQO8Dso3Vjhn/qGZzTJBdRv2+8ldkbahmIVUhltuQzZjqc62AU3UIMPfwcz7+KMXrTE5mv2Kpjw6+8rS2810r1ce7n7cycWqd/yC/8Yy9BQ3NVyUenEV2z89Esz7oZPFAaSQC0YtNFmVeqhu8MCSrL/NyT+RXWt/qln+JaKG/k2fxEWAu7G62I7t2jbla6hhUW1PjQUfO1x2kvII9PgzcdLdiUZI89BcD3yP8jlPMSBHRaDbhjl4zpewYvHdLcQ/jrzcTYJ1zMy5K9mE5cIBQ2uIKevYOvZpTAgMBAAECggEAEyUty5/U/iJRNNViK05sVuWnEE5RlqmBEblBrKVbwEI46EEZPTvBeIbfV+b9UsozQbophcu9tEaa57GF9M96A7wK+F+sbbUnv0nxrwplnHOtvxFsjm6GWYGCBs8fL7Pj6BSQ13hVzLGpynPkQOeKJUNnxUd75Gjz3dWk2imuCkemzPFCj6ImUBJ+vdFfdQIcdMAgu79/ln/h2QA5H2GhhSbkQB6tCd+7J6pAzbrY5LLZ+UayHRczAneyO1C75ZutgRJdUutEyD8aV3z67Xh624fBxC6srDIkXok6h7fR4NbCdDdqPDuDcKu/O2HMaTJyRVqsxgwzO3ZJgZ5V5r9rYQKBgQDYIInbV+pKVinO82337AXMVf5DDHBZqnmGaCNlAChFXgnEZJfsnWvWTSqTVX3hV/vi2PUyskgH5Ay1DxoCAFroLdD5RLSFRzUBhKiT0X+Hy/Dr7ODsnkc392MKYtmpLXm7sMqlpOn0quC7BuU8a+gnhR/rlG5xEa4gUC3giHM0LwKBgQC7vyGDncevxNYzzg8xYMFiF+qqo5T/Muu11wKlYn39jCX5p+PQqz/a4GVMI2+hsRUh1AizCReErYkRV76EmVUr3ib2Xc46gEvqbk36Eli8n3ZDDwQsvw1hZazqwBXNtdIMm15mjaJpWSwkoXgm2YmGEVo08Ssncpw7dPhetNEfHQKBgCWQqkVsp3VaOpQU7ZU8+dkFTVdcDx8WqV66NQS2HUnJSYtQLLPcvMSXzwGix2lmgIpySVQRdsGgWrERueSFKUfXxs28ql/L+6OS6jYfILp6sRpgpsuZyykoH5fLjKeCFvmPhfl6i4uSM9NuJGP3IXfVBLJOr0+J0ikdpouzS8rTAoGAZXpOhBRhHXk4CjJDdf4FKJ8iwO7AFxwP2W6IyJmu3b/FuuZrNZuKXSTSu8+fg/byJCG2PgcZBibCInSZzpQOPDLMPne/g1FbNzA9x3/pJLLQOjjAmer+MXnCd/QxFMpE6XmFEctWULjZKkd4aQ9FzQhOCLWeDKlfFqgHozD6o2UCgYEAmMywOxGj4mMMZsWszQl8ldHYMDLgR1E3S6p4RX0hLheeGhniMrgwp6G15LwdUfcXayDHlScRCzFdA0YhTa9XcuQ3Znm2dAfNs2s8jLBe1L4WsbMyonvR7WkHlEvBANOTORsPNuUiGahvhbsxcAXojpfhtoKaUsZM5GHmD24exao=";
//	private static final int SDK_PAY_FLAG = 1;
//	private static final int SDK_AUTH_FLAG = 2;
//
//	private Context context;
//
//	public Alipay(Context context) {
//		this.context = context;
//	}

//	@SuppressLint("HandlerLeak")
//	private Handler mHandler = new Handler() {
//		@SuppressWarnings("unused")
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case SDK_PAY_FLAG: {
//				@SuppressWarnings("unchecked")
//				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//				/**
//				 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//				 */
//				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//				String resultStatus = payResult.getResultStatus();
//				// 判断resultStatus 为9000则代表支付成功
//				if (TextUtils.equals(resultStatus, "9000")) {
//					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//					Toast.makeText(Alipay.this, "支付成功", Toast.LENGTH_SHORT).show();
//				} else {
//					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//					Toast.makeText(Alipay.this, "支付失败", Toast.LENGTH_SHORT).show();
//				}
//				break;
//			}
//			case SDK_AUTH_FLAG: {
//				@SuppressWarnings("unchecked")
//				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//				String resultStatus = authResult.getResultStatus();
//
//				// 判断resultStatus 为“9000”且result_code
//				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//					// 获取alipay_open_id，调支付时作为参数extern_token 的value
//					// 传入，则支付账户为该授权账户
//					Toast.makeText(Alipay.this,
//							"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//							.show();
//				} else {
//					// 其他状态值则为授权失败
//					Toast.makeText(Alipay.this,
//							"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
//
//				}
//				break;
//			}
//			default:
//				break;
//			}
//		};
//	};

//	/**
//	 * 支付宝支付业务
//	 */
//	public void payV2() {
//
//		/**
//		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//		 *
//		 * orderInfo的获取必须来自服务端；
//		 */
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//		final String orderInfo = orderParam + "&" + sign;
//
//		Runnable payRunnable = new Runnable() {
//
//			@Override
//			public void run() {
//				PayTask alipay = new PayTask(Alipay.this);
//				Map<String, String> result = alipay.payV2(orderInfo, true);
//				Log.i("msp", result.toString());
//
//				Message msg = new Message();
//				msg.what = SDK_PAY_FLAG;
//				msg.obj = result;
//				mHandler.sendMessage(msg);
//			}
//		};
//
//		Thread payThread = new Thread(payRunnable);
//		payThread.start();
//	}
//
//	/**
//	 * 支付宝账户授权业务
//	 *
//	 * @param v
//	 */
//	public void authV2(View v) {
//		if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
//				|| (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
//				|| TextUtils.isEmpty(TARGET_ID)) {
//			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
//					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialoginterface, int i) {
//						}
//					}).show();
//			return;
//		}
//
//		/**
//		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//		 *
//		 * authInfo的获取必须来自服务端；
//		 */
//		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
//		String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
//
//		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//		String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
//		final String authInfo = info + "&" + sign;
//		Runnable authRunnable = new Runnable() {
//
//			@Override
//			public void run() {
//				// 构造AuthTask 对象
//				AuthTask authTask = new AuthTask(Alipay.this);
//				// 调用授权接口，获取授权结果
//				Map<String, String> result = authTask.authV2(authInfo, true);
//
//				Message msg = new Message();
//				msg.what = SDK_AUTH_FLAG;
//				msg.obj = result;
//				mHandler.sendMessage(msg);
//			}
//		};
//
//		// 必须异步调用
//		Thread authThread = new Thread(authRunnable);
//		authThread.start();
//	}
	
	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
//	public void getSDKVersion() {
//		PayTask payTask = new PayTask(this);
//		String version = payTask.getVersion();
//		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
//	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
//	public void h5Pay(View v) {
//		Intent intent = new Intent(this, H5PayDemoActivity.class);
//		Bundle extras = new Bundle();
//		/**
//		 * url 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
//		 *
//		 * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
//		 * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
//		 * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
//		 * 进行测试。
//		 *
//		 * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
//		 * 可以参考它实现自定义的 URL 拦截逻辑。
//		 */
//		String url = "http://m.taobao.com";
//		extras.putString("url", url);
//		intent.putExtras(extras);
//		startActivity(intent);
//	}

}
