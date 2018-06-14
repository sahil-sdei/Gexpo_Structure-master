package ggn.home.help.features.userProfile;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.PostsResponse;
import ggn.home.help.web.response.ProfileResponse;
import ggn.home.help.web.response.RelationsResponse;

public interface UserProfileView extends Viewable<UserProfilePresenter> {

    void showProfileData(ProfileResponse output);

    void showRelations(RelationsResponse output);

    void requestSentSuccessfully(BasicResponse output);

    void unFriendSuccessfully(BasicResponse output);

    void onMemoryLiked(PostsResponse.Datum memory);
}