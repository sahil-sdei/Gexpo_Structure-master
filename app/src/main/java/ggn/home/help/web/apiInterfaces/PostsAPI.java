package ggn.home.help.web.apiInterfaces;

import ggn.home.help.web.Web;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.CommentListResponse;
import ggn.home.help.web.response.LikePostResponse;
import ggn.home.help.web.response.PostsResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostsAPI {

    @FormUrlEncoded
    @POST(Web.Path.ADD_POST)
    Observable<BasicResponse> addPost(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.POSTS)
    Observable<PostsResponse> getUserPosts(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.COMMENT_LIST)
    Observable<CommentListResponse> getPostComments(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.LIKE_POST)
    Observable<LikePostResponse> likeUnlikePost(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.COMMENT)
    Observable<BasicResponse> addComment(@Field(Web.Keys.DETAILS) String details);

    @FormUrlEncoded
    @POST(Web.Path.DELETE_COMMENT)
    Observable<BasicResponse> deleteComment(@Field(Web.Keys.DETAILS) String details);

}
