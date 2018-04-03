package ggn.home.help.features.previewPictures;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityPreviewPicturesBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.profile.interfaces.GalleryItemSelected;
import ggn.home.help.features.selectPictures.Pictures;
import ggn.home.help.utils.Constants;

public class PreviewPicturesActivity extends BaseActivity<ActivityPreviewPicturesBinding, PreviewPicturesPresenter> implements PreviewPicturesView, GalleryItemSelected {

    private PreviewPicturesAdapter previewPicturesAdapter;
    private MenuItem menuItemDelete;
    private List<Pictures> listPictures;

    public static void start(Context context, List<Pictures> picturesList) {
        Intent starter = new Intent(context, PreviewPicturesActivity.class);
        starter.putExtra(Constants.Extras.DATA, (Serializable) picturesList);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_preview_pictures;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new PreviewPicturesPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return PreviewPicturesActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Preview Pictures");

        listPictures = (List<Pictures>) getIntent().getSerializableExtra(Constants.Extras.DATA);
        for (Pictures pictures : listPictures) {
            pictures.isSelected = false;
        }

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivityG(), 3);
        getDataBinder().recyclerView.setLayoutManager(gridLayoutManager);
        previewPicturesAdapter = new PreviewPicturesAdapter(listPictures, getActivityG(), getPresenter());
        previewPicturesAdapter.setShouldLoadMore(false);
        previewPicturesAdapter.setGalleryItemSelectedListener(this);
        getDataBinder().recyclerView.setAdapter(previewPicturesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done_delete, menu);
        menuItemDelete = menu.findItem(R.id.action_delete);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                break;
            case R.id.action_delete:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGalleryItemSelected() {
        boolean isSelected = false;
        for (Pictures iObj : listPictures) {
            if (iObj.isSelected) {
                isSelected = true;
                break;
            }
        }
        updateActionsMenu(isSelected);
    }

    public void updateActionsMenu(boolean isSelected) {
        menuItemDelete.setVisible(isSelected);
    }
}
