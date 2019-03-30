package ggn.home.help.features.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityProfileBinding;
import ggn.home.help.features.editProfile.EditProfileActivity;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.share.ShareActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.PagerAdapter;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.request.ProfileRequest;
import ggn.home.help.web.response.ProfileResponse;

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

        getDataBinder().viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    menuItemEdit.setVisible(true);
                } else {
                    menuItemEdit.setVisible(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ProfileRequest profileRequest = new ProfileRequest();
        profileRequest.token = getLocalData().getAuthToken();
        profileRequest.userId = Integer.parseInt(getIntent().getStringExtra(Constants.Extras.USER_ID));
        profileRequest.page = 1;
        profileRequest.tab = 2;
        getPresenter().getProfile(profileRequest);
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
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(getActivityG(), EditProfileActivity.class);
                startActivityForResult(intent, Constants.RequestCode.EDIT_PROFILE);
                break;
            case R.id.action_share:
                PopupMenu popup = new PopupMenu(getActivityG(), findViewById(R.id.action_share));
                popup.getMenuInflater().inflate(R.menu.menu_share, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.share_in_the_memoreeta))) {
                            ShareActivity.start(getActivityG());
                        } else {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Here is the share content body";
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        }
                        return true;
                    }
                });
                popup.show();
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

    @Override
    public void showProfileData(ProfileResponse output) {
        ((ProfileAboutFragment) adapter.getItem(0)).showProfileData(output);
        if (!TextUtils.isEmpty(output.about.profileImage))
            ImageLoader.loadImageSmall(getDataBinder().imageViewProfilePic, output.about.profileImage);
        else
            getDataBinder().imageViewProfilePic.setImageResource(R.drawable.ic_user_placeholder);
        getLocalData().setProfileImage(output.about.profileImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RequestCode.EDIT_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {
                ProfileRequest profileRequest = new ProfileRequest();
                profileRequest.token = getLocalData().getAuthToken();
                profileRequest.userId = Integer.parseInt(getLocalData().getUserId());
                profileRequest.page = 1;
                profileRequest.tab = 2;
                getPresenter().getProfile(profileRequest);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
