package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/7/17.
 */

public class CollectNullModel implements Serializable{

    /**
     * subject : [{"subject_id":3,"flag":"index_top","region_id":0,"sort":1,"title":"新品首发","content":[{"small":"http://shoptt.yasn.com/static/attachment//store/1/goods/2017/8/3/21//57512052_small.jpg","store_id":1,"goods_name":"爱车屋 USB车载充电器 I-2217B 白色/件爱车屋 USB车载充电器 I-2217B 白色/件","discount_price":"8.00","total_buy_count":115,"have_voice":0,"advert":"采购价7折促销！","is_before_sale":1,"market_enable":1,"price":8,"has_discount":0,"maxcountPeruser":0,"is_limit_buy":0,"id":12,"is_success_case":0},{"small":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/5/17/18//15379702_small.jpg","store_id":1,"goods_name":"测试","discount_price":"1.00","total_buy_count":46,"have_voice":0,"advert":"2个规格测试","is_before_sale":0,"market_enable":1,"price":1,"has_discount":0,"maxcountPeruser":0,"is_limit_buy":0,"id":3724,"is_success_case":1},{"small":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/2/8/10//08138149_small.jpg","store_id":1,"goods_name":"语音商品","discount_price":"12.00","total_buy_count":49,"have_voice":1,"advert":"语音商品","is_before_sale":0,"market_enable":1,"price":12,"has_discount":0,"maxcountPeruser":6,"is_limit_buy":0,"id":3705,"is_success_case":1}],"is_display":0}]
     * member : null
     */

    private Object member;
    private List<SubjectBean> subject;

    public Object getMember() {
        return member;
    }

    public void setMember(Object member) {
        this.member = member;
    }

    public List<SubjectBean> getSubject() {
        return subject;
    }

    public void setSubject(List<SubjectBean> subject) {
        this.subject = subject;
    }

    public static class SubjectBean {
        /**
         * subject_id : 3
         * flag : index_top
         * region_id : 0
         * sort : 1
         * title : 新品首发
         * content : [{"small":"http://shoptt.yasn.com/static/attachment//store/1/goods/2017/8/3/21//57512052_small.jpg","store_id":1,"goods_name":"爱车屋 USB车载充电器 I-2217B 白色/件爱车屋 USB车载充电器 I-2217B 白色/件","discount_price":"8.00","total_buy_count":115,"have_voice":0,"advert":"采购价7折促销！","is_before_sale":1,"market_enable":1,"price":8,"has_discount":0,"maxcountPeruser":0,"is_limit_buy":0,"id":12,"is_success_case":0},{"small":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/5/17/18//15379702_small.jpg","store_id":1,"goods_name":"测试","discount_price":"1.00","total_buy_count":46,"have_voice":0,"advert":"2个规格测试","is_before_sale":0,"market_enable":1,"price":1,"has_discount":0,"maxcountPeruser":0,"is_limit_buy":0,"id":3724,"is_success_case":1},{"small":"http://shoptt.yasn.com/static/attachment//store/1/goods/2018/2/8/10//08138149_small.jpg","store_id":1,"goods_name":"语音商品","discount_price":"12.00","total_buy_count":49,"have_voice":1,"advert":"语音商品","is_before_sale":0,"market_enable":1,"price":12,"has_discount":0,"maxcountPeruser":6,"is_limit_buy":0,"id":3705,"is_success_case":1}]
         * is_display : 0
         */

        private int subject_id;
        private String flag;
        private int region_id;
        private int sort;
        private String title;
        private int is_display;
        private List<ContentBean> content;

        public int getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(int subject_id) {
            this.subject_id = subject_id;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIs_display() {
            return is_display;
        }

        public void setIs_display(int is_display) {
            this.is_display = is_display;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * small : http://shoptt.yasn.com/static/attachment//store/1/goods/2017/8/3/21//57512052_small.jpg
             * store_id : 1
             * goods_name : 爱车屋 USB车载充电器 I-2217B 白色/件爱车屋 USB车载充电器 I-2217B 白色/件
             * discount_price : 8.00
             * total_buy_count : 115
             * have_voice : 0
             * advert : 采购价7折促销！
             * is_before_sale : 1
             * market_enable : 1
             * price : 8.0
             * has_discount : 0
             * maxcountPeruser : 0
             * is_limit_buy : 0
             * id : 12
             * is_success_case : 0
             */

            private String small;
            private int store_id;
            private String goods_name;
            private String discount_price;
            private int total_buy_count;
            private int have_voice;
            private String advert;
            private int is_before_sale;
            private int market_enable;
            private double price;
            private int has_discount;
            private int maxcountPeruser;
            private int is_limit_buy;
            private int id;
            private int is_success_case;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getDiscount_price() {
                return discount_price;
            }

            public void setDiscount_price(String discount_price) {
                this.discount_price = discount_price;
            }

            public int getTotal_buy_count() {
                return total_buy_count;
            }

            public void setTotal_buy_count(int total_buy_count) {
                this.total_buy_count = total_buy_count;
            }

            public int getHave_voice() {
                return have_voice;
            }

            public void setHave_voice(int have_voice) {
                this.have_voice = have_voice;
            }

            public String getAdvert() {
                return advert;
            }

            public void setAdvert(String advert) {
                this.advert = advert;
            }

            public int getIs_before_sale() {
                return is_before_sale;
            }

            public void setIs_before_sale(int is_before_sale) {
                this.is_before_sale = is_before_sale;
            }

            public int getMarket_enable() {
                return market_enable;
            }

            public void setMarket_enable(int market_enable) {
                this.market_enable = market_enable;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getHas_discount() {
                return has_discount;
            }

            public void setHas_discount(int has_discount) {
                this.has_discount = has_discount;
            }

            public int getMaxcountPeruser() {
                return maxcountPeruser;
            }

            public void setMaxcountPeruser(int maxcountPeruser) {
                this.maxcountPeruser = maxcountPeruser;
            }

            public int getIs_limit_buy() {
                return is_limit_buy;
            }

            public void setIs_limit_buy(int is_limit_buy) {
                this.is_limit_buy = is_limit_buy;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIs_success_case() {
                return is_success_case;
            }

            public void setIs_success_case(int is_success_case) {
                this.is_success_case = is_success_case;
            }
        }
    }
}
