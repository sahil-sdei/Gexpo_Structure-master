package ggn.home.help.features.userProfile;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewPager;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityUserProfileBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.profile.FriendsFragment;
import ggn.home.help.features.profile.ProfileAboutFragment;
import ggn.home.help.features.profile.ProfileGalleryFragment;
import ggn.home.help.features.userProfile.post.UserProfilePostsFragment;
import ggn.home.help.utils.PagerAdapter;

public class UserProfileActivity extends BaseActivity<ActivityUserProfileBinding, UserProfilePresenter> implements UserProfileView {

    private PagerAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, UserProfileActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_user_profile;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new UserProfilePresenter());
        getPresenter().attachView(this);

    }

    @Override
    public Context getActivityG() {
        return UserProfileActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        setupViewPager(getDataBinder().viewPager);
        getDataBinder().tabs.setupWithViewPager(getDataBinder().viewPager);
        getDataBinder().viewPager.setOffscreenPageLimit(4);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(UserProfilePostsFragment.newInstance(), getString(R.string.posts));
        adapter.addFrag(ProfileAboutFragment.newInstance(), getString(R.string.about));
        adapter.addFrag(ProfileGalleryFragment.newInstance(), getString(R.string.gallery));
        adapter.addFrag(FriendsFragment.newInstance(), getString(R.string.friends));
        viewPager.setAdapter(adapter);
    }
}
