package ggn.home.help.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikePostResponse {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("liked_status")
    @Expose
    public Integer likedStatus;
    @SerializedName("message")
    @Expose
    public String message;

}