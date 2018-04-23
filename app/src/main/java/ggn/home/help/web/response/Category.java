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

}