package ggn.home.help.features.memoryCategories;

import ggn.home.help.features.internal.base.contract.Viewable;

public interface MemoryCategoriesView extends Viewable<MemoryCategoriesPresenter> {
    void goToAddMemories(Categories categories);
}
