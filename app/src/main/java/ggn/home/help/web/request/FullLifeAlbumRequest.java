package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullLifeAlbumRequest {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("user_id")
    @Expose
    public int userId;
    @SerializedName("category_id")
    @Expose
    public int categoryId;
    @SerializedName("sub_category_id")
    @Expose
    public String subCategoryId;
    @SerializedName("memory_type")
    @Expose
    public String memoryType;
    @SerializedName("child_id")
    @Expose
    public String childId;
}