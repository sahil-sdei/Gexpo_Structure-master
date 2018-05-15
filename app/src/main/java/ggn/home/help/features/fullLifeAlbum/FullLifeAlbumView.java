package ggn.home.help.features.fullLifeAlbum;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.web.response.CategoryResponse;
import ggn.home.help.web.response.FullLifeAlbumResponse;

public interface FullLifeAlbumView extends Viewable<FullLifeAlbumPresenter> {

    void showCategories(CategoryResponse output);

    void showFullLifeAlbum(FullLifeAlbumResponse output);

    void noDataFound();
}