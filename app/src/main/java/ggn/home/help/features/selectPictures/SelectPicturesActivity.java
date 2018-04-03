package ggn.home.help.features.selectPictures;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivitySelectPicturesBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.previewPictures.PreviewPicturesActivity;

public class SelectPicturesActivity extends BaseActivity<ActivitySelectPicturesBinding, SelectPicturesPresenter> implements SelectPicturesView {

    private SelectPicturesAdapter selectPicturesAdapter;
    private List<Pictures> listPictures;

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
        setupToolbar("Select Pictures");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        getDataBinder().textViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivityG(), R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Choose Category");
                final String[] animals = {"Pre Birth", "Birth", "Infancy", "Early Childhood", "Middle Childhood", "Late Childhood", "Adolescence"};
                int checkedItem = 1; // cow
                builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        String checkedItem = (String) lw.getAdapter().getItem(lw.getCheckedItemPosition());

                        getDataBinder().textViewCategory.setText(checkedItem);
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
                int checkedItem = 1; // cow
                builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView lw = ((AlertDialog) dialog).getListView();
                        String checkedItem = (String) lw.getAdapter().getItem(lw.getCheckedItemPosition());

                        getDataBinder().textViewSubCategory.setText(checkedItem);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


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
        getMenuInflater().inflate(R.menu.menu_next, menu);
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
}
