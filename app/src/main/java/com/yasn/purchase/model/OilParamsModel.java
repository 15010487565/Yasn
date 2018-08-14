package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2018/7/30.
 */

public class OilParamsModel implements Serializable {

    /**
     * parent_id : 0
     * name : 一汽奥迪
     * linkage_id : 1018295
     */

    private int parent_id;
    private String name;
    private int linkage_id;

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLinkage_id() {
        return linkage_id;
    }

    public void setLinkage_id(int linkage_id) {
        this.linkage_id = linkage_id;
    }
}
