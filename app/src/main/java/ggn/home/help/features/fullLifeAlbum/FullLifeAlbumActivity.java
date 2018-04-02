package ggn.home.help.features.fullLifeAlbum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityAddMemoryBinding;
import ggn.home.help.databinding.ActivityFullLifeAlbumBinding;
import ggn.home.help.features.addMemories.fragments.AddDescriptionFragment;
import ggn.home.help.features.addMemories.fragments.SubCategoriesFragment;
import ggn.home.help.features.fullLifeAlbum.fragments.ImagesFragment;
import ggn.home.help.features.fullLifeAlbum.fragments.VideosFragment;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.memoryCategories.Categories;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.features.profile.FamilyFragment;
import ggn.home.help.features.profile.FriendsFragment;
import ggn.home.help.features.profile.ProfileAboutFragment;
import ggn.home.help.features.profile.ProfileGalleryFragment;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.PagerAdapter;
import ggn.home.help.utils.bitmapUtils.ImageLoader;

public class FullLifeAlbumActivity extends BaseActivity<ActivityFullLifeAlbumBinding, FullLifeAlbumPresenter> implements FullLifeAlbumView {

    private PagerAdapter adapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, FullLifeAlbumActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_full_life_album;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new FullLifeAlbumPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return FullLifeAlbumActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar(getString(R.string.full_life_album));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        setupViewPager(getDataBinder().viewPager);
        getDataBinder().tabs.setupWithViewPager(getDataBinder().viewPager);
        getDataBinder().viewPager.setOffscreenPageLimit(2);

        getDataBinder().textViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivityG(), R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Choose Category");
                String[] animals = {"Pre Birth", "Birth", "Infancy", "Early Childhood", "Middle Childhood", "Late Childhood", "Adolescence"};
                int checkedItem = 1; // cow
                builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        getDataBinder().textViewSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivityG(), R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Choose Sub Category");
                String[] animals = {"Pregnancy Moments", "Baby Inside Me"};
                int checkedItem = 1; // cow
                builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(ImagesFragment.newInstance(), getString(R.string.photos));
        adapter.addFrag(VideosFragment.newInstance(), getString(R.string.videos));
        viewPager.setAdapter(adapter);
    }
}
