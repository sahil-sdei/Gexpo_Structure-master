package ggn.home.help.features.addMemories;


import ggn.home.help.features.internal.base.BasePresenter;

public class AddMemoryPresenter extends BasePresenter<AddMemoryView> implements AddMemoryAdapterBinder {

    @Override
    public void onSubcategoryItemClicked() {
        getView().showDescriptionFragment();
    }
}
