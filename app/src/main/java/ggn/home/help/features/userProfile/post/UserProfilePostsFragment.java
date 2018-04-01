package ggn.home.help.features.userProfile.post;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentProfilePostsBinding;
import ggn.home.help.features.dashboard.myMemories.MemoriesAdapter;
import ggn.home.help.features.dashboard.myMemories.MemoriesPresenter;
import ggn.home.help.features.dashboard.myMemories.MemoriesView;
import ggn.home.help.features.dashboard.myMemories.Memory;
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

        List<Memory> listM = new ArrayList<>();
        List<String> listImages = new ArrayList<>();
        listImages.add("pro");
        listImages.add("pro");
        listImages.add("pro");
        listImages.add("pro");

        listM.add(new Memory("Stan Smith", "pro", "5", "2", "img1", listImages));
        listM.add(new Memory("John Smith", "pro", "13", "3", "img2", listImages));
        listM.add(new Memory("Steve Smith", "pro", "2", "4", "img3", listImages));
        listM.add(new Memory("John Ward", "pro", "3", "6", "img4", listImages));
        listM.add(new Memory("Alexander", "pro", "13", "2", "img5", listImages));

        getDataBinder().recyclerViewPosts.setHasFixedSize(true);
        getDataBinder().recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        memoriesAdapter = new MemoriesAdapter(listM, getActivityG(), getPresenter());
        memoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewPosts.setAdapter(memoriesAdapter);
        getDataBinder().recyclerViewPosts.setNestedScrollingEnabled(false);
    }
}
