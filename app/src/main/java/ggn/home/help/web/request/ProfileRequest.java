package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileRequest {

    @SerializedName("user_id")
    @Expose
    public int userId;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("logged_user_id")
    @Expose
    public String loggedUserId;
    @SerializedName("tab")
    @Expose
    public int tab;
    @SerializedName("page")
    @Expose
    public int page;
}