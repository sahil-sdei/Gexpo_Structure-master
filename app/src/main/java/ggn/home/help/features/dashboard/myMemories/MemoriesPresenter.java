package ggn.home.help.features.dashboard.myMemories;


import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.profile.FamilyFriendsAdapterBinder;
import ggn.home.help.features.userProfile.UserProfileActivity;

public class MemoriesPresenter extends BasePresenter<MemoriesView> implements MemoriesAdapterBinder, FamilyFriendsAdapterBinder {

    @Override
    public void onProfileClicked(Memory memory) {
        UserProfileActivity.start(getView().getActivityG());
    }
}
