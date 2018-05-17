package com.yasn.purchasetest.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/1/10.
 */

public class CarType implements Serializable {

    private List<CarTypesBean> carTypes;

    public List<CarTypesBean> getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(List<CarTypesBean> carTypes) {
        this.carTypes = carTypes;
    }

    public static class CarTypesBean {
        /**
         * carTypeId : 1
         * name : 奥迪
         * initial : A
         * parentid : 0
         * logo : http://api.jisuapi.com/car/static/images/logo/300/1.png
         */

        private int carTypeId;
        private String name;
        private String initial;
        private int parentid;
        private String logo;

        public int getCarTypeId() {
            return carTypeId;
        }

        public void setCarTypeId(int carTypeId) {
            this.carTypeId = carTypeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
