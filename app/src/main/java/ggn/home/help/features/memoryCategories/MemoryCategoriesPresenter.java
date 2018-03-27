package ggn.home.help.features.memoryCategories;


import ggn.home.help.features.internal.base.BasePresenter;

public class MemoryCategoriesPresenter extends BasePresenter<MemoryCategoriesView> implements MemoryCategoriesAdapterBinder {

    @Override
    public void onItemClicked(Categories categories) {
        getView().goToAddMemories(categories);
    }
}
