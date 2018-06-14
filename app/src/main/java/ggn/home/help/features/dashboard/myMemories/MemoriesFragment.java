package ggn.home.help.features.dashboard.myMemories;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentMemoriesBinding;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.memoryCategories.MemoryCategoriesFragment;
import ggn.home.help.features.notifications.NotificationActivity;
import ggn.home.help.web.response.ChildAccountsResponse;
import ggn.home.help.web.response.PostsResponse;


public class MemoriesFragment extends BaseFragment<FragmentMemoriesBinding, MemoriesPresenter> implements MemoriesView, SwipeRefreshLayout.OnRefreshListener {

    private MemoriesAdapter memoriesAdapter;
    private int PAGE_NO = 1;

    public static MemoriesFragment newInstance() {
        MemoriesFragment activityFragment = new MemoriesFragment();
        return activityFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_memories;
    }

    @Override
    protected void onCreateFragmentG() {
        setHasOptionsMenu(true);
        injectPresenter(new MemoriesPresenter());
        getPresenter().attachView(this);

    }

    @Override
    public void initViews() {
        getPresenter().getChildAccounts();

        getDataBinder().textViewAddYourMemories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DashboardActivity) getActivity()).showFragmentWithBackStack(MemoryCategoriesFragment.newInstance(false), getString(R.string.post_memory));

            }
        });

        getDataBinder().swipeRefreshLayout.setOnRefreshListener(this);
        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getUserPosts(PAGE_NO);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_memories, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:
                ((DashboardActivity) getActivity()).showFragmentWithBackStack(MemoryCategoriesFragment.newInstance(false), getString(R.string.post_memory));
                return true;
            case R.id.action_notifications:
                NotificationActivity.start(getActivityG());
                return true;

            default:
                break;
        }

        return false;

    }

    @Override
    public void onMemoryLiked(PostsResponse.Datum memory) {
        memoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showChildAccounts(ChildAccountsResponse output) {
        ((DashboardActivity) getActivity()).setUpAccounts(output.data.childs);
    }

    @Override
    public void showUserPosts(PostsResponse output) {
        if (output.data != null) {
            getDataBinder().swipeRefreshLayout.setRefreshing(false);
            getDataBinder().recyclerViewMemories.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivityG());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            getDataBinder().recyclerViewMemories.setLayoutManager(linearLayoutManager);
            memoriesAdapter = new MemoriesAdapter(output.data, getActivityG(), getPresenter());
            memoriesAdapter.setShouldLoadMore(false);
            getDataBinder().recyclerViewMemories.setAdapter(memoriesAdapter);
            getDataBinder().textViewAddYourMemories.setVisibility(View.INVISIBLE);
        } else {
            getDataBinder().textViewAddYourMemories.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getUserPosts(PAGE_NO);
    }
}
