package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFriendRequest {
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("sender_id")
    @Expose
    public Integer senderId;
    @SerializedName("receiver_id")
    @Expose
    public String receiverId;
    @SerializedName("relation_id")
    @Expose
    public String relationId;
    @SerializedName("type")
    @Expose
    public String type;
}