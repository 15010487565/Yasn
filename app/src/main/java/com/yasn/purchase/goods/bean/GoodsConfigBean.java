package com.yasn.purchase.goods.bean;

/**
 * 商品信息中的规格参数bean
 */
public class GoodsConfigBean {
    
    public int keyPropId;
    public String keyProp;
    public int valueId;
    public String value;

    public GoodsConfigBean(String value, String keyProp) {
        this.value = value;
        this.keyProp = keyProp;
    }

    public GoodsConfigBean(int keyPropId, String keyProp, int valueId, String value) {
        this.keyPropId = keyPropId;
        this.keyProp = keyProp;
        this.valueId = valueId;
        this.value = value;
    }

    
}
