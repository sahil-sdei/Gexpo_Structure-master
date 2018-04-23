package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("registration_type")
    @Expose
    public int registrationType;
    @SerializedName("login_type")
    @Expose
    public String loginType;

}
