package ggn.home.help.web.request;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPostRequest {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("category_id")
    @Expose
    public String categoryId;
    @SerializedName("sub_category_id")
    @Expose
    public String subCategoryId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("privacy")
    @Expose
    public String privacy;
    @SerializedName("galleries")
    @Expose
    public List<Integer> galleries = null;
    @SerializedName("tags")
    @Expose
    public List<Integer> tags = null;
    @SerializedName("shared_user_ids")
    @Expose
    public List<Integer> sharedUserIds = null;

}