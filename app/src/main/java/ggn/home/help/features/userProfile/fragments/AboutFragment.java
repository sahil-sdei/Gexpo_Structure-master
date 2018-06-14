package ggn.home.help.features.userProfile.fragments;

import android.text.TextUtils;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentProfileAboutBinding;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.profile.ProfilePresenter;
import ggn.home.help.features.profile.ProfileView;
import ggn.home.help.features.userProfile.UserProfileActivity;
import ggn.home.help.features.userProfile.UserProfilePresenter;
import ggn.home.help.features.userProfile.UserProfileView;
import ggn.home.help.utils.DateHelper;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.PostsResponse;
import ggn.home.help.web.response.ProfileResponse;
import ggn.home.help.web.response.RelationsResponse;


public class AboutFragment extends BaseFragment<FragmentProfileAboutBinding, UserProfilePresenter> implements UserProfileView{

    public static AboutFragment newInstance() {
        AboutFragment profileAboutFragment = new AboutFragment();
        return profileAboutFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile_about;
    }

    @Override
    protected void onCreateFragmentG() {
        injectPresenter(new UserProfilePresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
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
