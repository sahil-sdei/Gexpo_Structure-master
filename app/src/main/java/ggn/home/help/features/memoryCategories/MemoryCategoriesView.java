package ggn.home.help.features.memoryCategories;

import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.CategoryResponse;

public interface MemoryCategoriesView extends Viewable<MemoryCategoriesPresenter> {
    void goToAddMemories(CategoryResponse.Datum categories);

    void showCategories(CategoryResponse output);
}
