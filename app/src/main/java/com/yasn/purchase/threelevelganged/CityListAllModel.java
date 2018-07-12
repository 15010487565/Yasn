package com.yasn.purchase.threelevelganged;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gs on 2018/7/3.
 */

public class CityListAllModel implements Serializable{

    private List<ListRegionsBean> listRegions;

    public List<ListRegionsBean> getListRegions() {
        return listRegions;
    }

    public void setListRegions(List<ListRegionsBean> listRegions) {
        this.listRegions = listRegions;
    }

    public static class ListRegionsBean {
        /**
         * city : [{"area":[{"region_id":3,"local_name":"东城区"}],"region_id":2,"local_name":"东城区"},{"area":[{"region_id":5,"local_name":"西城区"}],"region_id":4,"local_name":"西城区"},{"area":[{"region_id":7,"local_name":"朝阳区"}],"region_id":6,"local_name":"朝阳区"},{"area":[{"region_id":9,"local_name":"丰台区"}],"region_id":8,"local_name":"丰台区"},{"area":[{"region_id":11,"local_name":"石景山区"}],"region_id":10,"local_name":"石景山区"},{"area":[{"region_id":13,"local_name":"海淀区"}],"region_id":12,"local_name":"海淀区"},{"area":[{"region_id":15,"local_name":"门头沟区"}],"region_id":14,"local_name":"门头沟区"},{"area":[{"region_id":17,"local_name":"房山区"}],"region_id":16,"local_name":"房山区"},{"area":[{"region_id":19,"local_name":"通州区"}],"region_id":18,"local_name":"通州区"},{"area":[{"region_id":21,"local_name":"顺义区"}],"region_id":20,"local_name":"顺义区"},{"area":[{"region_id":23,"local_name":"昌平区"}],"region_id":22,"local_name":"昌平区"},{"area":[{"region_id":25,"local_name":"大兴区"}],"region_id":24,"local_name":"大兴区"},{"area":[{"region_id":27,"local_name":"怀柔区"}],"region_id":26,"local_name":"怀柔区"},{"area":[{"region_id":29,"local_name":"平谷区"}],"region_id":28,"local_name":"平谷区"},{"area":[{"region_id":31,"local_name":"密云县"}],"region_id":30,"local_name":"密云县"},{"area":[{"region_id":33,"local_name":"延庆县"}],"region_id":32,"local_name":"延庆县"}]
         * region_id : 1
         * local_name : 北京
         */

        private int region_id;
        private String local_name;
        private List<CityBean> city;

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public String getLocal_name() {
            return local_name;
        }

        public void setLocal_name(String local_name) {
            this.local_name = local_name;
        }

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            /**
             * area : [{"region_id":3,"local_name":"东城区"}]
             * region_id : 2
             * local_name : 东城区
             */

            private int region_id;
            private String local_name;
            private List<AreaBean> area;

            public int getRegion_id() {
                return region_id;
            }

            public void setRegion_id(int region_id) {
                this.region_id = region_id;
            }

            public String getLocal_name() {
                return local_name;
            }

            public void setLocal_name(String local_name) {
                this.local_name = local_name;
            }

            public List<AreaBean> getArea() {
                return area;
            }

            public void setArea(List<AreaBean> area) {
                this.area = area;
            }

            public static class AreaBean {
                /**
                 * region_id : 3
                 * local_name : 东城区
                 */

                private int region_id;
                private String local_name;

                public int getRegion_id() {
                    return region_id;
                }

                public void setRegion_id(int region_id) {
                    this.region_id = region_id;
                }

                public String getLocal_name() {
                    return local_name;
                }

                public void setLocal_name(String local_name) {
                    this.local_name = local_name;
                }
            }
        }
    }
}
