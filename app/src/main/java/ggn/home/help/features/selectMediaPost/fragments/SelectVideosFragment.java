package ggn.home.help.features.selectMediaPost.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumActivity;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumPresenter;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumView;
import ggn.home.help.features.fullLifeAlbum.PhotoVideoAdapterBinder;
import ggn.home.help.features.fullLifeAlbum.adapters.VideosAdapter;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.selectMediaPost.SelectMediaPostActivity;
import ggn.home.help.features.selectMediaPost.SelectMediaPostPresenter;
import ggn.home.help.features.selectMediaPost.SelectMediaPostView;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.response.CategoryResponse;
import ggn.home.help.web.response.FullLifeAlbumResponse;


public class SelectVideosFragment extends BaseFragment<FragmentRecyclerViewBinding, SelectMediaPostPresenter> implements SelectMediaPostView, SwipeRefreshLayout.OnRefreshListener, PhotoVideoAdapterBinder {

    private List<FullLifeAlbumResponse.Datum> listPictures;
    private VideosAdapter videosAdapter;
    private String categoryId;
    private String subCategoryId;

    public static SelectVideosFragment newInstance(String categoryId, String subCategoryId) {
        SelectVideosFragment videosFragment = new SelectVideosFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Extras.CATEGORY_ID, categoryId);
        bundle.putString(Constants.Extras.SUB_CATEGORY_ID, subCategoryId);
        videosFragment.setArguments(bundle);
        return videosFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new SelectMediaPostPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        listPictures = new ArrayList<>();

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivityG(), 3);
        getDataBinder().recyclerView.setLayoutManager(gridLayoutManager);
        videosAdapter = new VideosAdapter(listPictures, getActivityG(), getPresenter());
        videosAdapter.setShouldLoadMore(false);
        videosAdapter.setPhotoVideoAdapterBinder(this);
        getDataBinder().recyclerView.setAdapter(videosAdapter);

        getDataBinder().swipeRefreshLayout.setOnRefreshListener(this);

        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getFullLifeAlbum(getArguments().getString(Constants.Extras.CATEGORY_ID), getArguments().getString(Constants.Extras.SUB_CATEGORY_ID), "video", 1);
    }


    public List<FullLifeAlbumResponse.Datum> getListVideos() {
        return listPictures;
    }

    public void resetData() {
        for (FullLifeAlbumResponse.Datum pictures : listPictures) {
//            pictures.isCheckVisible = false;
            pictures.isSelected = false;
        }
        videosAdapter.notifyDataSetChanged();
    }


    @Override
    public void showAlbum(FullLifeAlbumResponse output) {
        listPictures.clear();
        listPictures.addAll(output.data);
        videosAdapter.notifyDataSetChanged();
        getDataBinder().swipeRefreshLayout.setRefreshing(false);
        getDataBinder().textViewNoRecords.setVisibility(View.GONE);
    }

    @Override
    public void noDataFound() {
        getDataBinder().swipeRefreshLayout.setRefreshing(false);
        listPictures.clear();

        getDataBinder().textViewNoRecords.setText("No videos");
        getDataBinder().textViewNoRecords.setVisibility(View.VISIBLE);
    }

    public void setCategoryIds(String id, String id1) {
        this.categoryId = id;
        this.subCategoryId = id1;

        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getFullLifeAlbum(categoryId, subCategoryId, "video", 1);
    }

    @Override
    public void onRefresh() {
        listPictures.clear();
        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getFullLifeAlbum(categoryId, subCategoryId, "video", 1);
    }

    @Override
    public void onPictureVideoSelected(boolean isSelected) {
    }
}
