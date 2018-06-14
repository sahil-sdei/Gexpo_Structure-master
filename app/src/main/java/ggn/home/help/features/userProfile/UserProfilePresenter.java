package ggn.home.help.features.userProfile;

import android.util.Log;

import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.dashboard.myMemories.MemoriesAdapterBinder;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.PostsAPI;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.AddFriendRequest;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.request.LikePostRequest;
import ggn.home.help.web.request.ProfileRequest;
import ggn.home.help.web.request.UnfriendRequest;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.LikePostResponse;
import ggn.home.help.web.response.PostsResponse;
import ggn.home.help.web.response.ProfileResponse;
import ggn.home.help.web.response.RelationsResponse;

public class UserProfilePresenter extends BasePresenter<UserProfileView> implements MemoriesAdapterBinder {

    @Override
    public void onProfileClicked(PostsResponse.Datum memory) {
        UserProfileActivity.start(getView().getActivityG(), memory.user.id);
    }

    @Override
    public void onLikeClicked(PostsResponse.Datum memory) {
        likeUnlikePost(memory);
    }

    public void likeUnlikePost(final PostsResponse.Datum memory) {
        LikePostRequest likePostRequest= new LikePostRequest();
        likePostRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        likePostRequest.token = getView().getLocalData().getAuthToken();
        likePostRequest.postId = memory.id;

        Gson gson = new Gson();

        createApiRequest(getRetrofitInstance(PostsAPI.class)
                .likeUnlikePost(gson.toJson(likePostRequest)), new CallBackG<LikePostResponse>() {
            @Override
            public void callBack(LikePostResponse output) {
                if (output.status == 1) {
                    memory.isUserLiked = output.likedStatus;
                    int likeCount = Integer.parseInt(memory.likeCount);
                    if (memory.isUserLiked == 1) {
                        likeCount++;
                        memory.likeCount = String.valueOf(likeCount);
                    } else {
                        likeCount--;
                        memory.likeCount = String.valueOf(likeCount);
                    }
                    getView().onMemoryLiked(memory);
                }
                else
                    getView().displayError(output.message);
            }
        });
    }

    public void getProfile(final ProfileRequest profileRequest) {
        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        Gson gson = new Gson();

       // Log.d("ProfileRequest", gson.toJson(profileRequest));

        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getProfile(gson.toJson(profileRequest)), new CallBackG<ProfileResponse>() {
            @Override
            public void callBack(ProfileResponse output) {
                if (output.status == 1) {
                    getView().showProfileData(output);
                    BasicRequest basicRequest = new BasicRequest();
                    basicRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
                    basicRequest.token = getView().getLocalData().getAuthToken();
                    getRelations(basicRequest);
                } else {
                    getView().displayError(output.message);
                    getView().hideLoading();
                }
            }
        });
    }

    public void getRelations(BasicRequest basicRequest) {
        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getRelations(gson.toJson(basicRequest)), new CallBackG<RelationsResponse>() {
            @Override
            public void callBack(RelationsResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showRelations(output);
                else
                    getView().displayError(output.message);
            }
        });
    }

    public void sendRequest(AddFriendRequest addFriendRequest) {
        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .sendRequest(gson.toJson(addFriendRequest)), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                getView().hideLoading();
                if (output.status == 1) {
                    getView().requestSentSuccessfully(output);
                } else {
                    getView().displayError(output.message);
                }
            }
        });
    }

    public void unFriend(String friendId) {
        UnfriendRequest unfriendRequest = new UnfriendRequest();
        unfriendRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        unfriendRequest.token = getView().getLocalData().getAuthToken();
        unfriendRequest.friendId = friendId;


        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .unFriend(gson.toJson(unfriendRequest)), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().unFriendSuccessfully(output);
                getView().displayError(output.message);
            }
        });
    }
}
