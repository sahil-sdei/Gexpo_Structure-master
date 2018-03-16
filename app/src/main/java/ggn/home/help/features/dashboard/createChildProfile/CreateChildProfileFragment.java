package ggn.home.help.features.dashboard.createChildProfile;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentCreateChildProfileBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.ProfilePresenter;
import ggn.home.help.features.profile.ProfileView;

public class CreateChildProfileFragment extends BaseFragment<FragmentCreateChildProfileBinding, ProfilePresenter> implements ProfileView {

    public static CreateChildProfileFragment newInstance() {
        CreateChildProfileFragment profileFragment = new CreateChildProfileFragment();
        return profileFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_create_child_profile;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new ProfilePresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {

    }
}
