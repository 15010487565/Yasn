package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/2/2.
 */

public class HotLableModel implements Serializable{

    /**
     * code : 0
     * message : null
     * data : [{"key_word":"11111"},{"key_word":"222"},{"key_word":"315"},{"key_word":"北京"},{"key_word":"tingshuo"},{"key_word":"还好！！！"},{"key_word":"测试"},{"key_word":"烹鱼宴"},{"key_word":"送终鸡"},{"key_word":"王撕葱"},{"key_word":"李易疯"},{"key_word":"忘疯"},{"key_word":"汽油"},{"key_word":"烹鱼宴烹鱼宴烹鱼宴烹鱼宴烹鱼宴烹鱼宴烹鱼宴烹鱼宴烹鱼宴烹鱼宴烹鱼宴烹鱼宴烹鱼宴"},{"key_word":"积分"},{"key_word":"纠结轮"},{"key_word":"22"},{"key_word":"海南一号"},{"key_word":"一月又一月晕"},{"key_word":"反反复复付付付付付付付多"},{"key_word":"123312312"},{"key_word":"还好还好"},{"key_word":"还好"},{"key_word":"@@@"}]
     * totalCount : 0
     */

    private int code;
    private Object message;
    private int totalCount;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * key_word : 11111
         */

        private String key_word;

        public String getKey_word() {
            return key_word;
        }

        public void setKey_word(String key_word) {
            this.key_word = key_word;
        }
    }
}
