package ggn.home.help.features.memoryCategories;

import java.io.Serializable;

public class SubCategories implements Serializable {
    public String title;
    public String icon;
    public String background;

    public SubCategories(String title, String icon, String background) {
        this.title = title;
        this.icon = icon;
        this.background = background;
    }
}