package ggn.home.help.features.memoryCategories;

import ggn.home.help.web.response.CategoryResponse;

public interface MemoryCategoriesAdapterBinder {
    void onItemClicked(CategoryResponse.Datum categories);
}