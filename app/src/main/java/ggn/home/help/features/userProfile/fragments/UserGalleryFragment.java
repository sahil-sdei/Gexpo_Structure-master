package ggn.home.help.features.userProfile.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.userProfile.UserProfilePresenter;
import ggn.home.help.features.userProfile.UserProfileView;
import ggn.home.help.features.userProfile.adapters.UserGalleryAdapter;
import ggn.home.help.features.userProfile.adapters.UserProfileGalleryAdapter;
import ggn.home.help.features.userProfile.model.GridItem;
import ggn.home.help.features.userProfile.model.HeaderItem;
import ggn.home.help.utils.SpacesItemDecoration;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.Gallery;
import ggn.home.help.web.response.PostsResponse;
import ggn.home.help.web.response.ProfileResponse;
import ggn.home.help.web.response.RelationsResponse;


public class UserGalleryFragment extends BaseFragment<FragmentRecyclerViewBinding, UserProfilePresenter> implements UserProfileView {

    private UserGalleryAdapter userGalleryAdapter;
    private UserProfileGalleryAdapter mAdapter;
    private List<Gallery> mItemList = new ArrayList<>();

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
        Collections.sort(output.galleries, new Comparator<Gallery>() {
            public int compare(Gallery obj1, Gallery obj2) {
                return obj1.categoryName.compareToIgnoreCase(obj2.categoryName);
            }
        });

//        getDataBinder().recyclerView.setHasFixedSize(true);
//        getDataBinder().recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivityG(), 3);
//        getDataBinder().recyclerView.setLayoutManager(gridLayoutManager);
//        userGalleryAdapter = new UserGalleryAdapter(output.galleries, getActivityG());
//        userGalleryAdapter.setShouldLoadMore(false);
//        getDataBinder().recyclerView.setAdapter(userGalleryAdapter);

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivityG(), 3);
        getDataBinder().recyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new UserProfileGalleryAdapter(getActivityG(), mItemList, gridLayoutManager, 3);
        getDataBinder().recyclerView.addItemDecoration(new SpacesItemDecoration(1));
        getDataBinder().recyclerView.setAdapter(mAdapter);

        addMockList(output);
    }

    private void addMockList(ProfileResponse output) {
        for (int i = 0; i < output.galleries.size(); i++) {
            Gallery gallery1;
            if (i == 0) {
                gallery1 = output.galleries.get(i);
                mAdapter.addItem(new HeaderItem(gallery1.categoryName));
                mAdapter.addItem(new GridItem(gallery1.gallery, i));
            } else {
                gallery1 = output.galleries.get(i);
                Gallery gallery2 = output.galleries.get(i - 1);

                if (gallery1.categoryName.equalsIgnoreCase(gallery2.categoryName)) {
                    mAdapter.addItem(new GridItem(gallery1.gallery, i));
                } else {
                    mAdapter.addItem(new HeaderItem(gallery1.categoryName));
                    mAdapter.addItem(new GridItem(gallery1.gallery, i));
                }
            }
        }
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
