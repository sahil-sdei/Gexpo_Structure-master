package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptRequest {

    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("sender_user_id")
    @Expose
    public Integer senderUserId;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("action")
    @Expose
    public String action;

}