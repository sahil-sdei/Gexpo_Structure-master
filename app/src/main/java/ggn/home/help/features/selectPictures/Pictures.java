package ggn.home.help.features.selectPictures;

import java.io.Serializable;

/**
 * Created by sahilsa on 3/4/18.
 */

public class Pictures implements Serializable{
    public String picture;
    public boolean isSelected;
    public boolean isCheckVisible;

    public Pictures(String picture, boolean isSelected) {
        this.picture = picture;
        this.isSelected = isSelected;
    }

    public Pictures(String picture, boolean isSelected, boolean isCheckVisible) {
        this.picture = picture;
        this.isSelected = isSelected;
        this.isCheckVisible = isCheckVisible;
    }
}
