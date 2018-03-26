package ggn.home.help.features.friendsNFamily;

import android.support.v4.view.ViewPager;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentFriendsFamilyBinding;
import ggn.home.help.features.friendsNFamily.fragments.FriendsRequestsFragment;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.searchUser.SearchUserActivity;
import ggn.home.help.utils.PagerAdapter;


public class FriendsFamilyFragment extends BaseFragment<FragmentFriendsFamilyBinding, FriendsFamilyPresenter> implements FriendsFamilyView {

    private PagerAdapter adapter;

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
        getDataBinder().editTextSearchName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    SearchUserActivity.start(getActivityG());
                }
            }
        });

        setupViewPager(getDataBinder().viewPager);
        getDataBinder().tabs.setupWithViewPager(getDataBinder().viewPager);
        getDataBinder().viewPager.setOffscreenPageLimit(2);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFrag(FriendsRequestsFragment.newInstance(), getString(R.string.friends));
        adapter.addFrag(FriendsRequestsFragment.newInstance(), getString(R.string.family));
        viewPager.setAdapter(adapter);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.menu_notifications, menu);
//        super.onCreateOptionsMenu(menu,inflater);
//    }
}
