package com.yasn.purchase.pay;

/**
 * Created by gs on 2018/7/10.
 */

public class PayConfig {

    /**
     * 微信配置
     */
    //微信开放平台审核通过的应用APPID
    public static final String WEIXIN_APP_ID = "wxfaf772b01b93fb55";
    //商户号 微信分配的商户号partnerid
    public static final String MCH_ID = "";
    //  微信 API密钥，在商户平台设置
    public static final  String API_KEY_WEXIN= "";
    //  //写你们的回调地址
    public static final  String CERT_NOTIFYWEXIN= "";
    /**
     * 支付宝配置
     */
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2018042002584802";

    /** 支付宝账户登录授权业务：入参pid值 */
//    public final static String PARTNER = "2088801989346310";
    public static final String PID = "2088801989346310";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "dasdasdadadasdasdads";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public final static String RSA2_PRIVATE ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCegSFFY38X6LG19iLy9DeJkn849lwBZMLq/WSzxtYj5WPQuEEkgt5gQO8Dso3Vjhn/qGZzTJBdRv2+8ldkbahmIVUhltuQzZjqc62AU3UIMPfwcz7+KMXrTE5mv2Kpjw6+8rS2810r1ce7n7cycWqd/yC/8Yy9BQ3NVyUenEV2z89Esz7oZPFAaSQC0YtNFmVeqhu8MCSrL/NyT+RXWt/qln+JaKG/k2fxEWAu7G62I7t2jbla6hhUW1PjQUfO1x2kvII9PgzcdLdiUZI89BcD3yP8jlPMSBHRaDbhjl4zpewYvHdLcQ/jrzcTYJ1zMy5K9mE5cIBQ2uIKevYOvZpTAgMBAAECggEAEyUty5/U/iJRNNViK05sVuWnEE5RlqmBEblBrKVbwEI46EEZPTvBeIbfV+b9UsozQbophcu9tEaa57GF9M96A7wK+F+sbbUnv0nxrwplnHOtvxFsjm6GWYGCBs8fL7Pj6BSQ13hVzLGpynPkQOeKJUNnxUd75Gjz3dWk2imuCkemzPFCj6ImUBJ+vdFfdQIcdMAgu79/ln/h2QA5H2GhhSbkQB6tCd+7J6pAzbrY5LLZ+UayHRczAneyO1C75ZutgRJdUutEyD8aV3z67Xh624fBxC6srDIkXok6h7fR4NbCdDdqPDuDcKu/O2HMaTJyRVqsxgwzO3ZJgZ5V5r9rYQKBgQDYIInbV+pKVinO82337AXMVf5DDHBZqnmGaCNlAChFXgnEZJfsnWvWTSqTVX3hV/vi2PUyskgH5Ay1DxoCAFroLdD5RLSFRzUBhKiT0X+Hy/Dr7ODsnkc392MKYtmpLXm7sMqlpOn0quC7BuU8a+gnhR/rlG5xEa4gUC3giHM0LwKBgQC7vyGDncevxNYzzg8xYMFiF+qqo5T/Muu11wKlYn39jCX5p+PQqz/a4GVMI2+hsRUh1AizCReErYkRV76EmVUr3ib2Xc46gEvqbk36Eli8n3ZDDwQsvw1hZazqwBXNtdIMm15mjaJpWSwkoXgm2YmGEVo08Ssncpw7dPhetNEfHQKBgCWQqkVsp3VaOpQU7ZU8+dkFTVdcDx8WqV66NQS2HUnJSYtQLLPcvMSXzwGix2lmgIpySVQRdsGgWrERueSFKUfXxs28ql/L+6OS6jYfILp6sRpgpsuZyykoH5fLjKeCFvmPhfl6i4uSM9NuJGP3IXfVBLJOr0+J0ikdpouzS8rTAoGAZXpOhBRhHXk4CjJDdf4FKJ8iwO7AFxwP2W6IyJmu3b/FuuZrNZuKXSTSu8+fg/byJCG2PgcZBibCInSZzpQOPDLMPne/g1FbNzA9x3/pJLLQOjjAmer+MXnCd/QxFMpE6XmFEctWULjZKkd4aQ9FzQhOCLWeDKlfFqgHozD6o2UCgYEAmMywOxGj4mMMZsWszQl8ldHYMDLgR1E3S6p4RX0hLheeGhniMrgwp6G15LwdUfcXayDHlScRCzFdA0YhTa9XcuQ3Znm2dAfNs2s8jLBe1L4WsbMyonvR7WkHlEvBANOTORsPNuUiGahvhbsxcAXojpfhtoKaUsZM5GHmD24exao=";
    public final static String RSA_PRIVATE = "";
    // 商户收款账号
    public final static String SELLER = "yasenchepinwang@163.com";
    //支付宝回调地址异步通知
    public final static String CERT_NOTIFY = "";
}
