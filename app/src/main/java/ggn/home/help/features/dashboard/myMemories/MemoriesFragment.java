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
import ggn.home.help.features.notifications.NotificationActivity;


public class MemoriesFragment extends BaseFragment<FragmentMemoriesBinding, MemoriesPresenter> implements MemoriesView {

    private List<Memory> list;
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
        List<String> listImages = new ArrayList<>();
        listImages.add("pro");
        listImages.add("pro");
        listImages.add("pro");
        listImages.add("pro");


        list.add(new Memory("Stan Smith", "pro", "5", "2", "img1", listImages));
        list.add(new Memory("John Smith", "pro", "13", "3", "img2", listImages));
        list.add(new Memory("Steve Smith", "pro", "2", "4", "img3", listImages));
        list.add(new Memory("John Ward", "pro", "3", "6", "img4", listImages));
        list.add(new Memory("Alexander", "pro", "13", "2", "img5", listImages));


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
                ((DashboardActivity)getActivity()).showFragmentWithBackStack(MemoryCategoriesFragment.newInstance(false), getString(R.string.add_memories));
                return true;
            case R.id.action_notifications:
                NotificationActivity.start(getActivityG());
                return true;

            default:
                break;
        }

        return false;

    }
}
