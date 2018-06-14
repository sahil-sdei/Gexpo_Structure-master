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
import ggn.home.help.features.userProfile.UserProfilePresenter;
import ggn.home.help.features.userProfile.UserProfileView;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.ChildAccountsResponse;
import ggn.home.help.web.response.PostsResponse;
import ggn.home.help.web.response.ProfileResponse;
import ggn.home.help.web.response.RelationsResponse;


public class UserProfilePostsFragment extends BaseFragment<FragmentProfilePostsBinding, UserProfilePresenter> implements UserProfileView {

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
        injectPresenter(new UserProfilePresenter());
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
    }

    @Override
    public void showProfileData(ProfileResponse output) {

    }

    @Override
    public void showRelations(RelationsResponse output) {

    }

    @Override
    public void requestSentSuccessfully(BasicResponse output) {

    }

    @Override
    public void unFriendSuccessfully(BasicResponse output) {

    }

    @Override
    public void onMemoryLiked(PostsResponse.Datum memory) {
        memoriesAdapter.notifyDataSetChanged();
    }

    public void setPostsData(List<PostsResponse.Datum> postsData){
        getDataBinder().recyclerViewPosts.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivityG());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        getDataBinder().recyclerViewPosts.setLayoutManager(linearLayoutManager);
        memoriesAdapter = new MemoriesAdapter(postsData, getActivityG(), getPresenter());
        memoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewPosts.setAdapter(memoriesAdapter);
    }
}
