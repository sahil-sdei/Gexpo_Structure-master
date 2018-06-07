package ggn.home.help.features.profile;

import android.text.TextUtils;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentProfileAboutBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.utils.DateHelper;
import ggn.home.help.web.response.ProfileResponse;


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

    @Override
    public void showProfileData(ProfileResponse output) {
        getDataBinder().editTextName.setText(output.about.name);
        if (!TextUtils.isEmpty(output.about.dob))
            getDataBinder().editTextDateOfBirth.setText(DateHelper.getInstance().getMyDateFormat(output.about.dob, DateHelper.ServerBasic, DateHelper.DOB));
        getDataBinder().editTextAddress.setText(output.about.address);
        getDataBinder().editTextLifeLesson.setText(output.about.lifeLession);
        getDataBinder().editTextGender.setText(output.about.gender);
    }
}
