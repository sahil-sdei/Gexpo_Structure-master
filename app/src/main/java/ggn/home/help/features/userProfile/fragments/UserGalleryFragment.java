package ggn.home.help.features.userProfile.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.fullLifeAlbum.PhotoVideoAdapterBinder;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.selectMediaPost.SelectMediaPostPresenter;
import ggn.home.help.features.selectMediaPost.SelectMediaPostView;
import ggn.home.help.features.selectMediaPost.adapters.SelectImagesAdapter;
import ggn.home.help.features.userProfile.UserProfilePresenter;
import ggn.home.help.features.userProfile.UserProfileView;
import ggn.home.help.features.userProfile.adapters.UserGalleryAdapter;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.FullLifeAlbumResponse;
import ggn.home.help.web.response.PostsResponse;
import ggn.home.help.web.response.ProfileResponse;
import ggn.home.help.web.response.RelationsResponse;


public class UserGalleryFragment extends BaseFragment<FragmentRecyclerViewBinding, UserProfilePresenter> implements UserProfileView{

    private UserGalleryAdapter userGalleryAdapter;

    public static UserGalleryFragment newInstance() {
        UserGalleryFragment imagesFragment = new UserGalleryFragment();
        return imagesFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new UserProfilePresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        getDataBinder().swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void showProfileData(ProfileResponse output) {
        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivityG(), 3);
        getDataBinder().recyclerView.setLayoutManager(gridLayoutManager);
        userGalleryAdapter = new UserGalleryAdapter(output.galleries, getActivityG());
        userGalleryAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(userGalleryAdapter);
    }

    @Override
    public void showRelations(RelationsResponse output) {

    }

    @Override
    public void requestSentSuccessfully(BasicResponse output) {

    }

    @Override
    public void unFriendSuccessfully(BasicResponse output) {

    }

    @Override
    public void onMemoryLiked(PostsResponse.Datum memory) {

    }
}
