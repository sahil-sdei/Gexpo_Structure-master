package ggn.home.help.features.addMemories;


import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.BasePresenter;
import ggn.home.help.features.services.UploadPostService;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.apiInterfaces.MemoryAPI;
import ggn.home.help.web.apiInterfaces.PostsAPI;
import ggn.home.help.web.request.AddPostRequest;
import ggn.home.help.web.request.AddSuggestionRequest;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.SubCategory;

public class AddMemoryPresenter extends BasePresenter<AddMemoryView> implements AddMemoryAdapterBinder {

    @Override
    public void onSubcategoryItemClicked(SubCategory subCategory) {
        getView().showDescriptionFragment(subCategory);
    }

    public void addSubCategory(String name, String categoryId) {
        AddSuggestionRequest addSuggestionRequest = new AddSuggestionRequest();
        addSuggestionRequest.userId = Integer.parseInt(getView().getLocalData().getUserId());
        addSuggestionRequest.token = getView().getLocalData().getAuthToken();
        addSuggestionRequest.categoryId = Integer.parseInt(categoryId);
        addSuggestionRequest.name = name;
        Gson gson = new Gson();

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(MemoryAPI.class)
                .addSuggestion(gson.toJson(addSuggestionRequest)), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                getView().hideLoading();
                getView().displayError(output.message);
            }
        });
    }

    public void addPost(AddPostRequest addPostRequest) {
        Gson gson = new Gson();

        if (!TextUtils.isEmpty(getView().getLocalData().getChildId()))
            addPostRequest.childId = getView().getLocalData().getChildId();

        getView().showLoading(getView().getActivityG().getString(R.string.loading), getView().getActivityG().getString(R.string.please_wait));
        createApiRequest(getRetrofitInstance(PostsAPI.class)
                .addPost(gson.toJson(addPostRequest)), new CallBackG<BasicResponse>() {
            @Override
            public void callBack(BasicResponse output) {
                getView().hideLoading();
                getView().displayError(output.message);
                if (output.status == 1)
                    getView().memoryPostedSuccessfully();
            }
        });
    }

    public void addPost(AddPostRequest addPostRequest, ArrayList<String> listImages) {
        Intent intent = new Intent(getView().getActivityG(), UploadPostService.class);
        intent.putExtra(Constants.Extras.DATA, addPostRequest);
        intent.putExtra(Constants.Extras.GALLERY_DATA, listImages);
        getView().getActivityG().startService(intent);
        getView().postAddedSuccessfully();
    }
}
