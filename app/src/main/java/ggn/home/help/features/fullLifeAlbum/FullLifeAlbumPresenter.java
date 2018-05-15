package ggn.home.help.features.fullLifeAlbum;


import android.text.TextUtils;

import com.google.gson.Gson;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.MemoryAPI;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.request.FullLifeAlbumRequest;
import ggn.home.help.web.response.CategoryResponse;
import ggn.home.help.web.response.FullLifeAlbumResponse;

public class FullLifeAlbumPresenter extends BasePresenter<FullLifeAlbumView> implements PhotoVideoAdapterBinder {
    public void getCategories() {
        BasicRequest basicRequest = new BasicRequest();
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

    public void getFullLifeAlbum(String categoryId, String subCategoryId, String memoryType, int page) {
        FullLifeAlbumRequest fullLifeAlbumRequest = new FullLifeAlbumRequest();
        fullLifeAlbumRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        fullLifeAlbumRequest.token = getView().getLocalData().getAuthToken();
        if (!TextUtils.isEmpty(categoryId))
            fullLifeAlbumRequest.categoryId = Integer.parseInt(categoryId);
        if (!TextUtils.isEmpty(subCategoryId))
            fullLifeAlbumRequest.subCategoryId = subCategoryId;
        fullLifeAlbumRequest.memoryType = memoryType;
        fullLifeAlbumRequest.page = page;

        Gson gson = new Gson();

//        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getFullLifeAlbum(gson.toJson(fullLifeAlbumRequest)), new CallBackG<FullLifeAlbumResponse>() {
            @Override
            public void callBack(FullLifeAlbumResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showFullLifeAlbum(output);
                else
//                    getView().displayError(output.message);
                    getView().noDataFound();
            }
        });
    }
}
