package ggn.home.help.features.fullLifeAlbum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityFullLifeAlbumBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.fullLifeAlbum.fragments.ImagesFragment;
import ggn.home.help.features.fullLifeAlbum.fragments.VideosFragment;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.memoryCategories.Categories;
import ggn.home.help.features.memoryCategories.MemoryCategoriesFragment;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.features.selectMediaPost.fragments.SelectImagesFragment;
import ggn.home.help.features.selectMediaPost.fragments.SelectVideosFragment;
import ggn.home.help.features.selectPictures.Pictures;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.PagerAdapter;

public class FullLifeAlbumActivity extends BaseActivity<ActivityFullLifeAlbumBinding, FullLifeAlbumPresenter> implements FullLifeAlbumView {

    private PagerAdapter adapter;
    private boolean isPostEnabled;
    private MenuItem menuItemNext;
    private String checkedItemCategory = "Choose Category";
    private String checkedItemSubCategory = "Choose Sub Category";
    private int selectedCategory;
    private int selectedSubCategory;

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
                final String[] animals = {"All", "Pre Birth", "Birth", "Infancy (0-3 y)", "Early Childhood (3-6 y)", "Middle Childhood (6-8 y)", "Late Childhood (9-11 y)", "Adolescence (12-22 y)"};
                builder.setSingleChoiceItems(animals, selectedCategory, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        checkedItemCategory = (String) lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        selectedCategory = lw.getCheckedItemPosition();

                        getDataBinder().textViewCategory.setText(checkedItemCategory);
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
                final String[] animals = {"Pregnancy Moments", "Baby Inside Me"};
                builder.setSingleChoiceItems(animals, selectedSubCategory, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        checkedItemSubCategory = (String) lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        selectedSubCategory = lw.getCheckedItemPosition();
                        getDataBinder().textViewSubCategory.setText(checkedItemSubCategory);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_white, menu);
        menuItemNext = menu.findItem(R.id.action_next);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RequestCode.POST_VIA_MEMORY) {
            if (resultCode == RESULT_OK) {
                menuItemNext.setVisible(false);
                ((ImagesFragment) adapter.getItem(0)).resetData();
                ((VideosFragment) adapter.getItem(1)).resetData();
                isPostEnabled = false;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                DashboardActivity.start(getActivityG(), true);
                break;

            case R.id.action_post:
                if (checkedItemCategory.equalsIgnoreCase("Choose Category") &&
                        checkedItemSubCategory.equalsIgnoreCase("Choose Sub Category")) {
                    Toast.makeText(getActivityG(), "Please choose category & sub category to post your new memory.", Toast.LENGTH_LONG).show();
                    break;
                }

                if (checkedItemCategory.equalsIgnoreCase("Choose Category")) {
                    Toast.makeText(getActivityG(), "Please choose category to post your new memory.", Toast.LENGTH_LONG).show();
                    break;
                }

                if (checkedItemSubCategory.equalsIgnoreCase("Choose Sub Category")) {
                    Toast.makeText(getActivityG(), "Please choose sub category to post your new memory.", Toast.LENGTH_LONG).show();
                    break;
                }

                if (!isPostEnabled) {
                    isPostEnabled = true;
                    ((ImagesFragment) adapter.getItem(0)).enableSharePost();
                    ((VideosFragment) adapter.getItem(1)).enableSharePost();
                    menuItemNext.setVisible(true);
                } else {
                    menuItemNext.setVisible(false);
                    ((ImagesFragment) adapter.getItem(0)).resetData();
                    ((VideosFragment) adapter.getItem(1)).resetData();
                    isPostEnabled = false;
                }
                break;

            case R.id.action_next:
                List<Pictures> listPictures = ((ImagesFragment) adapter.getItem(0)).getListPictures();
                List<Pictures> listVideos = ((VideosFragment) adapter.getItem(1)).getListVideos();

                boolean isFoundImages = false;
                for (Pictures pictures : listPictures) {
                    if (pictures.isSelected) {
                        isFoundImages = true;
                        break;
                    }
                }

                boolean isFoundVideos = false;
                for (Pictures pictures : listVideos) {
                    if (pictures.isSelected) {
                        isFoundVideos = true;
                        break;
                    }
                }

                if (isFoundImages || isFoundVideos) {
                    List<Pictures> listSelectedMedia = new ArrayList<>();
                    for (Pictures pictures : listPictures) {
                        if (pictures.isSelected) {
                            listSelectedMedia.add(pictures);
                        }
                    }

                    for (Pictures pictures : listVideos) {
                        if (pictures.isSelected) {
                            listSelectedMedia.add(pictures);
                        }
                    }

//                    Intent resultIntent = new Intent();
//                    resultIntent.putExtra(Constants.Extras.SELECTED_MEDIA, (Serializable) listSelectedMedia);
//                    setResult(RESULT_OK, resultIntent);
//                    finish();

                    List<SubCategories> preBirth = new ArrayList<>();
                    preBirth.add(new SubCategories("Pregnancy Moments", "ic_delivery_moments", "demo_add_mem"));
                    preBirth.add(new SubCategories("Baby Inside Me", "ic_miscellaneous", "demo_add_mem"));
                    preBirth.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "demo_add_mem"));
                    preBirth.add(new SubCategories("Suggest Sub Category", "ic_miscellaneous", "demo_add_mem"));
                    Categories categories = new Categories(checkedItemCategory, checkedItemSubCategory, "layer_2", null, preBirth);

                    Intent intent = new Intent(getActivityG(), AddMemoryActivity.class);
                    intent.putExtra(Constants.Extras.IS_MEMORY, false);
                    intent.putExtra(Constants.Extras.DATA, categories);
                    intent.putExtra(Constants.Extras.SELECTED_MEDIA, (Serializable) listSelectedMedia);
                    intent.putExtra(Constants.Extras.POST_FROM_ALBUM, true);
                    startActivityForResult(intent, Constants.RequestCode.POST_VIA_MEMORY);
                } else {
                    Toast.makeText(getActivityG(), "Please select atleast one picture or video to post your memory.", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
