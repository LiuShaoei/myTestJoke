package xst.app.com.essayjoke.bean;

import java.io.Serializable;

/**
 * Created by LiuZhaowei on 2018/12/14 0014.
 */
public class BannerBean implements Serializable {
    public String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String title;
    public String jumpUrl;
    public int id;
}
