package ggn.home.help.features.dashboard.myMemories;

import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentMemoriesBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.memoryCategories.MemoryCategoriesFragment;


public class MemoriesFragment extends BaseFragment<FragmentMemoriesBinding, MemoriesPresenter> implements MemoriesView {

    private List<String> list;
    private MemoriesAdapter memoriesAdapter;

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
        memoriesAdapter = new MemoriesAdapter(list, getActivityG(), getPresenter());
        memoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewMemories.setAdapter(memoriesAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_memories, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:
                ((DashboardActivity)getActivity()).showFragmentWithBackStack(MemoryCategoriesFragment.newInstance());
                return true;
            case R.id.action_notifications:
                return true;

            default:
                break;
        }

        return false;

    }
}
