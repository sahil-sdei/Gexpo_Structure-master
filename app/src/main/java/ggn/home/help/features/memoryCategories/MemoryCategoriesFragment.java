package ggn.home.help.features.memoryCategories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.notifications.NotificationActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.response.CategoryResponse;


public class MemoryCategoriesFragment extends BaseFragment<FragmentRecyclerViewBinding, MemoryCategoriesPresenter> implements MemoryCategoriesView {

    private MemoryCategoriesAdapter memoryCategoriesAdapter;

    public static MemoryCategoriesFragment newInstance(boolean isMemory) {
        MemoryCategoriesFragment activityFragment = new MemoryCategoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.Extras.IS_MEMORY, isMemory);
        activityFragment.setArguments(bundle);
        return activityFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void onCreateFragmentG() {
        setHasOptionsMenu(true);
        injectPresenter(new MemoryCategoriesPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        getDataBinder().swipeRefreshLayout.setRefreshing(false);
        getDataBinder().swipeRefreshLayout.setEnabled(false);

        getPresenter().getCategories();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_notifications, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void goToAddMemories(CategoryResponse.Datum categories) {
        Intent intent = new Intent(getActivityG(), AddMemoryActivity.class);
        intent.putExtra(Constants.Extras.DATA, categories);
        intent.putExtra(Constants.Extras.IS_MEMORY, getArguments().getBoolean(Constants.Extras.IS_MEMORY));
        intent.putExtra(Constants.Extras.BASE_URL_IMAGE, memoryCategoriesAdapter.getBaseUrl());

        getActivity().startActivityForResult(intent, Constants.RequestCode.ADD_MEMORY);
    }

    @Override
    public void showCategories(CategoryResponse output) {
        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        memoryCategoriesAdapter = new MemoryCategoriesAdapter(output.data, getActivityG(), getPresenter());
        memoryCategoriesAdapter.setBaseUrl(output.imagePath);
        memoryCategoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(memoryCategoriesAdapter);

        getPresenter().setImageBaseUrl(output.imagePath);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notifications:
                NotificationActivity.start(getActivityG());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
