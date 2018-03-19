package ggn.home.help.features.profile;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentProfileGalleryBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.interfaces.GalleryItemSelected;
import ggn.home.help.features.profile.model.GridItem;
import ggn.home.help.features.profile.model.HeaderItem;
import ggn.home.help.features.profile.model.Item;
import ggn.home.help.utils.SpacesItemDecoration;


public class ProfileGalleryFragment extends BaseFragment<FragmentProfileGalleryBinding, ProfilePresenter> implements ProfileView, GalleryItemSelected {

    private int mHeaderCounter = 0;
    private int mGridCounter;
    private List<Item> mItemList = new ArrayList<>();
    private ProfileGalleryAdapter mAdapter;

    public static ProfileGalleryFragment newInstance() {
        ProfileGalleryFragment profileAboutFragment = new ProfileGalleryFragment();
        return profileAboutFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile_gallery;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new ProfilePresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        list.add("i");

        getDataBinder().recyclerViewGallery.setHasFixedSize(true);
        getDataBinder().recyclerViewGallery.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivityG(), 3);
        getDataBinder().recyclerViewGallery.setLayoutManager(gridLayoutManager);

        mAdapter = new ProfileGalleryAdapter(getActivityG(), mItemList, gridLayoutManager, 3);
        mAdapter.setGalleryItemSelectedListener(this);
        getDataBinder().recyclerViewGallery.addItemDecoration(new SpacesItemDecoration(1));
        getDataBinder().recyclerViewGallery.setAdapter(mAdapter);

        addMockList();
    }

    private void addMockList() {
        mAdapter.addItem(new HeaderItem("Header " + getHeaderCounter()));

        mAdapter.addItem(new GridItem("Grid " + getGridCounter(), mGridCounter));
        mAdapter.addItem(new GridItem("Grid " + getGridCounter(), mGridCounter));
        mAdapter.addItem(new GridItem("Grid " + getGridCounter(), mGridCounter));
        mAdapter.addItem(new GridItem("Grid " + getGridCounter(), mGridCounter));
        mAdapter.addItem(new GridItem("Grid " + getGridCounter(), mGridCounter));
        mAdapter.addItem(new GridItem("Grid " + getGridCounter(), mGridCounter));
        mAdapter.addItem(new GridItem("Grid " + getGridCounter(), mGridCounter));

        int headerPosition = new Random().nextInt(19) + 1;

        for (int i = 0; i < 100; i++) {
            if (i % headerPosition == 0) {
                mAdapter.addItem(new HeaderItem("Header " + getHeaderCounter()));
                headerPosition = new Random().nextInt(19) + 1;
            }

            mAdapter.addItem(new GridItem("Grid " + getGridCounter(), mGridCounter));
        }
    }

    public int getHeaderCounter() {
        mGridCounter = 0;
        return ++mHeaderCounter;
    }

    public int getGridCounter() {
        return ++mGridCounter;
    }

    @Override
    public void onGalleryItemSelected() {
        boolean isSelected = false;
        for (Item iObj : mItemList) {
            if (iObj.isSelected()) {
                isSelected = true;
                break;
            }
        }
        ((ProfileActivity) getActivity()).updateActionsMenu(isSelected);
    }
}
