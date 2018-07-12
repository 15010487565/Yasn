package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2018/6/12.
 */

public class PayModel implements Serializable{


        /**
         * id : 8
         * name : 支付宝
         * biref : images/zfb.png
         * type : alipayWapPlugin
         * isLastPayment : 0
         */

        private int id;
        private String name;
        private String biref;
        private String type;
        private int isLastPayment;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBiref() {
            return biref;
        }

        public void setBiref(String biref) {
            this.biref = biref;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIsLastPayment() {
            return isLastPayment;
        }

        public void setIsLastPayment(int isLastPayment) {
            this.isLastPayment = isLastPayment;
        }

}
