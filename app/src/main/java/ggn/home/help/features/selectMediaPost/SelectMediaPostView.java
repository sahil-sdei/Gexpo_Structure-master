package ggn.home.help.features.selectMediaPost;


import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.web.response.FullLifeAlbumResponse;

public interface SelectMediaPostView extends Viewable<SelectMediaPostPresenter> {

    void showAlbum(FullLifeAlbumResponse output);

    void noDataFound();
}