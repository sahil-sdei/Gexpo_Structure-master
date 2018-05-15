package ggn.home.help.features.friendsNFamily.fragments;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentFriendsFamilyRequestsBinding;
import ggn.home.help.features.dashboard.myMemories.MemoriesPresenter;
import ggn.home.help.features.dashboard.myMemories.MemoriesView;
import ggn.home.help.features.dashboard.myMemories.Memory;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.FamilyAdapter;
import ggn.home.help.web.response.ChildAccountsResponse;


public class FriendsRequestsFragment extends BaseFragment<FragmentFriendsFamilyRequestsBinding, MemoriesPresenter> implements MemoriesView {

    private List<String> list;
    private FamilyAdapter familyAdapter;

    public static FriendsRequestsFragment newInstance() {
        FriendsRequestsFragment friendsRequestsFragment = new FriendsRequestsFragment();
        return friendsRequestsFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friends_family_requests;
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
        getDataBinder().recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        familyAdapter = new FamilyAdapter(list, getActivityG(), getPresenter());
        familyAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewFriends.setAdapter(familyAdapter);
        getDataBinder().recyclerViewFriends.setNestedScrollingEnabled(false);


        getDataBinder().recyclerViewRequests.setHasFixedSize(true);
        getDataBinder().recyclerViewRequests.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        familyAdapter = new FamilyAdapter(list, getActivityG(), getPresenter());
        familyAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewRequests.setAdapter(familyAdapter);
        getDataBinder().recyclerViewRequests.setNestedScrollingEnabled(false);
    }

    @Override
    public void onMemoryLiked(Memory memory) {

    }

    @Override
    public void showChildAccounts(ChildAccountsResponse output) {

    }
}
