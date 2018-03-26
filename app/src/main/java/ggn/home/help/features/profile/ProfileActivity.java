package ggn.home.help.features.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityProfileBinding;
import ggn.home.help.features.editProfile.EditProfileActivity;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.PagerAdapter;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding, ProfilePresenter> implements ProfileView {

    private PagerAdapter adapter;
    private MenuItem menuItemEdit;
    private MenuItem menuItemShare;
    private MenuItem menuItemDelete;

    public static void start(Context context) {
        Intent starter = new Intent(context, ProfileActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new ProfilePresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return ProfileActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar(getString(R.string.profile));

        setupViewPager(getDataBinder().viewPager);
        getDataBinder().tabs.setupWithViewPager(getDataBinder().viewPager);
        getDataBinder().viewPager.setOffscreenPageLimit(4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        menuItemEdit = menu.findItem(R.id.action_edit);
        menuItemShare = menu.findItem(R.id.action_share);
        menuItemDelete = menu.findItem(R.id.action_delete);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                EditProfileActivity.start(getActivityG());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(ProfileAboutFragment.newInstance(), getString(R.string.about));
        adapter.addFrag(FamilyFragment.newInstance(), getString(R.string.family));
        adapter.addFrag(FriendsFragment.newInstance(), getString(R.string.friends));
        adapter.addFrag(ProfileGalleryFragment.newInstance(), getString(R.string.gallery));
        viewPager.setAdapter(adapter);
    }

    public void updateActionsMenu(boolean isSelected) {
        menuItemEdit.setVisible(!isSelected);
        menuItemShare.setVisible(isSelected);
        menuItemDelete.setVisible(isSelected);
    }
}
