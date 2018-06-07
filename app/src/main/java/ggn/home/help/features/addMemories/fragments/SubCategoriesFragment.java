package ggn.home.help.features.addMemories.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usemedia.Matisse;
import com.example.usemedia.ui.MatisseFragment;
import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.addMemories.AddMemoryPresenter;
import ggn.home.help.features.addMemories.AddMemoryView;
import ggn.home.help.features.addMemories.SubCategoriesAdapter;
import ggn.home.help.features.addMemoryPreview.MemoryPreviewActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.pickMedia.AddMediaActivity;
import ggn.home.help.features.selectMediaPost.SelectMediaPostActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.web.response.CategoryResponse;
import ggn.home.help.web.response.SubCategory;

import static android.app.Activity.RESULT_OK;
import static ggn.home.help.features.addMemories.fragments.AddDescriptionFragment.MEDIA_ACTION_ARG;


public class SubCategoriesFragment extends BaseFragment<FragmentRecyclerViewBinding, AddMemoryPresenter> implements AddMemoryView {

    private CategoryResponse.Datum categoriesObj;
    private SubCategory subCategory;
    private String baseUrlImage;

    public static SubCategoriesFragment newInstance() {
        SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment();
        return subCategoriesFragment;
    }

    public static SubCategoriesFragment newInstance(Bundle bundle) {
        SubCategoriesFragment subCategoriesFragment = new SubCategoriesFragment();
        subCategoriesFragment.setArguments(bundle);
        return subCategoriesFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new AddMemoryPresenter());
        getPresenter().attachView(this);

        categoriesObj = (CategoryResponse.Datum) getArguments().getSerializable(Constants.Extras.DATA);
        baseUrlImage = getArguments().getString(Constants.Extras.BASE_URL_IMAGE);

    }

    @Override
    public void initViews() {
        getDataBinder().swipeRefreshLayout.setRefreshing(false);
        getDataBinder().swipeRefreshLayout.setEnabled(false);


        ((AddMemoryActivity) getActivity()).changeHeadingText(getString(R.string.sub_categories));

        List<SubCategory> listSubCategory = new ArrayList<>();
        listSubCategory.addAll(categoriesObj.subCategory);
        listSubCategory.add(new SubCategory("Suggest Sub Category"));

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        SubCategoriesAdapter subCategoriesAdapter = new SubCategoriesAdapter(listSubCategory, getActivityG(), getPresenter());
        subCategoriesAdapter.setBaseUrl(baseUrlImage);
        subCategoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(subCategoriesAdapter);
    }

    @Override
    public void showDescriptionFragment(SubCategory subCategory) {
        this.subCategory = subCategory;
        if (subCategory.name.equalsIgnoreCase("Suggest Sub Category")) {
            addSubCategory();
        } else {
            if (getArguments().getBoolean(Constants.Extras.IS_MEMORY)) {
                Intent intent = new Intent(getActivityG(), AddMediaActivity.class);
                intent.putExtra(Constants.Extras.IS_MEMORY, true);
                startActivityForResult(intent, Constants.RequestCode.IMAGE_SEARCH);
            } else {
//                ((AddMemoryActivity) getActivity()).changeHeadingText(subCategories.title);
//                ((AddMemoryActivity) getActivity()).showFragmentWithBackStack(AddDescriptionFragment.newInstance());
                Intent intent = new Intent(getActivityG(), SelectMediaPostActivity.class);
                intent.putExtra(Constants.Extras.CATEGORY_ID, categoriesObj.category.id);
                intent.putExtra(Constants.Extras.SUB_CATEGORY_ID, subCategory.id);
                startActivityForResult(intent, Constants.RequestCode.SELECT_IMAGES_VIDEOS);
            }
        }
    }

    @Override
    public void memoryPostedSuccessfully() {

    }

    private void addSubCategory() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivityG(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Do you want to suggest sub category name?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showAddSubCategoryDialog();
            }
        });
        builder.setNegativeButton("CANCEL", null);
        builder.show();
    }

    private void showAddSubCategoryDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivityG(), R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_sub_category, null);
        final EditText editText = dialogView.findViewById(R.id.editTextName);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Suggest Sub Category");
        dialogBuilder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = editText.getText().toString();
                if (!TextUtils.isEmpty(name)) {
                    getPresenter().addSubCategory(name, categoriesObj.category.id);
                    UtillsG.hideKeyboard(getActivityG(), dialogView);
                } else {
                    Toast.makeText(getActivityG(), "Please enter sub category title.", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialogBuilder.setNegativeButton("CANCEL", null);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.IMAGE_SEARCH) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra(MatisseFragment.EXTRA_IS_GALLERY, false)) {
                    List<Uri> uriList = Matisse.obtainResult(data);
                    List<String> pathList = Matisse.obtainPathResult(data);
                    Intent intent = new Intent(getActivityG(), MemoryPreviewActivity.class);
                    intent.putExtra(Constants.Extras.IS_MEMORY, getArguments().getBoolean(Constants.Extras.IS_MEMORY));
                    intent.putExtra("images_size", uriList.size());
                    intent.putExtra(Constants.Extras.DATA, categoriesObj);
                    intent.putExtra(Constants.Extras.SUB_CATEGORY, subCategory);
                    intent.putStringArrayListExtra("list_images", (ArrayList<String>) pathList);
                    startActivity(intent);
                } else {
                    if (data.getIntExtra(Constants.Extras.RESPONSE_CODE_ARG, 0) == PreviewActivity.ACTION_CONFIRM) {
                        if (data.getIntExtra(MEDIA_ACTION_ARG, 0) == Configuration.MEDIA_ACTION_VIDEO) {
                            List<String> list = new ArrayList<>();
                            list.add(data.getStringExtra(Constants.Extras.FILE_PATH_ARG));

                            Intent intent = new Intent(getActivityG(), MemoryPreviewActivity.class);
                            intent.putExtra(Constants.Extras.IS_MEMORY, getArguments().getBoolean(Constants.Extras.IS_MEMORY));
                            intent.putExtra("images_size", 1);
                            intent.putExtra(Constants.Extras.DATA, categoriesObj);
                            intent.putExtra(Constants.Extras.SUB_CATEGORY, subCategory);
                            intent.putStringArrayListExtra("list_images", (ArrayList<String>) list);
                            startActivity(intent);
                        } else {
                            List<String> list = new ArrayList<>();
                            list.add(data.getStringExtra(Constants.Extras.FILE_PATH_ARG));

                            Intent intent = new Intent(getActivityG(), MemoryPreviewActivity.class);
                            intent.putExtra(Constants.Extras.IS_MEMORY, getArguments().getBoolean(Constants.Extras.IS_MEMORY));
                            intent.putExtra("images_size", 1);
                            intent.putExtra(Constants.Extras.DATA, categoriesObj);
                            intent.putExtra(Constants.Extras.SUB_CATEGORY, subCategory);
                            intent.putStringArrayListExtra("list_images", (ArrayList<String>) list);
                            startActivity(intent);
                        }
                    }
                }
            }
        } else if (requestCode == Constants.RequestCode.SELECT_IMAGES_VIDEOS) {
            if (resultCode == RESULT_OK) {
                ((AddMemoryActivity) getActivity()).changeHeadingText(subCategory.name);
                //((AddMemoryActivity) getActivity()).showFragmentWithBackStack(AddDescriptionFragment.newInstance((List<Pictures>) data.getSerializableExtra(Constants.Extras.SELECTED_MEDIA)));
            }
        }
    }
}
