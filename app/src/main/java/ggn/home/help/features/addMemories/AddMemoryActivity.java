package ggn.home.help.features.addMemories;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityAddMemoryBinding;
import ggn.home.help.features.addMemories.fragments.AddDescriptionFragment;
import ggn.home.help.features.addMemories.fragments.SubCategoriesFragment;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.memoryCategories.Categories;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.features.selectPictures.Pictures;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.bitmapUtils.ImageLoader;

public class AddMemoryActivity extends BaseActivity<ActivityAddMemoryBinding, AddMemoryPresenter> implements AddMemoryView {

    private Categories categories;

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

        categories = (Categories) getIntent().getSerializableExtra(Constants.Extras.DATA);


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
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        binding.setData(categories);
        binding.executePendingBindings();

        if(!TextUtils.isEmpty(categories.years))
        binding.textViewTitle.setText(categories.title+" ("+categories.years+" years)");
        else
            binding.textViewTitle.setText(categories.title);

        ImageLoader.loadFullWidthImageBG(binding.relativeLayoutPicture, categories.listSubcategories.get(0).background);

        if(getIntent().hasExtra(Constants.Extras.POST_FROM_ALBUM)){
            changeHeadingText("Pregnancy Moments");
            showFragment(AddDescriptionFragment.newInstance((List<Pictures>)getIntent().getSerializableExtra(Constants.Extras.SELECTED_MEDIA)));
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.Extras.DATA, categories);
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
    public void showDescriptionFragment(SubCategories subCategories) {
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
}
