package ggn.home.help.utils;

import ggn.home.help.web.response.CategoryResponse;

/**
 * Created by maverick on 28/04/18.
 */

public interface CategorySelectedListener {
    void onCategorySelected(CategoryResponse.Datum category);
}
