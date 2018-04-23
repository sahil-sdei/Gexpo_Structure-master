package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {

    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("user_id")
    @Expose
    public int userId;
    @SerializedName("old_password")
    @Expose
    public String oldPassword;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("confirm_password")
    @Expose
    public String confirmPassword;

}