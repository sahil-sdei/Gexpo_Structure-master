package ggn.home.help.features.addMemoryPreview;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityAddMemoryPreviewBinding;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.request.AddMemoryRequest;
import ggn.home.help.web.response.CategoryResponse;
import ggn.home.help.web.response.Child;
import ggn.home.help.web.response.SubCategory;

public class MemoryPreviewActivity extends BaseActivity<ActivityAddMemoryPreviewBinding, MemoryPreviewPresenter> implements MemoryPreviewView, View.OnClickListener {

    private CategoryResponse.Datum categoriesObj;
    private SubCategory subCategoryObj;

    public static void start(Context context) {
        Intent starter = new Intent(context, MemoryPreviewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_memory_preview;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new MemoryPreviewPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return MemoryPreviewActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Add New Memory");
        getDataBinder().buttonShare.setOnClickListener(this);

        int size = getIntent().getIntExtra("images_size", 0);
        List<String> listImages = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            listImages.add("pro");
        }

        if (size == 1) {
            getDataBinder().indicator.setVisibility(View.GONE);
        }

        SlidingMemoryPreviewAdapter slidingImageAdapter = new SlidingMemoryPreviewAdapter(getActivityG(), getIntent().getStringArrayListExtra("list_images"));
        getDataBinder().viewPagerMedia.setAdapter(slidingImageAdapter);
        getDataBinder().indicator.setViewPager(getDataBinder().viewPagerMedia);

        categoriesObj = (CategoryResponse.Datum) getIntent().getSerializableExtra(Constants.Extras.DATA);
        subCategoryObj = (SubCategory) getIntent().getSerializableExtra(Constants.Extras.SUB_CATEGORY);
        getDataBinder().textViewTitle.setText(categoriesObj.category.name + " > " + categoriesObj.category.subTitle);

        if (!TextUtils.isEmpty(getLocalData().getChildId())) {
            for (Child childObj : getLocalData().getChildAccounts().data.childs) {
                if (childObj.id.equalsIgnoreCase(getLocalData().getChildId())) {
                    getDataBinder().textViewUserName.setText(childObj.userProfile.name);
                    if (!TextUtils.isEmpty(childObj.profileImage))
                        ImageLoader.loadImageSmall(getDataBinder().imageViewProfilePic, "http://18.216.102.186/memoreeta/files/profileimage/" +
                                childObj.profileImage);
                    else
                        getDataBinder().imageViewProfilePic.setImageResource(R.drawable.ic_user_placeholder);
                    break;
                }
            }
            return;
        }

        getDataBinder().textViewUserName.setText(getLocalData().getUserName());
        if (!TextUtils.isEmpty(getLocalData().getProfileImage()))
            ImageLoader.loadImageSmall(getDataBinder().imageViewProfilePic, getLocalData().getProfileImage());
        else
            getDataBinder().imageViewProfilePic.setImageResource(R.drawable.ic_user_placeholder);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonShare:
                if (!TextUtils.isEmpty(getDataBinder().editTextTitle.getText().toString())) {
                    AddMemoryRequest addMemoryRequest = new AddMemoryRequest();
                    addMemoryRequest.userId = Integer.parseInt(getLocalData().getUserId());
                    addMemoryRequest.token = getLocalData().getAuthToken();
                    addMemoryRequest.categoryId = categoriesObj.category.id;
                    addMemoryRequest.subCategoryId = subCategoryObj.id;
                    addMemoryRequest.title = getDataBinder().editTextTitle.getText().toString();
                    if (!TextUtils.isEmpty(getLocalData().getChildId()))
                        addMemoryRequest.childId = getLocalData().getChildId();

                    getPresenter().addMemory(addMemoryRequest, getIntent().getStringArrayListExtra("list_images"));
                } else {
                    displayError("Please enter description for your memory.");
                }
                break;
        }
    }

    @Override
    public void memoryPostedSuccessfully() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UtillsG.finishAll(getActivityG());
                DashboardActivity.start(getActivityG());
            }
        }, 1000);
    }
}
