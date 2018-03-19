package ggn.home.help.features.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityProfileBinding;
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
        setupTabIcons();
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        menuItemEdit = menu.findItem(R.id.action_edit);
//        menuItemShare = menu.findItem(R.id.action_share);
//        menuItemDelete = menu.findItem(R.id.action_delete);
//
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        menuItemEdit = menu.findItem(R.id.action_edit);
        menuItemShare = menu.findItem(R.id.action_share);
        menuItemDelete = menu.findItem(R.id.action_delete);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(ProfileAboutFragment.newInstance(), getString(R.string.about));
        adapter.addFrag(FamilyFragment.newInstance(), getString(R.string.family));
        adapter.addFrag(FriendsFragment.newInstance(), getString(R.string.friends));
        adapter.addFrag(ProfileGalleryFragment.newInstance(), getString(R.string.gallery));
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        LinearLayout tabOne = (LinearLayout) LayoutInflater.from(getActivityG()).inflate(R.layout.custom_tab, null);
        TextView textViewTab1 = tabOne.findViewById(R.id.textViewTab);
        textViewTab1.setText(R.string.about);
        getDataBinder().tabs.getTabAt(0).setCustomView(tabOne);

        LinearLayout tabTwo = (LinearLayout) LayoutInflater.from(getActivityG()).inflate(R.layout.custom_tab, null);
        TextView textViewTab2 = tabTwo.findViewById(R.id.textViewTab);
        textViewTab2.setText(R.string.family);
        getDataBinder().tabs.getTabAt(1).setCustomView(tabTwo);

        LinearLayout tabTThree = (LinearLayout) LayoutInflater.from(getActivityG()).inflate(R.layout.custom_tab, null);
        TextView textViewTab3 = tabTThree.findViewById(R.id.textViewTab);
        textViewTab3.setText(R.string.friends);
        getDataBinder().tabs.getTabAt(2).setCustomView(tabTThree);

        LinearLayout tabTFour = (LinearLayout) LayoutInflater.from(getActivityG()).inflate(R.layout.custom_tab, null);
        TextView textViewTab4 = tabTFour.findViewById(R.id.textViewTab);
        textViewTab4.setText(R.string.gallery);
        getDataBinder().tabs.getTabAt(3).setCustomView(tabTFour);
    }

    public void updateActionsMenu(boolean isSelected) {
        menuItemEdit.setVisible(!isSelected);
        menuItemShare.setVisible(isSelected);
        menuItemDelete.setVisible(isSelected);
//        invalidateOptionsMenu();
    }
}
