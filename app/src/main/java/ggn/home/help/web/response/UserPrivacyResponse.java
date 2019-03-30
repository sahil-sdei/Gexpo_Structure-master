package ggn.home.help.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPrivacyResponse {
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("privacy")
    @Expose
    public String privacy;
    @SerializedName("message")
    @Expose
    public String message;
}