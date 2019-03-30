package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostsRequest {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("child_id")
    @Expose
    public String childId;
}