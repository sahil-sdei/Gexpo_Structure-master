package ggn.home.help.features.memoryCategories;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentMemoriesBinding;
import ggn.home.help.features.internal.base.BaseFragment;


public class MemoryCategoriesFragment extends BaseFragment<FragmentMemoriesBinding, MemoryCategoriesPresenter> implements MemoryCategoriesView {

    private List<String> list;
    private MemoryCategoriesAdapter memoryCategoriesAdapter;

    public static MemoryCategoriesFragment newInstance() {
        MemoryCategoriesFragment activityFragment = new MemoryCategoriesFragment();
        return activityFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_memories;
    }

    @Override
    protected void onCreateFragmentG() {
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

        getDataBinder().recyclerViewMemories.setHasFixedSize(true);
        getDataBinder().recyclerViewMemories.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        memoryCategoriesAdapter = new MemoryCategoriesAdapter(list, getActivityG(), getPresenter());
        memoryCategoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewMemories.setAdapter(memoryCategoriesAdapter);
    }
}
