package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnfriendRequest {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("friend_id")
    @Expose
    public String friendId;

}