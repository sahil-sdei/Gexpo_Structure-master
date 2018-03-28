package ggn.home.help.features.profile;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentMemoriesBinding;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.dashboard.myMemories.MemoriesPresenter;
import ggn.home.help.features.dashboard.myMemories.MemoriesView;
import ggn.home.help.features.internal.base.BaseFragment;


public class FamilyFragment extends BaseFragment<FragmentRecyclerViewBinding, MemoriesPresenter> implements MemoriesView {

    private List<String> list;
    private FamilyAdapter familyAdapter;

    public static FamilyFragment newInstance() {
        FamilyFragment activityFragment = new FamilyFragment();
        return activityFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
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
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        familyAdapter = new FamilyAdapter(list, getActivityG(), getPresenter());
        familyAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(familyAdapter);
    }
}