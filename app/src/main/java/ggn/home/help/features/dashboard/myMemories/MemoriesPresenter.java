package ggn.home.help.features.dashboard.myMemories;


import com.google.gson.Gson;

import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.profile.FamilyFriendsAdapterBinder;
import ggn.home.help.features.userProfile.UserProfileActivity;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.PostsAPI;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.ChildAccountsRequest;
import ggn.home.help.web.request.LikePostRequest;
import ggn.home.help.web.request.PostsRequest;
import ggn.home.help.web.response.ChildAccountsResponse;
import ggn.home.help.web.response.LikePostResponse;
import ggn.home.help.web.response.PostsResponse;

public class MemoriesPresenter extends BasePresenter<MemoriesView> implements MemoriesAdapterBinder, FamilyFriendsAdapterBinder {

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

    public void getChildAccounts() {
        ChildAccountsRequest childAccountsRequest = new ChildAccountsRequest();
        childAccountsRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        childAccountsRequest.token = getView().getLocalData().getAuthToken();
        childAccountsRequest.page = 1;

        Gson gson = new Gson();

        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getChildAccounts(gson.toJson(childAccountsRequest)), new CallBackG<ChildAccountsResponse>() {
            @Override
            public void callBack(ChildAccountsResponse output) {
                if (output.status == 1)
                    getView().showChildAccounts(output);
                else
                    getView().displayError(output.message);
            }
        });
    }

    public void getUserPosts(int pageNo) {
        PostsRequest postsRequest = new PostsRequest();
        postsRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        postsRequest.token = getView().getLocalData().getAuthToken();
        postsRequest.page = pageNo;

        Gson gson = new Gson();
        createApiRequest(getRetrofitInstance(PostsAPI.class)
                .getUserPosts(gson.toJson(postsRequest)), new CallBackG<PostsResponse>() {
            @Override
            public void callBack(PostsResponse output) {
                if (output.status == 1)
                    getView().showUserPosts(output);
                else
                    getView().displayError(output.message);
            }
        });
    }
}
