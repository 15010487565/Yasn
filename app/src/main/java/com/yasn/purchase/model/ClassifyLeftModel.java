package com.yasn.purchase.model;

/**
 * Created by gs on 2018/1/4.
 */

public class ClassifyLeftModel extends BaseRecyModel {
    private String id;
    private String title;
    private boolean checked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "ClassifyLeftModel{" +
                "title='" + title + '\'' +
                ", checked=" + checked +
                '}';
    }
}
