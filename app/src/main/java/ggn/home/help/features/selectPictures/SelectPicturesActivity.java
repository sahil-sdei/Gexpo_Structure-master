package ggn.home.help.features.selectPictures;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivitySelectPicturesBinding;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumActivity;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.previewPictures.PreviewPicturesActivity;
import ggn.home.help.utils.CategorySelectedListener;
import ggn.home.help.utils.SubCategorySelectedListener;
import ggn.home.help.utils.widgets.CategoryAdapter;
import ggn.home.help.utils.widgets.SubCategoryAdapter;
import ggn.home.help.web.response.Category;
import ggn.home.help.web.response.CategoryResponse;
import ggn.home.help.web.response.SubCategory;

public class SelectPicturesActivity extends BaseActivity<ActivitySelectPicturesBinding, SelectPicturesPresenter> implements SelectPicturesView, CategorySelectedListener, SubCategorySelectedListener {

    private SelectPicturesAdapter selectPicturesAdapter;
    private List<Pictures> listPictures;
    private CategoryResponse categoryResponseObj;
    private AlertDialog dialogCategory;
    private AlertDialog dialogSubCategory;
    private Category categoryObj;
    private SubCategory subCategoryObj;
    private List<SubCategory> listSubCategory;

    public static void start(Context context) {
        Intent starter = new Intent(context, SelectPicturesActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_select_pictures;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new SelectPicturesPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return SelectPicturesActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Select Your Memories");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        getDataBinder().textViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryObj != null) {
                    for (CategoryResponse.Datum data : categoryResponseObj.data) {
                        if (data.category.name.equalsIgnoreCase(categoryObj.name)) {
                            data.category.isSelected = true;
                        } else
                            data.category.isSelected = false;
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivityG(), R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Choose Category");
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.dialog_list_category_sub_category, null);
                CategoryAdapter categoryAdapter = new CategoryAdapter(getActivityG(), categoryResponseObj.data);
                categoryAdapter.setCategorySelectedListener(SelectPicturesActivity.this);
                ListView listView = dialogLayout.findViewById(R.id.listViewCategorySubCategory);
                listView.setAdapter(categoryAdapter);
                builder.setView(dialogLayout);

                dialogCategory = builder.create();
                dialogCategory.show();
            }
        });

        getDataBinder().textViewSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listSubCategory != null) {
                    if (subCategoryObj != null)
                        for (SubCategory subCategory : listSubCategory) {
                            if (subCategory.id == subCategoryObj.id)
                                subCategory.isSelected = true;
                            else
                                subCategory.isSelected = false;
                        }

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivityG(), R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("Choose Sub Category");
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.dialog_list_category_sub_category, null);
                    SubCategoryAdapter categoryAdapter = new SubCategoryAdapter(getActivityG(), listSubCategory);
                    categoryAdapter.setSubCategorySelectedListener(SelectPicturesActivity.this);
                    ListView listView = dialogLayout.findViewById(R.id.listViewCategorySubCategory);
                    listView.setAdapter(categoryAdapter);
                    builder.setView(dialogLayout);

                    dialogSubCategory = builder.create();
                    dialogSubCategory.show();
                }
            }
        });

        getPresenter().getCategories();


        listPictures = new ArrayList<>();
        listPictures.add(new Pictures("pro", false));
        listPictures.add(new Pictures("pro", false));
        listPictures.add(new Pictures("pro", false));
        listPictures.add(new Pictures("pro", false));
        listPictures.add(new Pictures("pro", false));
        listPictures.add(new Pictures("pro", false));
        listPictures.add(new Pictures("pro", false));

        getDataBinder().recyclerViewPictures.setHasFixedSize(true);
        getDataBinder().recyclerViewPictures.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivityG(), 3);
        getDataBinder().recyclerViewPictures.setLayoutManager(gridLayoutManager);
        selectPicturesAdapter = new SelectPicturesAdapter(listPictures, getActivityG(), getPresenter());
        selectPicturesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewPictures.setAdapter(selectPicturesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_next_arrow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                boolean isFound = false;
                for (Pictures pictures : listPictures) {
                    if (pictures.isSelected) {
                        isFound = true;
                        break;
                    }
                }

                if(isFound){
                    List<Pictures> picturesList = new ArrayList<>();
                    for (Pictures pictures : listPictures) {
                        if (pictures.isSelected) {
                            picturesList.add(pictures);
                        }
                    }

                    PreviewPicturesActivity.start(getActivityG(), picturesList);
                }else{
                    Toast.makeText(getActivityG(), "Please select atleat 1 picture.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCategories(CategoryResponse output) {
        categoryResponseObj = output;
    }

    @Override
    public void onCategorySelected(CategoryResponse.Datum category) {
        categoryObj = category.category;
        if (dialogCategory.isShowing()) {
            dialogCategory.dismiss();
        }
        getDataBinder().textViewCategory.setText(category.category.name);
        getDataBinder().textViewSubCategory.setText("Select Sub Category");
        listSubCategory = category.subCategory;
    }

    @Override
    public void onSubCategorySelected(SubCategory category) {
        subCategoryObj = category;
        if (dialogSubCategory.isShowing()) {
            dialogSubCategory.dismiss();
        }
        getDataBinder().textViewSubCategory.setText(category.name);
    }
}
