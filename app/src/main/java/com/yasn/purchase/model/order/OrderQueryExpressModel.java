package com.yasn.purchase.model.order;

import java.util.List;

/**
 * Created by gs on 2018/6/6.
 */

public class OrderQueryExpressModel {

    private List<DeliverysBean> deliverys;

    public List<DeliverysBean> getDeliverys() {
        return deliverys;
    }

    public void setDeliverys(List<DeliverysBean> deliverys) {
        this.deliverys = deliverys;
    }

    public static class DeliverysBean {
        /**
         * orderId : 48170
         * logiLCode : huitongkuaidi
         * logiName : 百世汇通
         * logiNo : 123123
         * expressDetail : {"message":"ok","nu":"123123","ischeck":"1","condition":"F00","com":"huitongkuaidi","status":"200","state":"3","data":[{"time":"2018-03-23 10:40:16","ftime":"2018-03-23 10:40:16","context":"已签收，签收人凭取货码签收。"},{"time":"2018-03-23 10:39:11","ftime":"2018-03-23 10:39:11","context":"快件已由南通崇川紫东花苑75栋朝南菜鸟驿站代收，请及时取件，如有疑问请联系15706290910"},{"time":"2017-01-02 14:32:04","ftime":"2017-01-02 14:32:04","context":"快件被快递员13922372435取出，请等待快递员与您联系，电话13922372435。"},{"time":"2016-12-30 21:09:45","ftime":"2016-12-30 21:09:45","context":"快件仍在GZ金碧新城东门口格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-12-28 15:52:54","ftime":"2016-12-28 15:52:54","context":"快件被快递员13922372435取出，请等待快递员与您联系，电话13922372435。"},{"time":"2016-12-17 12:55:40","ftime":"2016-12-17 12:55:40","context":"快件被快递员13543896089取出，请等待快递员与您联系，电话13543896089。"},{"time":"2016-12-06 10:46:23","ftime":"2016-12-06 10:46:23","context":"快件被快递员孙迪取出，请等待快递员与您联系，电话18550428993。"},{"time":"2016-12-03 17:12:04","ftime":"2016-12-03 17:12:04","context":"快件仍在JS宏图上水庭格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-12-01 15:32:11","ftime":"2016-12-01 15:32:11","context":"快件被快递员孙迪取出，请等待快递员与您联系，电话18550428993。"},{"time":"2016-11-29 15:29:45","ftime":"2016-11-29 15:29:45","context":"快件仍在JS宏图上水园格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-11-21 14:07:30","ftime":"2016-11-21 14:07:30","context":"快件被快递员13730063560取出，请等待快递员与您联系，电话13730063560。"},{"time":"2016-11-16 11:39:04","ftime":"2016-11-16 11:39:04","context":"快件被快递员18605930399取出，请等待快递员与您联系，电话18605930399。"},{"time":"2016-11-09 11:27:29","ftime":"2016-11-09 11:27:29","context":"快件因为超期等原因被自提柜管理员取出，请等待管理员与您联系，电话4000338888。"},{"time":"2016-10-23 09:58:54","ftime":"2016-10-23 09:58:54","context":"快件仍在FJ龙华里A格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-10-03 15:00:43","ftime":"2016-10-03 15:00:43","context":"快件被快递员18805152370取出，请等待快递员与您联系，电话18805152370。"},{"time":"2016-09-10 12:00:54","ftime":"2016-09-10 12:00:54","context":"快件被快递员15355401324取出，请等待快递员与您联系，电话15355401324。"},{"time":"2016-09-03 14:49:18","ftime":"2016-09-03 14:49:18","context":"快件被快递员18622421235取出，请等待快递员与您联系，电话18622421235。"},{"time":"2016-09-02 10:05:32","ftime":"2016-09-02 10:05:32","context":"快件被快递员15259253259取出，请等待快递员与您联系，电话15259253259。"},{"time":"2016-08-31 09:54:33","ftime":"2016-08-31 09:54:33","context":"快件仍在FJ龙昌里A格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-08-12 11:54:10","ftime":"2016-08-12 11:54:10","context":"快件被快递员杨应专取出，请等待快递员与您联系，电话13952949730。"},{"time":"2016-08-10 10:05:47","ftime":"2016-08-10 10:05:47","context":"快件被快递员快递员9862取出，请等待快递员与您联系，电话17729166778。"},{"time":"2016-07-25 15:24:59","ftime":"2016-07-25 15:24:59","context":"快件被快递员15721510465取出，请等待快递员与您联系，电话15721510465。"},{"time":"2016-07-23 08:11:38","ftime":"2016-07-23 08:11:38","context":"快件仍在SH华鹏花苑格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-07-14 11:03:16","ftime":"2016-07-14 11:03:16","context":"快件被快递员13832381987取出，请等待快递员与您联系，电话13832381987。"},{"time":"2016-07-10 10:40:51","ftime":"2016-07-10 10:40:51","context":"快件被快递员李德瑞取出，请等待快递员与您联系，电话18652052920。"},{"time":"2016-07-08 14:44:22","ftime":"2016-07-08 14:44:22","context":"快件被快递员15196664431取出，请等待快递员与您联系，电话15196664431。"}]}
         */

        private int orderId;
        private String logiLCode;
        private String logiName;
        private String logiNo;
        private ExpressDetailBean expressDetail;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getLogiLCode() {
            return logiLCode;
        }

        public void setLogiLCode(String logiLCode) {
            this.logiLCode = logiLCode;
        }

        public String getLogiName() {
            return logiName;
        }

        public void setLogiName(String logiName) {
            this.logiName = logiName;
        }

        public String getLogiNo() {
            return logiNo;
        }

        public void setLogiNo(String logiNo) {
            this.logiNo = logiNo;
        }

        public ExpressDetailBean getExpressDetail() {
            return expressDetail;
        }

        public void setExpressDetail(ExpressDetailBean expressDetail) {
            this.expressDetail = expressDetail;
        }

        @Override
        public String toString() {
            return "DeliverysBean{" +
                    "orderId=" + orderId +
                    ", logiLCode='" + logiLCode + '\'' +
                    ", logiName='" + logiName + '\'' +
                    ", logiNo='" + logiNo + '\'' +
                    ", expressDetail=" + expressDetail +
                    '}';
        }

        public static class ExpressDetailBean {
            /**
             * message : ok
             * nu : 123123
             * ischeck : 1
             * condition : F00
             * com : huitongkuaidi
             * status : 200
             * state : 3
             * data : [{"time":"2018-03-23 10:40:16","ftime":"2018-03-23 10:40:16","context":"已签收，签收人凭取货码签收。"},{"time":"2018-03-23 10:39:11","ftime":"2018-03-23 10:39:11","context":"快件已由南通崇川紫东花苑75栋朝南菜鸟驿站代收，请及时取件，如有疑问请联系15706290910"},{"time":"2017-01-02 14:32:04","ftime":"2017-01-02 14:32:04","context":"快件被快递员13922372435取出，请等待快递员与您联系，电话13922372435。"},{"time":"2016-12-30 21:09:45","ftime":"2016-12-30 21:09:45","context":"快件仍在GZ金碧新城东门口格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-12-28 15:52:54","ftime":"2016-12-28 15:52:54","context":"快件被快递员13922372435取出，请等待快递员与您联系，电话13922372435。"},{"time":"2016-12-17 12:55:40","ftime":"2016-12-17 12:55:40","context":"快件被快递员13543896089取出，请等待快递员与您联系，电话13543896089。"},{"time":"2016-12-06 10:46:23","ftime":"2016-12-06 10:46:23","context":"快件被快递员孙迪取出，请等待快递员与您联系，电话18550428993。"},{"time":"2016-12-03 17:12:04","ftime":"2016-12-03 17:12:04","context":"快件仍在JS宏图上水庭格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-12-01 15:32:11","ftime":"2016-12-01 15:32:11","context":"快件被快递员孙迪取出，请等待快递员与您联系，电话18550428993。"},{"time":"2016-11-29 15:29:45","ftime":"2016-11-29 15:29:45","context":"快件仍在JS宏图上水园格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-11-21 14:07:30","ftime":"2016-11-21 14:07:30","context":"快件被快递员13730063560取出，请等待快递员与您联系，电话13730063560。"},{"time":"2016-11-16 11:39:04","ftime":"2016-11-16 11:39:04","context":"快件被快递员18605930399取出，请等待快递员与您联系，电话18605930399。"},{"time":"2016-11-09 11:27:29","ftime":"2016-11-09 11:27:29","context":"快件因为超期等原因被自提柜管理员取出，请等待管理员与您联系，电话4000338888。"},{"time":"2016-10-23 09:58:54","ftime":"2016-10-23 09:58:54","context":"快件仍在FJ龙华里A格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-10-03 15:00:43","ftime":"2016-10-03 15:00:43","context":"快件被快递员18805152370取出，请等待快递员与您联系，电话18805152370。"},{"time":"2016-09-10 12:00:54","ftime":"2016-09-10 12:00:54","context":"快件被快递员15355401324取出，请等待快递员与您联系，电话15355401324。"},{"time":"2016-09-03 14:49:18","ftime":"2016-09-03 14:49:18","context":"快件被快递员18622421235取出，请等待快递员与您联系，电话18622421235。"},{"time":"2016-09-02 10:05:32","ftime":"2016-09-02 10:05:32","context":"快件被快递员15259253259取出，请等待快递员与您联系，电话15259253259。"},{"time":"2016-08-31 09:54:33","ftime":"2016-08-31 09:54:33","context":"快件仍在FJ龙昌里A格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-08-12 11:54:10","ftime":"2016-08-12 11:54:10","context":"快件被快递员杨应专取出，请等待快递员与您联系，电话13952949730。"},{"time":"2016-08-10 10:05:47","ftime":"2016-08-10 10:05:47","context":"快件被快递员快递员9862取出，请等待快递员与您联系，电话17729166778。"},{"time":"2016-07-25 15:24:59","ftime":"2016-07-25 15:24:59","context":"快件被快递员15721510465取出，请等待快递员与您联系，电话15721510465。"},{"time":"2016-07-23 08:11:38","ftime":"2016-07-23 08:11:38","context":"快件仍在SH华鹏花苑格格货栈【自提柜】未取，请及时取件。"},{"time":"2016-07-14 11:03:16","ftime":"2016-07-14 11:03:16","context":"快件被快递员13832381987取出，请等待快递员与您联系，电话13832381987。"},{"time":"2016-07-10 10:40:51","ftime":"2016-07-10 10:40:51","context":"快件被快递员李德瑞取出，请等待快递员与您联系，电话18652052920。"},{"time":"2016-07-08 14:44:22","ftime":"2016-07-08 14:44:22","context":"快件被快递员15196664431取出，请等待快递员与您联系，电话15196664431。"}]
             */

            private String message;
            private String nu;
            private String ischeck;
            private String condition;
            private String com;
            private String status;
            private String state;
            private List<DataBean> data;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getNu() {
                return nu;
            }

            public void setNu(String nu) {
                this.nu = nu;
            }

            public String getIscheck() {
                return ischeck;
            }

            public void setIscheck(String ischeck) {
                this.ischeck = ischeck;
            }

            public String getCondition() {
                return condition;
            }

            public void setCondition(String condition) {
                this.condition = condition;
            }

            public String getCom() {
                return com;
            }

            public void setCom(String com) {
                this.com = com;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            @Override
            public String toString() {
                return "ExpressDetailBean{" +
                        "message='" + message + '\'' +
                        ", nu='" + nu + '\'' +
                        ", ischeck='" + ischeck + '\'' +
                        ", condition='" + condition + '\'' +
                        ", com='" + com + '\'' +
                        ", status='" + status + '\'' +
                        ", state='" + state + '\'' +
                        ", data=" + data +
                        '}';
            }

            public static class DataBean {
                /**
                 * time : 2018-03-23 10:40:16
                 * ftime : 2018-03-23 10:40:16
                 * context : 已签收，签收人凭取货码签收。
                 */

                private String time;
                private String ftime;
                private String context;

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getFtime() {
                    return ftime;
                }

                public void setFtime(String ftime) {
                    this.ftime = ftime;
                }

                public String getContext() {
                    return context;
                }

                public void setContext(String context) {
                    this.context = context;
                }

                @Override
                public String toString() {
                    return "DataBean{" +
                            "time='" + time + '\'' +
                            ", ftime='" + ftime + '\'' +
                            ", context='" + context + '\'' +
                            '}';
                }
            }
        }
    }

    @Override
    public String toString() {
        return "OrderQueryExpressModel{" +
                "deliverys=" + deliverys +
                '}';
    }
}
