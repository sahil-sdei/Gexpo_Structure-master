package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicPageRequest {

    @SerializedName("user_id")
    @Expose
    public int userId;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("page")
    @Expose
    public int page;
}