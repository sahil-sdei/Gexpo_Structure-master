package ggn.home.help.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileRequest {

    @SerializedName("user_id")
    @Expose
    public Integer userId;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("name")
    @Expose
    public String name;
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
