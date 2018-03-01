package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2018/2/1.
 */

public class BaseRecyModel implements Serializable {
    public int itemType;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
