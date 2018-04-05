package ggn.home.help.features.fullLifeAlbum.adapters.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumPresenter;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumView;
import ggn.home.help.features.fullLifeAlbum.adapters.adapters.ImagesAdapter;
import ggn.home.help.features.internal.base.BaseFragment;


public class ImagesFragment extends BaseFragment<FragmentRecyclerViewBinding, FullLifeAlbumPresenter> implements FullLifeAlbumView {

    private List<String> list;
    private ImagesAdapter imagesAdapter;

    public static ImagesFragment newInstance() {
        ImagesFragment activityFragment = new ImagesFragment();
        return activityFragment;
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
        list = new ArrayList<>();
        list.add("pro");
        list.add("pro");
        list.add("pro");
        list.add("pro");

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivityG(), 3);
        getDataBinder().recyclerView.setLayoutManager(gridLayoutManager);
        imagesAdapter = new ImagesAdapter(list, getActivityG(), getPresenter());
        imagesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(imagesAdapter);
    }
}
