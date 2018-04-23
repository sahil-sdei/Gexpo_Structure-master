package ggn.home.help.web.apiInterfaces;

import ggn.home.help.web.Web;
import ggn.home.help.web.response.CategoryResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MemoryAPI {

    @FormUrlEncoded
    @POST(Web.Path.CATEGORIES)
    Observable<CategoryResponse> getCategories(@Field(Web.Keys.DETAILS) String details);

}
