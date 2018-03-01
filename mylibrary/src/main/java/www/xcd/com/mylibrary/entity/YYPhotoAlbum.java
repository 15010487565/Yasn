package www.xcd.com.mylibrary.entity;

import com.yonyou.sns.im.entity.album.YYPhotoItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Android on 2017/6/5.
 */

public class YYPhotoAlbum implements Serializable {
    private static final long serialVersionUID = -2740760771931498264L;
    private String name;
    private int bitmap;
    private boolean isCurrentChoice;
    private boolean isTotal;
    private List<YYPhotoItem> photoList = new ArrayList();

    public YYPhotoAlbum() {
    }

    public YYPhotoAlbum(String name, int bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public void addPhotoItem(YYPhotoItem photoItem) {
        this.photoList.add(photoItem);
    }

    public YYPhotoItem getPhotoItem(int index) {
        return (YYPhotoItem)this.photoList.get(index);
    }

    public int count() {
        return this.photoList.size();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(int bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isCurrentChoice() {
        return this.isCurrentChoice;
    }

    public void setCurrentChoice(boolean isCurrentChoice) {
        this.isCurrentChoice = isCurrentChoice;
    }

    public boolean isTotal() {
        return this.isTotal;
    }

    public void setTotal(boolean isTotal) {
        this.isTotal = isTotal;
    }

    public List<YYPhotoItem> getPhotoList() {
        return this.photoList;
    }

    public void setPhotoList(List<YYPhotoItem> photoList) {
        this.photoList = photoList;
    }

    public String toString() {
        return "PhotoAibum [name=" + this.name + ", count=" + this.photoList.size() + ", bitmap=" + this.bitmap + ", photoList=" + this.photoList + "]";
    }
}

