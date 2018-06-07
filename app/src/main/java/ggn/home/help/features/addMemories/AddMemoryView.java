package ggn.home.help.features.addMemories;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.SubCategory;

public interface AddMemoryView extends Viewable<AddMemoryPresenter> {

    void showDescriptionFragment(SubCategory subCategory);

    void memoryPostedSuccessfully();
}