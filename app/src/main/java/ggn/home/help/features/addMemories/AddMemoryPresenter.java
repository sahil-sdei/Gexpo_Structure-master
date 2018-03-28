package ggn.home.help.features.addMemories;


import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.memoryCategories.SubCategories;

public class AddMemoryPresenter extends BasePresenter<AddMemoryView> implements AddMemoryAdapterBinder {

    @Override
    public void onSubcategoryItemClicked(SubCategories subCategories) {
        getView().showDescriptionFragment(subCategories);
    }
}
