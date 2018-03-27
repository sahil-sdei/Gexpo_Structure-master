package ggn.home.help.features.memoryCategories;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sahilsa on 27/3/18.
 */

public class Categories implements Serializable{
    public String title;
    public String subTitle;
    public String image;
    public String years;
    public List<SubCategories> listSubcategories;

    public Categories(String title, String subTitle, String image, String years, List<SubCategories> listSubcategories) {
        this.title = title;
        this.subTitle = subTitle;
        this.image = image;
        this.years = years;
        this.listSubcategories = listSubcategories;
    }
}
