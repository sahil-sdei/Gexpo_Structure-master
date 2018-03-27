package ggn.home.help.features.addMemories.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentMemoriesBinding;
import ggn.home.help.databinding.FragmentProfileAboutBinding;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.addMemories.AddMemoryPresenter;
import ggn.home.help.features.addMemories.AddMemoryView;
import ggn.home.help.features.addMemories.SubCategoriesAdapter;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.memoryCategories.Categories;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.features.profile.ProfilePresenter;
import ggn.home.help.features.profile.ProfileView;
import ggn.home.help.utils.Constants;


public class SubCategoriesFragment extends BaseFragment<FragmentRecyclerViewBinding, AddMemoryPresenter> implements AddMemoryView {

    private Categories categoriesObj;

    public static SubCategoriesFragment newInstance() {
        SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment();
        return subCategoriesFragment;
    }

    public static SubCategoriesFragment newInstance(Bundle bundle) {
        SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment();
        subCategoriesFragment.setArguments(bundle);
        return subCategoriesFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new AddMemoryPresenter());
        getPresenter().attachView(this);

        categoriesObj = (Categories) getArguments().getSerializable(Constants.Extras.DATA);
    }

    @Override
    public void initViews() {
        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        SubCategoriesAdapter subCategoriesAdapter = new SubCategoriesAdapter(categoriesObj.listSubcategories, getActivityG(), getPresenter());
        subCategoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(subCategoriesAdapter);
    }

    @Override
    public void showDescriptionFragment() {
        ((AddMemoryActivity)getActivity()).showFragmentWithBackStack(AddDescriptionFragment.newInstance());
    }
}
