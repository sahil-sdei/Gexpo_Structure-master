package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountTransferRequest {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("child_id")
    @Expose
    public String childId;
    @SerializedName("email")
    @Expose
    public String email;
}