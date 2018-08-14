package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2018/8/2.
 */

public class MakerShroffAccModel implements Serializable{

    /**
     * code : 0
     * message : null
     * data : {"page":null,"pageSize":null,"id":3,"memberId":null,"name":"雅森国际","taxpayerNum":null,"address":null,"mobile":null,"bankDeposit":"中国银行","accountNum":"12345678901234567890","type":2,"createTime":1519623229}
     * totalCount : 0
     * success : true
     */

    private int code;
    private Object message;
    private DataBean data;
    private int totalCount;
    private boolean success;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * page : null
         * pageSize : null
         * id : 3
         * memberId : null
         * name : 雅森国际
         * taxpayerNum : null
         * address : null
         * mobile : null
         * bankDeposit : 中国银行
         * accountNum : 12345678901234567890
         * type : 2
         * createTime : 1519623229
         */

        private Object page;
        private Object pageSize;
        private int id;
        private Object memberId;
        private String name;
        private String taxpayerNum;
        private String address;
        private String mobile;
        private String bankDeposit;
        private String accountNum;
        private int type;
        private int createTime;

        public Object getPage() {
            return page;
        }

        public void setPage(Object page) {
            this.page = page;
        }

        public Object getPageSize() {
            return pageSize;
        }

        public void setPageSize(Object pageSize) {
            this.pageSize = pageSize;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getMemberId() {
            return memberId;
        }

        public void setMemberId(Object memberId) {
            this.memberId = memberId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTaxpayerNum() {
            return taxpayerNum;
        }

        public void setTaxpayerNum(String taxpayerNum) {
            this.taxpayerNum = taxpayerNum;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBankDeposit() {
            return bankDeposit;
        }

        public void setBankDeposit(String bankDeposit) {
            this.bankDeposit = bankDeposit;
        }

        public String getAccountNum() {
            return accountNum;
        }

        public void setAccountNum(String accountNum) {
            this.accountNum = accountNum;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }
    }
}
