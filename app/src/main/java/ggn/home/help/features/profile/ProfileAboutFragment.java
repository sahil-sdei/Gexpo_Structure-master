package ggn.home.help.features.profile;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentProfileAboutBinding;
import ggn.home.help.features.internal.base.BaseFragment;


public class ProfileAboutFragment extends BaseFragment<FragmentProfileAboutBinding, ProfilePresenter> implements ProfileView {

    public static ProfileAboutFragment newInstance() {
        ProfileAboutFragment profileAboutFragment = new ProfileAboutFragment();
        return profileAboutFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile_about;
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
