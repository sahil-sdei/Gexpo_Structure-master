package ggn.home.help.features.addMemories;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityAddMemoryBinding;
import ggn.home.help.features.addMemories.fragments.AddDescriptionFragment;
import ggn.home.help.features.addMemories.fragments.SubCategoriesFragment;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.CategoryResponse;
import ggn.home.help.web.response.FullLifeAlbumResponse;
import ggn.home.help.web.response.SubCategory;

public class AddMemoryActivity extends BaseActivity<ActivityAddMemoryBinding, AddMemoryPresenter> implements AddMemoryView {

    private CategoryResponse.Datum categories;

    public static void start(Context context) {
        Intent starter = new Intent(context, AddMemoryActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_memory;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new AddMemoryPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return AddMemoryActivity.this;
    }

    @Override
    public void initViews() {
        if (getIntent().getBooleanExtra(Constants.Extras.IS_MEMORY, false))
            setupToolbar(getString(R.string.add_new_memory));
        else
            setupToolbar(getString(R.string.post_memory));

        if (getIntent().hasExtra(Constants.Extras.POST_FROM_ALBUM)) {
            if (getIntent().getBooleanExtra(Constants.Extras.POST_FROM_ALBUM, false)) {
                toolbar.setBackgroundResource(R.color.white);
                toolbarText.setTextColor(ContextCompat.getColor(getActivityG(), R.color.black));
                getDataBinder().relativeLayoutPicture.setVisibility(View.GONE);
                getDataBinder().textViewSubCategories.setVisibility(View.INVISIBLE);
                showFragment(AddDescriptionFragment.newInstance((List<FullLifeAlbumResponse.Datum>) getIntent().getSerializableExtra(Constants.Extras.DATA)));
            } else {
                showFragment(AddDescriptionFragment.newInstance((List<FullLifeAlbumResponse.Datum>) getIntent().getSerializableExtra(Constants.Extras.DATA)));
                toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                categories = (CategoryResponse.Datum) getIntent().getSerializableExtra(Constants.Extras.CATEGORY_DATA);
                binding.setData(categories);
                binding.executePendingBindings();
                if (!TextUtils.isEmpty(categories.category.years))
                    binding.textViewTitle.setText(categories.category.name + " (" + categories.category.years + " years)");
                else
                    binding.textViewTitle.setText(categories.category.name);
                ImageLoader.loadFullWidthImage(binding.imageViewBg, getIntent().getStringExtra(Constants.Extras.BASE_URL_IMAGE) + categories.category.image);
            }
            return;
        }

        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        categories = (CategoryResponse.Datum) getIntent().getSerializableExtra(Constants.Extras.DATA);
        binding.setData(categories);
        binding.executePendingBindings();

        if (!TextUtils.isEmpty(categories.category.years))
            binding.textViewTitle.setText(categories.category.name + " (" + categories.category.years + " years)");
        else
            binding.textViewTitle.setText(categories.category.name);

        ImageLoader.loadFullWidthImage(binding.imageViewBg, getIntent().getStringExtra(Constants.Extras.BASE_URL_IMAGE) + categories.category.image);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.Extras.DATA, categories);
        bundle.putString(Constants.Extras.BASE_URL_IMAGE, getIntent().getStringExtra(Constants.Extras.BASE_URL_IMAGE));
        bundle.putBoolean(Constants.Extras.IS_MEMORY, getIntent().getBooleanExtra(Constants.Extras.IS_MEMORY, false));
        showFragment(SubCategoriesFragment.newInstance(bundle));
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void showFragmentWithBackStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, fragment.getClass().getName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
//        if(getIntent().hasExtra(Constants.Extras.POST_FROM_ALBUM)){
//            Intent resultIntent = getIntent();
//            setResult(RESULT_OK, resultIntent);
//            finish();
//            return;
//        }

        if (!isFragmentPresent(AddDescriptionFragment.class.getName())) {
            Intent resultIntent = getIntent();
            setResult(RESULT_CANCELED, resultIntent);
            finish();
        }
    }

    public boolean isFragmentPresent(String tag) {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(tag);
        if (frag instanceof AddDescriptionFragment) {
            getSupportFragmentManager().popBackStack();
            return true;
        } else
            return false;
    }

    public void changeHeadingText(String title) {
        getDataBinder().textViewSubCategories.setText(title);
    }

    @Override
    public void showDescriptionFragment(SubCategory subCategory) {

    }

    @Override
    public void memoryPostedSuccessfully() {

    }
}
