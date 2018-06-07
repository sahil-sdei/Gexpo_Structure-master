package ggn.home.help.features.fullLifeAlbum;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityFullLifeAlbumBinding;
import ggn.home.help.features.addPostPreview.PostPreviewActivity;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.fullLifeAlbum.fragments.ImagesFragment;
import ggn.home.help.features.fullLifeAlbum.fragments.VideosFragment;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.CategorySelectedListener;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.PagerAdapter;
import ggn.home.help.utils.SubCategorySelectedListener;
import ggn.home.help.utils.widgets.CategoryAdapter;
import ggn.home.help.utils.widgets.SubCategoryAdapter;
import ggn.home.help.web.response.Category;
import ggn.home.help.web.response.CategoryResponse;
import ggn.home.help.web.response.FullLifeAlbumResponse;
import ggn.home.help.web.response.SubCategory;

public class FullLifeAlbumActivity extends BaseActivity<ActivityFullLifeAlbumBinding, FullLifeAlbumPresenter> implements FullLifeAlbumView, CategorySelectedListener, SubCategorySelectedListener {

    private PagerAdapter adapter;
    private boolean isPostEnabled;
    private MenuItem menuItemPost;
    private String checkedItemCategory = "Choose Category";
    private String checkedItemSubCategory = "Choose Sub Category";
    private CategoryResponse categoryResponseObj;
    private AlertDialog dialogCategory;
    private AlertDialog dialogSubCategory;
    private Category categoryObj;
    private SubCategory subCategoryObj;
    private List<SubCategory> listSubCategory;
    private Dialog dialogCat;
    private Dialog dialogSubCat;
    private List<FullLifeAlbumResponse.Datum> listMedia;

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

        getDataBinder().textViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryObj != null) {
                    for (CategoryResponse.Datum data : categoryResponseObj.data) {
                        if (data.category.name.equalsIgnoreCase(categoryObj.name)) {
                            data.category.isSelected = true;
                        } else
                            data.category.isSelected = false;
                    }
                }

                dialogCat = new Dialog(getActivityG());
                dialogCat.setTitle("Select Category");
                View viewDialog = getLayoutInflater().inflate(R.layout.dialog_list_view, null);
                CategoryAdapter categoryAdapter = new CategoryAdapter(getActivityG(), categoryResponseObj.data);
                categoryAdapter.setCategorySelectedListener(FullLifeAlbumActivity.this);
                ListView listView = viewDialog.findViewById(R.id.listView);
                listView.setAdapter(categoryAdapter);

                dialogCat.setContentView(viewDialog);

                dialogCat.show();
            }
        });

        getDataBinder().textViewSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listSubCategory != null) {
                    if (subCategoryObj != null)
                        for (SubCategory subCategory : listSubCategory) {
                            if (subCategory.id == subCategoryObj.id)
                                subCategory.isSelected = true;
                            else
                                subCategory.isSelected = false;
                        }

                    dialogSubCat = new Dialog(getActivityG());
                    dialogSubCat.setTitle("Select Sub Category");

                    View viewDialog = getLayoutInflater().inflate(R.layout.dialog_list_view, null);
                    SubCategoryAdapter categoryAdapter = new SubCategoryAdapter(getActivityG(), listSubCategory);
                    categoryAdapter.setSubCategorySelectedListener(FullLifeAlbumActivity.this);
                    ListView listView = viewDialog.findViewById(R.id.listView);
                    listView.setAdapter(categoryAdapter);

                    dialogSubCat.setContentView(viewDialog);

                    dialogSubCat.show();
                }
            }
        });

        getPresenter().getCategories();
    }

    private void setupViewPager(ViewPager viewPager, String categoryId, String subCategoryId) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(ImagesFragment.newInstance(categoryId, subCategoryId), getString(R.string.photos));
        adapter.addFrag(VideosFragment.newInstance(categoryId, subCategoryId), getString(R.string.videos));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_white, menu);
        menuItemPost = menu.findItem(R.id.action_post);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RequestCode.POST_VIA_MEMORY) {
            if (resultCode == RESULT_OK) {
                menuItemPost.setVisible(false);
                ((ImagesFragment) adapter.getItem(0)).resetData();
                ((VideosFragment) adapter.getItem(1)).resetData();
                isPostEnabled = false;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setPostEnabled(boolean isEnable) {
        List<FullLifeAlbumResponse.Datum> listPictures = ((ImagesFragment) adapter.getItem(0)).getListPictures();
        List<FullLifeAlbumResponse.Datum> listVideos = ((VideosFragment) adapter.getItem(1)).getListVideos();

        listMedia = new ArrayList<>();
        for (FullLifeAlbumResponse.Datum img : listPictures) {
            if (img.isSelected) {
                listMedia.add(img);
            }
        }
        for (FullLifeAlbumResponse.Datum vid : listVideos) {
            if (vid.isSelected) {
                listMedia.add(vid);
            }
        }

        boolean isSelected = false;
        for (FullLifeAlbumResponse.Datum data : listMedia) {
            if (data.isSelected) {
                isSelected = true;
                break;
            }
        }
        menuItemPost.setVisible(isSelected);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                DashboardActivity.start(getActivityG(), true);
                break;

            case R.id.action_post:
                Intent intent = new Intent(getActivityG(), PostPreviewActivity.class);
                intent.putExtra(Constants.Extras.DATA, (Serializable) listMedia);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCategories(CategoryResponse output) {
        categoryResponseObj = output;
        if (categoryResponseObj.data.size() > 0) {
            setupViewPager(getDataBinder().viewPager, "", "");
            getDataBinder().tabs.setupWithViewPager(getDataBinder().viewPager);
            getDataBinder().viewPager.setOffscreenPageLimit(2);
        }
    }

    @Override
    public void showFullLifeAlbum(FullLifeAlbumResponse output) {

    }

    @Override
    public void noDataFound() {

    }

    @Override
    public void onCategorySelected(CategoryResponse.Datum category) {
        if (dialogCat != null) {
            dialogCat.dismiss();
            dialogCat = null;
        }
        categoryObj = category.category;

        if (!TextUtils.isEmpty(category.category.years))
            getDataBinder().textViewCategory.setText(category.category.name + " (" + category.category.years + " y)");
        else
            getDataBinder().textViewCategory.setText(category.category.name);

        getDataBinder().textViewSubCategory.setText("Select Sub Category");
        listSubCategory = category.subCategory;

        if (categoryObj != null && subCategoryObj != null) {
            ((ImagesFragment) adapter.getItem(0)).setCategoryIds(categoryObj.id, subCategoryObj.id);
            ((VideosFragment) adapter.getItem(1)).setCategoryIds(categoryObj.id, subCategoryObj.id);
        }

    }

    @Override
    public void onSubCategorySelected(SubCategory category) {
        if (dialogSubCat != null) {
            dialogSubCat.dismiss();
            dialogSubCat = null;
        }
        subCategoryObj = category;
        getDataBinder().textViewSubCategory.setText(category.name);

        ((ImagesFragment) adapter.getItem(0)).setCategoryIds(categoryObj.id, subCategoryObj.id);
        ((VideosFragment) adapter.getItem(1)).setCategoryIds(categoryObj.id, subCategoryObj.id);
    }
}
