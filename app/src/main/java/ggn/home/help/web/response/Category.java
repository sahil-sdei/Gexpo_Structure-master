package ggn.home.help.web.response;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable{

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("sub_title")
    @Expose
    public String subTitle;
    @SerializedName("years")
    @Expose
    public String years;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("image")
    @Expose
    public String image;
    public boolean isSelected;

    public Category(String id, String name, String subTitle, String years, String status, String image) {
        this.id = id;
        this.name = name;
        this.subTitle = subTitle;
        this.years = years;
        this.status = status;
        this.image = image;
    }
}