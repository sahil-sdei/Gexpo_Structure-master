package ggn.home.help.features.selectMediaPost.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumPresenter;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumView;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.selectMediaPost.PicturesSelectedListener;
import ggn.home.help.features.selectMediaPost.adapters.SelectVideosAdapter;
import ggn.home.help.features.selectPictures.Pictures;


public class SelectVideosFragment extends BaseFragment<FragmentRecyclerViewBinding, FullLifeAlbumPresenter> implements FullLifeAlbumView, PicturesSelectedListener {

    private List<Pictures> listPictures;
    private SelectVideosAdapter selectVideosAdapter;

    public static SelectVideosFragment newInstance() {
        SelectVideosFragment activityFragment = new SelectVideosFragment();
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
        listPictures = new ArrayList<>();
        listPictures.add(new Pictures("pro", false));
        listPictures.add(new Pictures("pro", false));
        listPictures.add(new Pictures("pro", false));
        listPictures.add(new Pictures("pro", false));

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivityG(), 3);
        getDataBinder().recyclerView.setLayoutManager(gridLayoutManager);
        selectVideosAdapter = new SelectVideosAdapter(listPictures, getActivityG(), getPresenter());
        selectVideosAdapter.setPicturesSelectedListener(this);
        selectVideosAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(selectVideosAdapter);
    }

    @Override
    public void onPicturesSelected() {

    }

    public List<Pictures> getVideos(){
        return listPictures;
    }
}
