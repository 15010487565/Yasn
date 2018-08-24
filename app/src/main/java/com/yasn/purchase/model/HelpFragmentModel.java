package com.yasn.purchase.model;

import java.io.Serializable;

/**
 * Created by gs on 2018/8/22.
 */

public class HelpFragmentModel implements Serializable {
    private String title;
    private String descript;
    private int ItemType;
    private String content;

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItemType() {
        return ItemType;
    }

    public void setItemType(int itemType) {
        ItemType = itemType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
