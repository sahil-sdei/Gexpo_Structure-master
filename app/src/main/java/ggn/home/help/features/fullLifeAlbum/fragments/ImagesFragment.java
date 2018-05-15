package ggn.home.help.features.fullLifeAlbum.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumPresenter;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumView;
import ggn.home.help.features.fullLifeAlbum.adapters.ImagesAdapter;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.response.CategoryResponse;
import ggn.home.help.web.response.FullLifeAlbumResponse;


public class ImagesFragment extends BaseFragment<FragmentRecyclerViewBinding, FullLifeAlbumPresenter> implements FullLifeAlbumView,  SwipeRefreshLayout.OnRefreshListener {

    private List<FullLifeAlbumResponse.Datum> listPictures;
    private ImagesAdapter imagesAdapter;
    private String categoryId;
    private String subCategoryId;

    public static ImagesFragment newInstance(String categoryId, String subCategoryId) {
        ImagesFragment imagesFragment = new ImagesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Extras.CATEGORY_ID, categoryId);
        bundle.putString(Constants.Extras.SUB_CATEGORY_ID, subCategoryId);
        imagesFragment.setArguments(bundle);
        return imagesFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new FullLifeAlbumPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        listPictures = new ArrayList<>();

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivityG(), 3);
        getDataBinder().recyclerView.setLayoutManager(gridLayoutManager);
        imagesAdapter = new ImagesAdapter(listPictures, getActivityG(), getPresenter());
        imagesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(imagesAdapter);

        getDataBinder().swipeRefreshLayout.setOnRefreshListener(this);

        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getFullLifeAlbum(getArguments().getString(Constants.Extras.CATEGORY_ID), getArguments().getString(Constants.Extras.SUB_CATEGORY_ID), "image", 1);
    }

    public void enableSharePost() {
//        for (FullLifeAlbumResponse.Datum pictures : listPictures) {
//            pictures.isCheckVisible = true;
//        }
//        imagesAdapter.notifyDataSetChanged();
    }

    public List<FullLifeAlbumResponse.Datum> getListPictures() {
        return listPictures;
    }

    public void resetData() {
        for (FullLifeAlbumResponse.Datum pictures : listPictures) {
            pictures.isSelected = false;
        }
        imagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategories(CategoryResponse output) {

    }

    @Override
    public void showFullLifeAlbum(FullLifeAlbumResponse output) {
        listPictures.clear();
        listPictures.addAll(output.data);
        imagesAdapter.notifyDataSetChanged();
        getDataBinder().swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void noDataFound() {
        getDataBinder().swipeRefreshLayout.setRefreshing(false);
        listPictures.clear();
        imagesAdapter.notifyDataSetChanged();
    }

    public void setCategoryIds(String id, String id1) {
        this.categoryId = id;
        this.subCategoryId = id1;

        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getFullLifeAlbum(categoryId, subCategoryId, "image", 1);
    }

    @Override
    public void onRefresh() {
        listPictures.clear();
        getDataBinder().swipeRefreshLayout.setRefreshing(true);
        getPresenter().getFullLifeAlbum(categoryId, subCategoryId, "image", 1);
    }
}
