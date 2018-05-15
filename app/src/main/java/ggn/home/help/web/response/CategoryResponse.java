package ggn.home.help.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryResponse implements Serializable{

    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("image_path")
    @Expose
    public String imagePath;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
    @SerializedName("message")
    @Expose
    public String message;

    public class Datum implements Serializable{
        @SerializedName("Category")
        @Expose
        public Category category;
        @SerializedName("SubCategory")
        @Expose
        public List<SubCategory> subCategory = null;

        public Datum(Category category, List<SubCategory> subCategory) {
            this.category = category;
            this.subCategory = subCategory;
        }
    }


}