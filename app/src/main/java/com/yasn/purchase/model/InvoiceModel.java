package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2018/7/9.
 */

public class InvoiceModel implements Serializable{


    /**
     * code : 0
     * message : null
     * data : {"invoiceId":27,"memberId":24640,"title":"666666666666666","invoiceNum":"1111111111111111","invoiceAddress":"88888888888888","invoiceMobile":"15178885555","invoiceBank":"asdfasfadfasfasdfad","invoiceBankNum":"213","invoiceType":null,"invoiceStatus":0,"noApproval":"1111111ssssss"}
     * totalCount : 0
     * success : false
     */

    private int code;
    private String message;
    private DataBean data;
    private int totalCount;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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
         * invoiceId : 27
         * memberId : 24640
         * title : 666666666666666
         * invoiceNum : 1111111111111111
         * invoiceAddress : 88888888888888
         * invoiceMobile : 15178885555
         * invoiceBank : asdfasfadfasfasdfad
         * invoiceBankNum : 213
         * invoiceType : null
         * invoiceStatus : 0
         * noApproval : 1111111ssssss
         */

        private int invoiceId;
        private int memberId;
        private String title;
        private String invoiceNum;
        private String invoiceAddress;
        private String invoiceMobile;
        private String invoiceBank;
        private String invoiceBankNum;
        private Object invoiceType;
        private int invoiceStatus;
        private String noApproval;

        public int getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(int invoiceId) {
            this.invoiceId = invoiceId;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getInvoiceNum() {
            return invoiceNum;
        }

        public void setInvoiceNum(String invoiceNum) {
            this.invoiceNum = invoiceNum;
        }

        public String getInvoiceAddress() {
            return invoiceAddress;
        }

        public void setInvoiceAddress(String invoiceAddress) {
            this.invoiceAddress = invoiceAddress;
        }

        public String getInvoiceMobile() {
            return invoiceMobile;
        }

        public void setInvoiceMobile(String invoiceMobile) {
            this.invoiceMobile = invoiceMobile;
        }

        public String getInvoiceBank() {
            return invoiceBank;
        }

        public void setInvoiceBank(String invoiceBank) {
            this.invoiceBank = invoiceBank;
        }

        public String getInvoiceBankNum() {
            return invoiceBankNum;
        }

        public void setInvoiceBankNum(String invoiceBankNum) {
            this.invoiceBankNum = invoiceBankNum;
        }

        public Object getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(Object invoiceType) {
            this.invoiceType = invoiceType;
        }

        public int getInvoiceStatus() {
            return invoiceStatus;
        }

        public void setInvoiceStatus(int invoiceStatus) {
            this.invoiceStatus = invoiceStatus;
        }

        public String getNoApproval() {
            return noApproval;
        }

        public void setNoApproval(String noApproval) {
            this.noApproval = noApproval;
        }
    }
}
