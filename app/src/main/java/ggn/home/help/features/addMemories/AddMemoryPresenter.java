package ggn.home.help.features.addMemories;


import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.web.response.SubCategory;

public class AddMemoryPresenter extends BasePresenter<AddMemoryView> implements AddMemoryAdapterBinder {

    @Override
    public void onSubcategoryItemClicked(SubCategory subCategory) {
        getView().showDescriptionFragment(subCategory);
    }
}
