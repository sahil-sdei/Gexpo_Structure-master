package ggn.home.help.web.apiInterfaces;

import ggn.home.help.web.Web;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.CategoryResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MemoryAPI {

    @FormUrlEncoded
    @POST(Web.Path.CATEGORIES)
    Observable<CategoryResponse> getCategories(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.ADD_SUGGESTION)
    Observable<BasicResponse> addSuggestion(@Field(Web.Keys.DETAILS) String details);

    @Multipart
    @POST(Web.Path.ADD_MEMORY)
    Observable<BasicResponse> addMemory(@Part MultipartBody.Part[] memories, @Part("details") RequestBody details);

}
