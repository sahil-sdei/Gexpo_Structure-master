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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityFullLifeAlbumBinding;
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
    private MenuItem menuItemNext;
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

//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivityG(), R.style.AppCompatAlertDialogStyle);
//                builder.setTitle("Choose Category");
//                LayoutInflater inflater = getLayoutInflater();
//                View dialogLayout = inflater.inflate(R.layout.dialog_list_category_sub_category, null);
//                CategoryAdapter categoryAdapter = new CategoryAdapter(getActivityG(), categoryResponseObj.data);
//                categoryAdapter.setCategorySelectedListener(FullLifeAlbumActivity.this);
//                ListView listView = dialogLayout.findViewById(R.id.listViewCategorySubCategory);
//                listView.setAdapter(categoryAdapter);
//                builder.setView(dialogLayout);
//
//                dialogCategory = builder.create();
//                dialogCategory.show();

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

//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivityG(), R.style.AppCompatAlertDialogStyle);
//                    builder.setTitle("Choose Sub Category");
//                    LayoutInflater inflater = getLayoutInflater();
//                    View dialogLayout = inflater.inflate(R.layout.dialog_list_category_sub_category, null);
//                    SubCategoryAdapter categoryAdapter = new SubCategoryAdapter(getActivityG(), listSubCategory);
//                    categoryAdapter.setSubCategorySelectedListener(FullLifeAlbumActivity.this);
//                    ListView listView = dialogLayout.findViewById(R.id.listViewCategorySubCategory);
//                    listView.setAdapter(categoryAdapter);
//                    builder.setView(dialogLayout);
//
//                    dialogSubCategory = builder.create();
//                    dialogSubCategory.show();

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
//                if (checkedItemCategory.equalsIgnoreCase("Choose Category") &&
//                        checkedItemSubCategory.equalsIgnoreCase("Choose Sub Category")) {
//                    Toast.makeText(getActivityG(), "Please choose category & sub category to post your new memory.", Toast.LENGTH_LONG).show();
//                    break;
//                }
//
//                if (checkedItemCategory.equalsIgnoreCase("Choose Category")) {
//                    Toast.makeText(getActivityG(), "Please choose category to post your new memory.", Toast.LENGTH_LONG).show();
//                    break;
//                }
//
//                if (checkedItemSubCategory.equalsIgnoreCase("Choose Sub Category")) {
//                    Toast.makeText(getActivityG(), "Please choose sub category to post your new memory.", Toast.LENGTH_LONG).show();
//                    break;
//                }
//
//                if (!isPostEnabled) {
//                    isPostEnabled = true;
//                    ((ImagesFragment) adapter.getItem(0)).enableSharePost();
//                    ((VideosFragment) adapter.getItem(1)).enableSharePost();
//                    menuItemNext.setVisible(true);
//                } else {
//                    menuItemNext.setVisible(false);
//                    ((ImagesFragment) adapter.getItem(0)).resetData();
//                    ((VideosFragment) adapter.getItem(1)).resetData();
//                    isPostEnabled = false;
//                }
                break;

            case R.id.action_next:

//                if (getDataBinder().textViewCategory.getText().toString().equalsIgnoreCase("Select Category") &&
//                        getDataBinder().textViewSubCategory.getText().toString().equalsIgnoreCase("Select Sub Category")) {
//                    Toast.makeText(getActivityG(), "Please choose category & sub category to post your new memory.", Toast.LENGTH_LONG).show();
//                    break;
//                }
//
//                if (getDataBinder().textViewCategory.getText().toString().equalsIgnoreCase("Select Category")) {
//                    Toast.makeText(getActivityG(), "Please choose category to post your new memory.", Toast.LENGTH_LONG).show();
//                    break;
//                }
//
//                if (getDataBinder().textViewSubCategory.getText().toString().equalsIgnoreCase("Select Sub Category")) {
//                    Toast.makeText(getActivityG(), "Please choose sub category to post your new memory.", Toast.LENGTH_LONG).show();
//                    break;
//                }
//
//                List<FullLifeAlbumResponse.Datum> listPictures = ((ImagesFragment) adapter.getItem(0)).getListPictures();
//                List<FullLifeAlbumResponse.Datum> listVideos = ((VideosFragment) adapter.getItem(1)).getListVideos();
//
//                boolean isFoundImages = false;
//                for (FullLifeAlbumResponse.Datum pictures : listPictures) {
//                    if (pictures.isSelected) {
//                        isFoundImages = true;
//                        break;
//                    }
//                }
//
//                boolean isFoundVideos = false;
//                for (FullLifeAlbumResponse.Datum pictures : listVideos) {
//                    if (pictures.isSelected) {
//                        isFoundVideos = true;
//                        break;
//                    }
//                }
//
//                if (isFoundImages || isFoundVideos) {
//                    List<FullLifeAlbumResponse.Datum> listSelectedMedia = new ArrayList<>();
//                    for (FullLifeAlbumResponse.Datum pictures : listPictures) {
//                        if (pictures.isSelected) {
//                            listSelectedMedia.add(pictures);
//                        }
//                    }
//
//                    for (FullLifeAlbumResponse.Datum pictures : listVideos) {
//                        if (pictures.isSelected) {
//                            listSelectedMedia.add(pictures);
//                        }
//                    }
//
//                    CategoryResponse.Datum datum = new CategoryResponse().new Datum(categoryObj, listSubCategory);
//
////                    Intent intent = new Intent(getActivityG(), AddMemoryActivity.class);
////                    intent.putExtra(Constants.Extras.IS_MEMORY, false);
////                    intent.putExtra(Constants.Extras.DATA, datum);
////                    intent.putExtra(Constants.Extras.BASE_URL_IMAGE, categoryResponseObj.imagePath);
////                    intent.putExtra(Constants.Extras.SUB_CATEGORY, subCategoryObj);
////                    intent.putExtra(Constants.Extras.SELECTED_MEDIA, (Serializable) listSelectedMedia);
////                    intent.putExtra(Constants.Extras.POST_FROM_ALBUM, true);
////                    startActivityForResult(intent, Constants.RequestCode.POST_VIA_MEMORY);
//                } else {
//                    Toast.makeText(getActivityG(), "Please select atleast one picture or video to post your memory.", Toast.LENGTH_LONG).show();
//                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCategories(CategoryResponse output) {
        categoryResponseObj = output;
        if (categoryResponseObj.data.size() > 0) {
//            categoryResponseObj.data.get(0).category.isSelected = true;
//            categoryResponseObj.data.get(0).subCategory.get(0).isSelected = true;
//
//            getDataBinder().textViewCategory.setText(categoryResponseObj.data.get(0).category.name);
//            getDataBinder().textViewSubCategory.setText(categoryResponseObj.data.get(0).subCategory.get(0).name);
//
//            categoryObj = categoryResponseObj.data.get(0).category;
//            subCategoryObj = categoryResponseObj.data.get(0).subCategory.get(0);

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
//        if (dialogCategory.isShowing()) {
//            dialogCategory.dismiss();
//        }
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
//        if (dialogSubCategory.isShowing()) {
//            dialogSubCategory.dismiss();
//        }
        getDataBinder().textViewSubCategory.setText(category.name);

        ((ImagesFragment) adapter.getItem(0)).setCategoryIds(categoryObj.id, subCategoryObj.id);
        ((VideosFragment) adapter.getItem(1)).setCategoryIds(categoryObj.id, subCategoryObj.id);
    }
}
