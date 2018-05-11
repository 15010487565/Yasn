package com.yasn.purchase.model;

/**
 * Created by gs on 2018/1/4.
 */

public class ClassifyRightModel extends BaseRecyModel {

    private String rightClassifyImg;
    private String rightClassifyName;
    private String rightClassifyparentId;
    private String rightClassifycatId;

    private String rightClassifyBrandImg;
    private String rightClassifyBrandName;
    private int rightClassifyBrandId;

    public String getRightClassifyBrandImg() {
        return rightClassifyBrandImg;
    }

    public void setRightClassifyBrandImg(String rightClassifyBrandImg) {
        this.rightClassifyBrandImg = rightClassifyBrandImg;
    }

    public String getRightClassifyBrandName() {
        return rightClassifyBrandName;
    }

    public void setRightClassifyBrandName(String rightClassifyBrandName) {
        this.rightClassifyBrandName = rightClassifyBrandName;
    }

    public int getRightClassifyBrandId() {
        return rightClassifyBrandId;
    }

    public void setRightClassifyBrandId(int rightClassifyBrandId) {
        this.rightClassifyBrandId = rightClassifyBrandId;
    }

    public String getRightClassifyImg() {
        return rightClassifyImg;
    }

    public void setRightClassifyImg(String rightClassifyImg) {
        this.rightClassifyImg = rightClassifyImg;
    }

    public String getRightClassifyName() {
        return rightClassifyName;
    }

    public void setRightClassifyName(String rightClassifyName) {
        this.rightClassifyName = rightClassifyName;
    }

    public String getRightClassifyparentId() {
        return rightClassifyparentId;
    }

    public void setRightClassifyparentId(String rightClassifyparentId) {
        this.rightClassifyparentId = rightClassifyparentId;
    }

    public String getRightClassifycatId() {
        return rightClassifycatId;
    }

    public void setRightClassifycatId(String rightClassifycatId) {
        this.rightClassifycatId = rightClassifycatId;
    }

    @Override
    public String toString() {
        return "ClassifyRightModel{" +
                "rightClassifyImg='" + rightClassifyImg + '\'' +
                ", rightClassifyName='" + rightClassifyName + '\'' +
                ", rightClassifyparentId='" + rightClassifyparentId + '\'' +
                ", rightClassifycatId='" + rightClassifycatId + '\'' +
                '}';
    }
}
