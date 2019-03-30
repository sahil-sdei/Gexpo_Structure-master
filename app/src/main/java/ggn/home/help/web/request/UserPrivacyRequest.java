package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPrivacyRequest {
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("privacy")
    @Expose
    public String privacy;
}