package ggn.home.help.features.addMemories.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.usemedia.Matisse;
import com.example.usemedia.ui.MatisseFragment;
import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.configuration.Configuration;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.addMemories.AddMemoryPresenter;
import ggn.home.help.features.addMemories.AddMemoryView;
import ggn.home.help.features.addMemories.SubCategoriesAdapter;
import ggn.home.help.features.addMemoryPreview.MemoryPreviewActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.memoryCategories.Categories;
import ggn.home.help.features.memoryCategories.SubCategories;
import ggn.home.help.features.pickMedia.AddMediaActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.UtillsG;

import static android.app.Activity.RESULT_OK;
import static ggn.home.help.features.addMemories.fragments.AddDescriptionFragment.MEDIA_ACTION_ARG;


public class SubCategoriesFragment extends BaseFragment<FragmentRecyclerViewBinding, AddMemoryPresenter> implements AddMemoryView {

    private Categories categoriesObj;

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

        categoriesObj = (Categories) getArguments().getSerializable(Constants.Extras.DATA);
    }

    @Override
    public void initViews() {
        ((AddMemoryActivity) getActivity()).changeHeadingText(getString(R.string.sub_categories));

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        SubCategoriesAdapter subCategoriesAdapter = new SubCategoriesAdapter(categoriesObj.listSubcategories, getActivityG(), getPresenter());
        subCategoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(subCategoriesAdapter);
    }

    @Override
    public void showDescriptionFragment(SubCategories subCategories) {
        if (subCategories.title.equalsIgnoreCase("Miscellaneous")) {
            addSubCategory();
        } else {
            if (getArguments().getBoolean(Constants.Extras.IS_MEMORY)) {
                Intent intent = new Intent(getActivityG(), AddMediaActivity.class);
                intent.putExtra(Constants.Extras.IS_MEMORY, true);
                startActivityForResult(intent, Constants.RequestCode.IMAGE_SEARCH);
            } else {
                ((AddMemoryActivity) getActivity()).showFragmentWithBackStack(AddDescriptionFragment.newInstance());
            }
        }
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
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Suggest Sub Category");
        dialogBuilder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivityG(), getString(R.string.success_admin_sub_category), Toast.LENGTH_LONG).show();
                UtillsG.hideKeyboard(getActivityG(), dialogView);
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
//                    Toast.makeText(getActivityG(), "Got the data " + uriList.size() + " - - " + pathList.size(), Toast.LENGTH_SHORT).show();
//                    String path = pathList.get(0);
                    MemoryPreviewActivity.start(getActivityG());
                } else {
                    if (data.getIntExtra(Constants.Extras.RESPONSE_CODE_ARG, 0) == PreviewActivity.ACTION_CONFIRM) {
                        if (data.getIntExtra(MEDIA_ACTION_ARG, 0) == Configuration.MEDIA_ACTION_VIDEO) {
                            MemoryPreviewActivity.start(getActivityG());
                        } else {
                            MemoryPreviewActivity.start(getActivityG());
                        }
                    }
                }
            }
        }
    }
}
