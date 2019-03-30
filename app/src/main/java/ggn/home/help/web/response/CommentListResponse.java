package ggn.home.help.web.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentListResponse {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("current_page")
    @Expose
    public Integer currentPage;
    @SerializedName("pages")
    @Expose
    public Integer pages;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;

    public class Datum {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("post_id")
        @Expose
        public String postId;
        @SerializedName("comment")
        @Expose
        public String comment;
        @SerializedName("user")
        @Expose
        public User user;
        @SerializedName("date")
        @Expose
        public String date;
    }

}