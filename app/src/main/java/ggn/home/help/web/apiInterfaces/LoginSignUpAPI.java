package ggn.home.help.web.apiInterfaces;

import ggn.home.help.web.Web;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.LoginResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginSignUpAPI {

    @FormUrlEncoded
    @POST(Web.Path.SIGN_IN)
    Observable<LoginResponse> login(@Field(Web.Keys.LOGIN_DETAILS) String loginDetails);

    @FormUrlEncoded
    @POST(Web.Path.SIGN_UP)
    Observable<LoginResponse> register(@Field(Web.Keys.USER_DETAILS) String userDetails);

    @FormUrlEncoded
    @POST(Web.Path.FORGOT_PASSWORD)
    Observable<BasicResponse> getPassword(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.CHANGE_PASSWORD)
    Observable<BasicResponse> changePassword(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.SOCIAL_LOGIN)
    Observable<LoginResponse> socialLogin(@Field(Web.Keys.LOGIN_DETAILS) String details);

}
