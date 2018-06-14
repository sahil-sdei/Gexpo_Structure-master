package ggn.home.help.web.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllFamilyResponse {

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

    public class Datum {
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