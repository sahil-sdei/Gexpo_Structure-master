package ggn.home.help.features.userProfile.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.ProfilePresenter;
import ggn.home.help.features.profile.ProfileView;
import ggn.home.help.features.userProfile.adapters.UserFriendsAdapter;
import ggn.home.help.web.response.ProfileResponse;


public class UserFriendsFragment extends BaseFragment<FragmentRecyclerViewBinding, ProfilePresenter> implements ProfileView {

    private List<String> list;
    private UserFriendsAdapter friendsAdapter;

    public static UserFriendsFragment newInstance() {
        UserFriendsFragment activityFragment = new UserFriendsFragment();
        return activityFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new ProfilePresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        getDataBinder().swipeRefreshLayout.setRefreshing(false);
        getDataBinder().swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void showProfileData(ProfileResponse output) {
        if (output.friends.size() == 0) {
            getDataBinder().textViewNoRecords.setText("No friends");
            getDataBinder().textViewNoRecords.setVisibility(View.VISIBLE);
            return;
        }

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        friendsAdapter = new UserFriendsAdapter(output.friends, getActivityG());
        friendsAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(friendsAdapter);
    }
}
