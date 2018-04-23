package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @SerializedName("result")
    @Expose
    public boolean result;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("registration_type")
    @Expose
    public int registrationType;
    @SerializedName("device_token")
    @Expose
    public String deviceToken;

}