package ggn.home.help.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gallery {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("memory_gallery_id")
    @Expose
    public String memoryGalleryId;
    @SerializedName("gallery_image")
    @Expose
    public String galleryImage;
    @SerializedName("memory_type")
    @Expose
    public String memoryType;

}