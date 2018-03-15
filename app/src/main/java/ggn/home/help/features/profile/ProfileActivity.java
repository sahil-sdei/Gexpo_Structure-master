package ggn.home.help.features.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityProfileBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.PagerAdapter;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding, ProfilePresenter> implements ProfileView {

    private PagerAdapter adapter;

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
        setupViewPager(getDataBinder().viewPager);
        getDataBinder().tabs.setupWithViewPager(getDataBinder().viewPager);
        getDataBinder().viewPager.setOffscreenPageLimit(2);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(ProfileAboutFragment.newInstance(), getString(R.string.about));
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
        textViewTab2.setText(R.string.gallery);
        getDataBinder().tabs.getTabAt(1).setCustomView(tabTwo);
    }
}
