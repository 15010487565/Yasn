package com.yasn.purchasetest.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Android on 2017/8/9.
 */

public class ClassifyModel implements Serializable{

    private List<CatsBean> cats;

    public List<CatsBean> getCats() {
        return cats;
    }

    public void setCats(List<CatsBean> cats) {
        this.cats = cats;
    }

    public static class CatsBean {
        /**
         * image : http://shoptt.yasn.com/static/attachment/goodscat/2017/11/17/15//50307101.png
         * children : [{"image":"http://shoptt.yasn.com/static/attachment/goodscat/2017/2/14/21//53596707.jpg","parent_id":635,"cat_id":593,"name":"雨刷"},{"parent_id":635,"cat_id":600,"name":"镀晶"},{"image":"http://caigou.yasn.com/statics/attachment/goodscat/2016/11/1/16//19041092.jpg","parent_id":635,"cat_id":560,"name":"轮胎"}]
         * parent_id : 0
         * cat_id : 635
         * name : 轮毂轮胎
         */
        private String adv_image;
        private String image;
        private int parent_id;
        private int cat_id;
        private String name;
        private List<ChildrenBean> children;

        public String getAdv_image() {
            return adv_image;
        }

        public void setAdv_image(String adv_image) {
            this.adv_image = adv_image;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

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

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * image : http://shoptt.yasn.com/static/attachment/goodscat/2017/2/14/21//53596707.jpg
             * parent_id : 635
             * cat_id : 593
             * name : 雨刷
             */

            private String image;
            private int parent_id;
            private int cat_id;
            private String name;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

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
}
