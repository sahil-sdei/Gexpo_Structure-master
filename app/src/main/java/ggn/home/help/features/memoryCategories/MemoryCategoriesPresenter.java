package ggn.home.help.features.memoryCategories;


import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumActivity;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.MemoryAPI;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.response.CategoryResponse;

public class MemoryCategoriesPresenter extends BasePresenter<MemoryCategoriesView> implements MemoryCategoriesAdapterBinder {

    private String imageBaseUrl;

    public void getCategories() {
        BasicRequest basicRequest= new BasicRequest();
        basicRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        basicRequest.token = getView().getLocalData().getAuthToken();

        Gson gson = new Gson();

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(MemoryAPI.class)
                .getCategories(gson.toJson(basicRequest)), new CallBackG<CategoryResponse>() {
            @Override
            public void callBack(CategoryResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showCategories(output);
                else
                    getView().displayError(output.message);
            }
        });
    }

    @Override
    public void onItemClicked(CategoryResponse.Datum categories) {
        if (categories.category.name.equalsIgnoreCase("Full Life Album"))
            FullLifeAlbumActivity.start(getView().getActivityG());
        else
            getView().goToAddMemories(categories);
    }

    public void setImageBaseUrl(String imageBaseUrl) {
        this.imageBaseUrl = imageBaseUrl;
    }
}
