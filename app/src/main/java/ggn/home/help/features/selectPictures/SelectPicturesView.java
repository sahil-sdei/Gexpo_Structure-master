package ggn.home.help.features.selectPictures;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.CategoryResponse;

public interface SelectPicturesView extends Viewable<SelectPicturesPresenter> {

    void showCategories(CategoryResponse output);
}