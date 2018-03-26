package ggn.home.help.features.userProfile.post;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentProfilePostsBinding;
import ggn.home.help.features.dashboard.myMemories.MemoriesAdapter;
import ggn.home.help.features.dashboard.myMemories.MemoriesPresenter;
import ggn.home.help.features.dashboard.myMemories.MemoriesView;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.FamilyAdapter;


public class UserProfilePostsFragment extends BaseFragment<FragmentProfilePostsBinding, MemoriesPresenter> implements MemoriesView {

    private List<String> list;
    private UserFriendsAdapter userFriendsAdapter;
    private MemoriesAdapter memoriesAdapter;

    public static UserProfilePostsFragment newInstance() {
        UserProfilePostsFragment userProfilePostsFragment = new UserProfilePostsFragment();
        return userProfilePostsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile_posts;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new MemoriesPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");

        getDataBinder().recyclerViewFriends.setHasFixedSize(true);
        getDataBinder().recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.HORIZONTAL, false));
        userFriendsAdapter = new UserFriendsAdapter(list, getActivityG(), null);
        userFriendsAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewFriends.setAdapter(userFriendsAdapter);
        getDataBinder().recyclerViewFriends.setNestedScrollingEnabled(false);


        getDataBinder().recyclerViewPosts.setHasFixedSize(true);
        getDataBinder().recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        memoriesAdapter = new MemoriesAdapter(list, getActivityG(), getPresenter());
        memoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewPosts.setAdapter(memoriesAdapter);
        getDataBinder().recyclerViewPosts.setNestedScrollingEnabled(false);
    }
}
