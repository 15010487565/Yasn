package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2018/7/30.
 */

public class OilQueryModel implements Serializable {

    /**
     * oilId : 2224
     * brand : 进口奥迪
     * cars : A1
     * yearStyle : 12-16
     * engineDisplacement : 1.4T
     * gearboxType : 7速干式双离合
     * gearboxOilModel : TOPGEAR 手动变速箱油75W-80S
     * oilModel : 动力专家全合成5W-40,方程式全合成5W-40,竞速全合成加强版5W-40i
     * oilAddition : NA
     * linkageId : 1018409
     */

    private int oilId;
    private String brand;
    private String cars;
    private String yearStyle;
    private String engineDisplacement;
    private String gearboxType;
    private String gearboxOilModel;
    private String oilModel;
    private String oilAddition;
    private int linkageId;

    public int getOilId() {
        return oilId;
    }

    public void setOilId(int oilId) {
        this.oilId = oilId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCars() {
        return cars;
    }

    public void setCars(String cars) {
        this.cars = cars;
    }

    public String getYearStyle() {
        return yearStyle;
    }

    public void setYearStyle(String yearStyle) {
        this.yearStyle = yearStyle;
    }

    public String getEngineDisplacement() {
        return engineDisplacement;
    }

    public void setEngineDisplacement(String engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    public String getGearboxType() {
        return gearboxType;
    }

    public void setGearboxType(String gearboxType) {
        this.gearboxType = gearboxType;
    }

    public String getGearboxOilModel() {
        return gearboxOilModel;
    }

    public void setGearboxOilModel(String gearboxOilModel) {
        this.gearboxOilModel = gearboxOilModel;
    }

    public String getOilModel() {
        return oilModel;
    }

    public void setOilModel(String oilModel) {
        this.oilModel = oilModel;
    }

    public String getOilAddition() {
        return oilAddition;
    }

    public void setOilAddition(String oilAddition) {
        this.oilAddition = oilAddition;
    }

    public int getLinkageId() {
        return linkageId;
    }

    public void setLinkageId(int linkageId) {
        this.linkageId = linkageId;
    }
}
