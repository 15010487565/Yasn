package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/2/1.
 */

public class SearchCatsModel implements Serializable {

    private List<CatsBean> cats;

    public List<CatsBean> getCats() {
        return cats;
    }

    public void setCats(List<CatsBean> cats) {
        this.cats = cats;
    }

    public static class CatsBean {
        /**
         * cat_id : 593
         * name : 雨刷
         */

        private int cat_id;
        private String name;

        public int getCat_id() {
            return cat_id;
        }

        public void setCat_id(int cat_id) {
            this.cat_id = cat_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
