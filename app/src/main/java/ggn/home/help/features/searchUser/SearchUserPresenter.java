package ggn.home.help.features.searchUser;


import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.userProfile.UserProfileActivity;

public class SearchUserPresenter extends BasePresenter<SearchUserView> implements SearchUserAdapterBinder {

    @Override
    public void onItemClicked() {
        UserProfileActivity.start(getView().getActivityG());
    }
}
