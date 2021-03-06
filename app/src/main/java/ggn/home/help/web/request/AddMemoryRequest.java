package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddMemoryRequest implements Serializable{

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("user_id")
    @Expose
    public int userId;
    @SerializedName("category_id")
    @Expose
    public String categoryId;
    @SerializedName("sub_category_id")
    @Expose
    public String subCategoryId;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("child_id")
    @Expose
    public String childId;

}