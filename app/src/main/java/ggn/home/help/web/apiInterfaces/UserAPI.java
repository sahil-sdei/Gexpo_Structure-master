package ggn.home.help.web.apiInterfaces;

import ggn.home.help.web.Web;
import ggn.home.help.web.response.AllFamilyResponse;
import ggn.home.help.web.response.AllFriendsResponse;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.ChildAccountsResponse;
import ggn.home.help.web.response.FriendRequestsResponse;
import ggn.home.help.web.response.FullLifeAlbumResponse;
import ggn.home.help.web.response.ProfileResponse;
import ggn.home.help.web.response.RelationsResponse;
import ggn.home.help.web.response.SearchUserResponse;
import ggn.home.help.web.response.UserPrivacyResponse;
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

    @Multipart
    @POST(Web.Path.EDIT_PROFILE)
    Observable<BasicResponse> editProfile(@Part MultipartBody.Part file, @Part("user_details") RequestBody details);

    @FormUrlEncoded
    @POST(Web.Path.SEARCH_USERS)
    Observable<SearchUserResponse> searchUser(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.RELATIONS)
    Observable<RelationsResponse> getRelations(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.ADD_FRIEND)
    Observable<BasicResponse> sendRequest(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.FRIEND_REQUEST)
    Observable<FriendRequestsResponse> getRequests(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.ACCEPT_REQUEST)
    Observable<BasicResponse> acceptRejectRequest(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.FRIENDS_LIST)
    Observable<AllFriendsResponse> getFriendsList(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.FAMILY_LIST)
    Observable<AllFamilyResponse> getFamilyList(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.UN_FRIEND)
    Observable<BasicResponse> unFriend(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.ACCOUNT_TRANSFER)
    Observable<BasicResponse> accountTransfer(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.DELETE_ACCOUNT)
    Observable<BasicResponse> deleteChildAccount(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.USER_PRIVACY)
    Observable<UserPrivacyResponse> getPrivacy(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.PRIVACY_SETTING)
    Observable<BasicResponse> setPrivacy(@Field(Web.Keys.DETAILS) String details);
}
