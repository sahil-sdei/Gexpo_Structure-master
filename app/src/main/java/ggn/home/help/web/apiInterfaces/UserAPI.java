package ggn.home.help.web.apiInterfaces;

import ggn.home.help.web.Web;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.ChildAccountsResponse;
import ggn.home.help.web.response.FullLifeAlbumResponse;
import ggn.home.help.web.response.ProfileResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserAPI {

    @Multipart
    @POST(Web.Path.ADD_CHILD)
    Observable<BasicResponse> addChildProfile(@Part MultipartBody.Part file, @Part("details") RequestBody details);

    @FormUrlEncoded
    @POST(Web.Path.FULL_LIFE_ALBUM)
    Observable<FullLifeAlbumResponse> getFullLifeAlbum(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.CHILD_ACCOUNTS)
    Observable<ChildAccountsResponse> getChildAccounts(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.PROFILE)
    Observable<ProfileResponse> getProfile(@Field(Web.Keys.DETAILS) String details);
}