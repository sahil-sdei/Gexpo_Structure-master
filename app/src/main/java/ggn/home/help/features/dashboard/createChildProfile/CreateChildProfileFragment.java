package ggn.home.help.features.dashboard.createChildProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import com.mvc.imagepicker.ImagePicker;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentCreateChildProfileBinding;
import ggn.home.help.features.editProfile.EditProfileActivity;
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

        ImagePicker.setMinQuality(600, 600);
    }

    @Override
    public void initViews() {
        getDataBinder().imageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickImage(CreateChildProfileFragment.this, "Select your image");
            }
        });
    }

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(getActivityG(), requestCode, resultCode, data);
        getDataBinder().imageViewProfilePic.setImageBitmap(bitmap);
    }
}
