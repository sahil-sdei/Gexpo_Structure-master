package ggn.home.help.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FullLifeAlbumResponse implements Serializable {

    @SerializedName("status")
    @Expose
    public int status;
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
    @SerializedName("current_page")
    @Expose
    public int currentPage;
    @SerializedName("pages")
    @Expose
    public int pages;
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
        @SerializedName("memory_id")
        @Expose
        public String memoryId;
        @SerializedName("title")
        @Expose
        public String title;
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
        @SerializedName("memory_type")
        @Expose
        public String memoryType;
        @SerializedName("gallery")
        @Expose
        public String gallery;
        public boolean isSelected;


        public Datum(String categoryName, String subCategoryName, String gallery) {
            this.categoryName = categoryName;
            this.subCategoryName = subCategoryName;
            this.gallery = gallery;
        }
    }
}