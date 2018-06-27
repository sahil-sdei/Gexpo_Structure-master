package ggn.home.help.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostsResponse implements Serializable{

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

    public class Datum implements Serializable{
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("category_name")
        @Expose
        public String categoryName;
        @SerializedName("sub_category_id")
        @Expose
        public String subCategoryId;
        @SerializedName("sub_category_name")
        @Expose
        public String subCategoryName;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("like_count")
        @Expose
        public String likeCount;
        @SerializedName("comment_count")
        @Expose
        public String commentCount;
        @SerializedName("user")
        @Expose
        public User user;
        @SerializedName("galleries")
        @Expose
        public List<Gallery> galleries = null;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("is_user_liked")
        @Expose
        public Integer isUserLiked;
    }
}