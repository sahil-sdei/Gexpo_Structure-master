package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddSuggestionRequest {

    @SerializedName("user_id")
    @Expose
    public int userId;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("category_id")
    @Expose
    public int categoryId;
    @SerializedName("name")
    @Expose
    public String name;

}