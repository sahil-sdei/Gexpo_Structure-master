package ggn.home.help.features.userProfile.post;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentProfilePostsBinding;
import ggn.home.help.features.dashboard.myMemories.MemoriesAdapter;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.userProfile.UserProfilePresenter;
import ggn.home.help.features.userProfile.UserProfileView;
import ggn.home.help.web.response.BasicResponse;
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
//        getDataBinder().recyclerViewFriends.setHasFixedSize(true);
//        getDataBinder().recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.HORIZONTAL, false));
//        userFriendsAdapter = new UserFriendsAdapter(list, getActivityG(), null);
//        userFriendsAdapter.setShouldLoadMore(false);
//        getDataBinder().recyclerViewFriends.setAdapter(userFriendsAdapter);
//        getDataBinder().recyclerViewFriends.setNestedScrollingEnabled(false);
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

    public void setPostsData(List<PostsResponse.Datum> postsData) {
        if(postsData.size()>0) {
            getDataBinder().recyclerViewPosts.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivityG());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            getDataBinder().recyclerViewPosts.setLayoutManager(linearLayoutManager);
            memoriesAdapter = new MemoriesAdapter(postsData, getActivityG(), getPresenter());
            memoriesAdapter.setShouldLoadMore(false);
            getDataBinder().recyclerViewPosts.setAdapter(memoriesAdapter);
            getDataBinder().textViewNoRecords.setVisibility(View.GONE);
        }else {
            getDataBinder().textViewNoRecords.setVisibility(View.VISIBLE);
            getDataBinder().textViewNoRecords.setText("No posts yet");
        }
    }
}
