package ggn.home.help.features.userProfile;

import ggn.home.help.features.dashboard.myMemories.MemoriesAdapterBinder;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.web.response.PostsResponse;

public class UserProfilePresenter extends BasePresenter<UserProfileView> implements MemoriesAdapterBinder {

    @Override
    public void onProfileClicked(PostsResponse.Datum memory) {

    }

    @Override
    public void onLikeClicked(PostsResponse.Datum memory) {

    }
}
