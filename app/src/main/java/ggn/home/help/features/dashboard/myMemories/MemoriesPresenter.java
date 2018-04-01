package ggn.home.help.features.dashboard.myMemories;


import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.profile.FamilyFriendsAdapterBinder;
import ggn.home.help.features.profile.ProfileActivity;

public class MemoriesPresenter extends BasePresenter<MemoriesView> implements MemoriesAdapterBinder, FamilyFriendsAdapterBinder {

    @Override
    public void onProfileClicked(Memory memory) {
        ProfileActivity.start(getView().getActivityG());
    }
}
