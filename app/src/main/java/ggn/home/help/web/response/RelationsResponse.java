package ggn.home.help.web.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelationsResponse {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
    @SerializedName("message")
    @Expose
    public String message;

    public class Datum {
        @SerializedName("Relation")
        @Expose
        public Relation relation;
    }

    public class Relation {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("status")
        @Expose
        public String status;
        public boolean isSelected;
    }
}