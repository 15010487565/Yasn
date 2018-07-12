package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2018/6/27.
 */

public class AddressModel implements Serializable{

    /**
     * regionId : 385
     * defAddr : 1
     * mobile : 13466663750
     * addrId : 12318
     * region : 阿鲁科尔沁旗
     * province : 内蒙古
     * addr : 哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈
     * provinceId : 360
     * cityId : 382
     * zip : 123456
     * tel : null
     * name : 海绵宝宝1
     * city : 赤峰市
     */

    private int regionId;
    private int defAddr;
    private String mobile;
    private int addrId;
    private String region;
    private String province;
    private String addr;
    private int provinceId;
    private int cityId;
    private String zip;
    private Object tel;
    private String name;
    private String city;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getDefAddr() {
        return defAddr;
    }

    public void setDefAddr(int defAddr) {
        this.defAddr = defAddr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAddrId() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Object getTel() {
        return tel;
    }

    public void setTel(Object tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
