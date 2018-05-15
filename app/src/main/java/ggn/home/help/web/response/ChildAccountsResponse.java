package ggn.home.help.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChildAccountsResponse {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("profile_path")
    @Expose
    public String profilePath;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {
        @SerializedName("User")
        @Expose
        public User user;
        @SerializedName("UserProfile")
        @Expose
        public UserProfile userProfile;
        @SerializedName("Childs")
        @Expose
        public List<Child> childs = null;
    }
}