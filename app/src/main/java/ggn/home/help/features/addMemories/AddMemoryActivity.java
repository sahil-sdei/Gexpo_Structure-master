package ggn.home.help.features.addMemories;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityAddMemoryBinding;
import ggn.home.help.features.addMemories.fragments.AddDescriptionFragment;
import ggn.home.help.features.addMemories.fragments.SubCategoriesFragment;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.memoryCategories.MemoryCategoriesFragment;

public class AddMemoryActivity extends BaseActivity<ActivityAddMemoryBinding, AddMemoryPresenter> implements AddMemoryView {

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
        setupToolbar(getString(R.string.add_memories));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        showFragment(SubCategoriesFragment.newInstance());
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public void showFragmentWithBackStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showDescriptionFragment() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
