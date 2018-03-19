package ggn.home.help.features.addMemories.fragments;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentMemoriesBinding;
import ggn.home.help.databinding.FragmentProfileAboutBinding;
import ggn.home.help.features.addMemories.AddMemoryPresenter;
import ggn.home.help.features.addMemories.AddMemoryView;
import ggn.home.help.features.addMemories.SubCategoriesAdapter;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.ProfilePresenter;
import ggn.home.help.features.profile.ProfileView;


public class SubCategoriesFragment extends BaseFragment<FragmentMemoriesBinding, AddMemoryPresenter> implements AddMemoryView {

    public static SubCategoriesFragment newInstance() {
        SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment();
        return subCategoriesFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_memories;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new AddMemoryPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        List<String> list = new ArrayList<>();
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
        SubCategoriesAdapter subCategoriesAdapter = new SubCategoriesAdapter(list, getActivityG(), getPresenter());
        subCategoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewMemories.setAdapter(subCategoriesAdapter);
    }
}
