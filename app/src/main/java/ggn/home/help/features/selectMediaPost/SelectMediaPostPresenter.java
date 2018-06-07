package ggn.home.help.features.selectMediaPost;


import android.text.TextUtils;

import com.google.gson.Gson;

import ggn.home.help.features.fullLifeAlbum.PhotoVideoAdapterBinder;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.web.apiInterfaces.UserAPI;
import ggn.home.help.web.request.FullLifeAlbumRequest;
import ggn.home.help.web.response.FullLifeAlbumResponse;

public class SelectMediaPostPresenter extends BasePresenter<SelectMediaPostView> implements PhotoVideoAdapterBinder {

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

        createApiRequest(getRetrofitInstance(UserAPI.class)
                .getFullLifeAlbum(gson.toJson(fullLifeAlbumRequest)), new CallBackG<FullLifeAlbumResponse>() {
            @Override
            public void callBack(FullLifeAlbumResponse output) {
                getView().hideLoading();
                if (output.status == 1)
                    getView().showAlbum(output);
                else
                    getView().noDataFound();
            }
        });
    }

    @Override
    public void onPictureVideoSelected(boolean isSelected) {

    }
}
