package ggn.home.help.features.friendsNFamily;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentFriendsFamilyBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.FamilyAdapter;
import ggn.home.help.features.searchUser.SearchUserActivity;
import ggn.home.help.utils.PagerAdapter;


public class FriendsFamilyFragment extends BaseFragment<FragmentFriendsFamilyBinding, FriendsFamilyPresenter> implements FriendsFamilyView {

    private RequestsAdapter requestsAdapter;

    public static FriendsFamilyFragment newInstance() {
        FriendsFamilyFragment friendsFamilyFragment = new FriendsFamilyFragment();
        return friendsFamilyFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friends_family;
    }

    @Override
    protected void onCreateFragmentG() {
        setHasOptionsMenu(true);
        injectPresenter(new FriendsFamilyPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        getDataBinder().textViewSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchUserActivity.start(getActivityG());
            }
        });

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");


        getDataBinder().recyclerViewRequests.setHasFixedSize(true);
        getDataBinder().recyclerViewRequests.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        requestsAdapter = new RequestsAdapter(list, getActivityG());
        requestsAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewRequests.setAdapter(requestsAdapter);
        getDataBinder().recyclerViewRequests.setNestedScrollingEnabled(false);
    }
}
