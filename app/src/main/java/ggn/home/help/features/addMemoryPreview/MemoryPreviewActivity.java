package ggn.home.help.features.addMemoryPreview;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityAddMemoryPreviewBinding;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.memoryCategories.Categories;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.web.response.CategoryResponse;

public class MemoryPreviewActivity extends BaseActivity<ActivityAddMemoryPreviewBinding, MemoryPreviewPresenter> implements MemoryPreviewView, View.OnClickListener {

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

        CategoryResponse.Datum categoriesObj = (CategoryResponse.Datum) getIntent().getSerializableExtra(Constants.Extras.DATA);
        getDataBinder().textViewTitle.setText(categoriesObj.category.name + " > " + categoriesObj.category.subTitle);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonShare:
//                Toast.makeText(getActivityG(), "Your new memory has been added successfully.", Toast.LENGTH_LONG).show();
                UtillsG.finishAll(getActivityG());

//                if(getIntent().getBooleanExtra(Constants.Extras.IS_MEMORY, false)){
                DashboardActivity.start(getActivityG());
//                }else
//                DashboardActivity.start(getActivityG(), 1);
                break;
        }
    }
}
