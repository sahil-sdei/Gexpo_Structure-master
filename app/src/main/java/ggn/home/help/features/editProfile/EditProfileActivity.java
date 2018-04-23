package ggn.home.help.features.editProfile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mvc.imagepicker.ImagePicker;
//import com.myhexaville.smartimagepicker.ImagePicker;
//import com.myhexaville.smartimagepicker.OnImagePickedListener;

import java.io.File;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityEditProfileBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.UtillsG;

public class EditProfileActivity extends BaseActivity<ActivityEditProfileBinding, EditProfilePresenter> implements EditProfileView {

//    private ImagePicker imagePicker;

    public static void start(Context context) {
        Intent starter = new Intent(context, EditProfileActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_edit_profile;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new EditProfilePresenter());
        getPresenter().attachView(this);

        ImagePicker.setMinQuality(600, 600);
    }

    @Override
    public Context getActivityG() {
        return EditProfileActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar(getString(R.string.edit_profile));
        getDataBinder().setBinder(getPresenter());

        getDataBinder().imageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickImage(EditProfileActivity.this, "Select Image");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        getDataBinder().imageViewProfilePic.setImageBitmap(bitmap);

    }

    @Override
    public void hideKeyboard(View view) {
        UtillsG.hideKeyboard(getActivityG(), view);
    }
}
