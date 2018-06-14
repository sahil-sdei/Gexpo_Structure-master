package ggn.home.help.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("social_id")
    @Expose
    public Object socialId;
    @SerializedName("parent_id")
    @Expose
    public String parentId;
    @SerializedName("username")
    @Expose
    public Object username;
    @SerializedName("email")
    @Expose
    public Object email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("user_type")
    @Expose
    public String userType;
    @SerializedName("profile_image")
    @Expose
    public String profileImage;
    @SerializedName("registration_type")
    @Expose
    public String registrationType;
    @SerializedName("login_type")
    @Expose
    public String loginType;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("device_token")
    @Expose
    public String deviceToken;
    @SerializedName("secure_token")
    @Expose
    public Object secureToken;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("UserProfile")
    @Expose
    public UserProfile userProfile;

}