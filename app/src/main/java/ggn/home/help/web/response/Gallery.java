package ggn.home.help.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Gallery implements Serializable{

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("memory_type")
    @Expose
    public String memoryType;
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
    @SerializedName("memory_gallery_id")
    @Expose
    public String memoryGalleryId;
    @SerializedName("gallery_image")
    @Expose
    public String galleryImage;
    @SerializedName("gallery")
    @Expose
    public String gallery;

}