package com.yasn.purchase.model;

import java.io.Serializable;
import java.util.List;

public class FindVideoModel implements Serializable {


    /**
     * code : 200
     * data : [{"fileUrl":"","discoveryId":44,"classify":"8","content":"6666666666000000222ccc","createTime":1515643015,"fileType":1,"modifyTime":1517192389},{"fileUrl":"http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4","discoveryId":43,"classify":"4","content":"这是视频","createTime":1515643015,"fileType":1,"modifyTime":1517192389},{"fileUrlMin":["http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85"],"fileUrlMax":["http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384"],"discoveryId":45,"classify":"4","content":"YYYYYYYYYYYYYYY3363dsasadsasdasd","createTime":1515643558,"fileType":0,"modifyTime":1517623703},{"fileUrl":"https://res.exexm.com/cw_145225549855002","discoveryId":43,"classify":"4","content":"这是视频1","createTime":1515643015,"fileType":1,"modifyTime":1517192389},{"fileUrl":"https://res.exexm.com/cw_145225549855002","discoveryId":43,"classify":"4","content":"这是视频2","createTime":1515643015,"fileType":1,"modifyTime":1517192389},{"fileUrl":"https://res.exexm.com/cw_145225549855002","discoveryId":43,"classify":"4","content":"这是视频11","createTime":1515643015,"fileType":1,"modifyTime":1517192389}]
     * page : 1
     * message : null
     * title : [{"classifyId":4,"classifyName":"视频","isShow":1,"sort":10},{"classifyId":8,"classifyName":"精选","isShow":1,"sort":25}]
     */

    private int code;
    private int page;
    private Object message;
    private List<DataBean> data;
    private List<TitleBean> title;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<TitleBean> getTitle() {
        return title;
    }

    public void setTitle(List<TitleBean> title) {
        this.title = title;
    }

    public static class DataBean {
        /**
         * fileUrl :
         * discoveryId : 44
         * classify : 8
         * content : 6666666666000000222ccc
         * createTime : 1515643015
         * fileType : 1
         * modifyTime : 1517192389
         * fileUrlMin : ["http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384&imageView2/1/w/200/h/200/q/85"]
         * fileUrlMax : ["http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384","http://me.yasn.com/image2161da7496834ff0aa324504a8228902.jpg?sign=18c608392a1aeccfc0fd4914a6b0adfa&t=5a759384"]
         */

        private String fileUrl;
        private int discoveryId;
        private String classify;
        private String content;
        private int createTime;
        private int fileType;
        private int modifyTime;
        private List<String> fileUrlMin;
        private List<String> fileUrlMax;
        private String videoShowImg;

        public String getVideoShowImg() {
            return videoShowImg;
        }

        public void setVideoShowImg(String videoShowImg) {
            this.videoShowImg = videoShowImg;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public int getDiscoveryId() {
            return discoveryId;
        }

        public void setDiscoveryId(int discoveryId) {
            this.discoveryId = discoveryId;
        }

        public String getClassify() {
            return classify;
        }

        public void setClassify(String classify) {
            this.classify = classify;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }

        public int getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(int modifyTime) {
            this.modifyTime = modifyTime;
        }

        public List<String> getFileUrlMin() {
            return fileUrlMin;
        }

        public void setFileUrlMin(List<String> fileUrlMin) {
            this.fileUrlMin = fileUrlMin;
        }

        public List<String> getFileUrlMax() {
            return fileUrlMax;
        }

        public void setFileUrlMax(List<String> fileUrlMax) {
            this.fileUrlMax = fileUrlMax;
        }
    }

    public static class TitleBean {
        /**
         * classifyId : 4
         * classifyName : 视频
         * isShow : 1
         * sort : 10
         */

        private int classifyId;
        private String classifyName;
        private int isShow;
        private int sort;

        public int getClassifyId() {
            return classifyId;
        }

        public void setClassifyId(int classifyId) {
            this.classifyId = classifyId;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
