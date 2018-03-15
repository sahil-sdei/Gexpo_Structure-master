package ggn.home.help.features.profile;

import android.support.v7.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentProfileGalleryBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.utils.SpacesItemDecoration;
import ggn.home.help.utils.UtillsG;


public class ProfileGalleryFragment extends BaseFragment<FragmentProfileGalleryBinding, ProfilePresenter> implements ProfileView {

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
        getDataBinder().recyclerViewGallery.setLayoutManager(new GridLayoutManager(getActivityG(), 3));
        ProfileGalleryAdapter profileGalleryAdapter = new ProfileGalleryAdapter(list, getActivityG());
        getDataBinder().recyclerViewGallery.addItemDecoration(new SpacesItemDecoration(1));
        profileGalleryAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewGallery.setAdapter(profileGalleryAdapter);
    }
}
