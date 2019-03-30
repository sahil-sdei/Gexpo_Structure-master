package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteCommentRequest {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("user_id")
    @Expose
    public Integer userId;

}