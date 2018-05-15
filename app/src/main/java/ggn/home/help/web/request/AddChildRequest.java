package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddChildRequest {

    @SerializedName("user_id")
    @Expose
    public int userId;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("nickname")
    @Expose
    public String nickname;
    @SerializedName("dob")
    @Expose
    public String dob;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("life_lession")
    @Expose
    public String lifeLession;

}