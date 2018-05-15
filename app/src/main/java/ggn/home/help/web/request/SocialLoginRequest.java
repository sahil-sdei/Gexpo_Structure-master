package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialLoginRequest {

    @SerializedName("social_id")
    @Expose
    public String socialId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("registration_type")
    @Expose
    public Integer registrationType;
    @SerializedName("login_type")
    @Expose
    public String loginType;
    @SerializedName("device_token")
    @Expose
    public String deviceToken;
    @SerializedName("email")
    @Expose
    public String email;
}