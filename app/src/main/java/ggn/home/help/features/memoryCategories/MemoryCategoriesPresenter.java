package ggn.home.help.features.memoryCategories;


import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumActivity;
import ggn.home.help.features.internal.base.BasePresenter;

public class MemoryCategoriesPresenter extends BasePresenter<MemoryCategoriesView> implements MemoryCategoriesAdapterBinder {

    @Override
    public void onItemClicked(Categories categories) {
        if (categories.title.equalsIgnoreCase("Full Life Album"))
            FullLifeAlbumActivity.start(getView().getActivityG());
        else
            getView().goToAddMemories(categories);
    }
}
