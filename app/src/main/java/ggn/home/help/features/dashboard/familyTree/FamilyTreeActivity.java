package ggn.home.help.features.dashboard.familyTree;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityFamilyTreeBinding;
import ggn.home.help.features.internal.base.BaseActivity;

public class FamilyTreeActivity extends BaseActivity<ActivityFamilyTreeBinding, FamilyTreePresenter> implements FamilyTreeView {

    public static void start(Context context) {
        Intent starter = new Intent(context, FamilyTreeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_family_tree;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new FamilyTreePresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return FamilyTreeActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    }
}
