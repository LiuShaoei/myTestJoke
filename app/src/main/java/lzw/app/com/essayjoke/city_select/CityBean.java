package lzw.app.com.essayjoke.city_select;

/**
 * Created by LiuZhaowei on 2018/12/29 0029.
 */
public class CityBean {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public CityBean(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    private String name;
    private String pinyin;

}
