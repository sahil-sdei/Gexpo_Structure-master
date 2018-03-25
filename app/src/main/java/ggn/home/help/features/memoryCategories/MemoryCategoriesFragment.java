package ggn.home.help.features.memoryCategories;

import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentMemoriesBinding;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.internal.base.BaseFragment;


public class MemoryCategoriesFragment extends BaseFragment<FragmentRecyclerViewBinding, MemoryCategoriesPresenter> implements MemoryCategoriesView {

    private List<String> list;
    private MemoryCategoriesAdapter memoryCategoriesAdapter;

    public static MemoryCategoriesFragment newInstance() {
        MemoryCategoriesFragment activityFragment = new MemoryCategoriesFragment();
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
        list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        memoryCategoriesAdapter = new MemoryCategoriesAdapter(list, getActivityG(), getPresenter());
        memoryCategoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(memoryCategoriesAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_notifications, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }
}
