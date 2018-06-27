package ggn.home.help.features.services;

import ggn.home.help.web.Web;
import ggn.home.help.web.response.BasicResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
 
 
public interface ApiInterface {
    @Multipart
    @POST(Web.Path.ADD_MEMORY)
    Call<BasicResponse> addMemory(@Part MultipartBody.Part[] memories, @Part("details") RequestBody details);
}