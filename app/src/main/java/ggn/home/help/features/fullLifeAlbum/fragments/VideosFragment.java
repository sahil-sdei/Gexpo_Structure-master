package ggn.home.help.features.fullLifeAlbum.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.dashboard.myMemories.MemoriesPresenter;
import ggn.home.help.features.dashboard.myMemories.MemoriesView;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumPresenter;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumView;
import ggn.home.help.features.fullLifeAlbum.adapters.ImagesAdapter;
import ggn.home.help.features.fullLifeAlbum.adapters.VideosAdapter;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.FamilyAdapter;
import ggn.home.help.features.selectPictures.Pictures;


public class VideosFragment extends BaseFragment<FragmentRecyclerViewBinding, FullLifeAlbumPresenter> implements FullLifeAlbumView {

    private List<Pictures> listPictures;
    private VideosAdapter videosAdapter;

    public static VideosFragment newInstance() {
        VideosFragment activityFragment = new VideosFragment();
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
        videosAdapter = new VideosAdapter(listPictures, getActivityG(), getPresenter());
        videosAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(videosAdapter);
    }

    public void enableSharePost() {
        for(Pictures pictures : listPictures){
            pictures.isCheckVisible = true;
        }
        videosAdapter.notifyDataSetChanged();
    }

    public List<Pictures> getListVideos(){
        return listPictures;
    }

    @Override
    public void onResume() {
        super.onResume();
//        resetData();
    }

    public void resetData() {
        for(Pictures pictures : listPictures){
            pictures.isCheckVisible = false;
            pictures.isSelected = false;
        }
        videosAdapter.notifyDataSetChanged();
    }
}
