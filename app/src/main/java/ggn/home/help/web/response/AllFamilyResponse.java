package ggn.home.help.web.response;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllFamilyResponse implements Serializable{

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("current_page")
    @Expose
    public Integer currentPage;
    @SerializedName("pages")
    @Expose
    public Integer pages;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;

    public class Datum implements Serializable{
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        public boolean isSelected;
    }
}