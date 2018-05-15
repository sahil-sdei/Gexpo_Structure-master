package ggn.home.help.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("about")
    @Expose
    public About about;
    @SerializedName("posts")
    @Expose
    public String posts;
    @SerializedName("galleries")
    @Expose
    public String galleries;
    @SerializedName("friends")
    @Expose
    public String friends;

    public class About {
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
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
    }
}