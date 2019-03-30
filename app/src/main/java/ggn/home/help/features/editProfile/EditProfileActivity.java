package ggn.home.help.features.editProfile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;

import com.mvc.imagepicker.ImagePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityEditProfileBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.DateHelper;
import ggn.home.help.utils.UtillsG;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.request.BasicRequest;
import ggn.home.help.web.request.ProfileRequest;
import ggn.home.help.web.response.ProfileResponse;

public class EditProfileActivity extends BaseActivity<ActivityEditProfileBinding, EditProfilePresenter> implements EditProfileView {

    Calendar myCalendar = Calendar.getInstance();

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
        getDataBinder().setHandler(getPresenter());

        getDataBinder().imageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickImage(EditProfileActivity.this, "Select Image");
            }
        });

        getDataBinder().editTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivityG(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ProfileRequest profileRequest= new ProfileRequest();
        profileRequest.token = getLocalData().getAuthToken();
        profileRequest.userId = Integer.parseInt(getLocalData().getUserId());
        profileRequest.page = 1;
        profileRequest.tab = 2;
        getPresenter().getProfile(profileRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        String imagePath = ImagePicker.getImagePathFromResult(getActivityG(), requestCode, resultCode, data);
        getDataBinder().imageViewProfilePic.setImageBitmap(bitmap);
        getPresenter().setImagePath(imagePath);
    }

    @Override
    public void hideKeyboard(View view) {
        UtillsG.hideKeyboard(getActivityG(), view);
    }

    @Override
    public void showProfileData(ProfileResponse output) {
        getDataBinder().editTextName.setText(output.about.name);
        if (!TextUtils.isEmpty(output.about.dob))
            getDataBinder().editTextDateOfBirth.setText(DateHelper.getInstance().getMyDateFormat(output.about.dob, DateHelper.ServerBasic, DateHelper.DOB));
        getDataBinder().editTextAddress.setText(output.about.address);
        getDataBinder().editTextLifeLesson.setText(output.about.lifeLession);

        if (!TextUtils.isEmpty(output.about.profileImage))
            ImageLoader.loadImageSmall(getDataBinder().imageViewProfilePic, output.about.profileImage);
        else
            getDataBinder().imageViewProfilePic.setImageResource(R.drawable.ic_user_placeholder);

        if (!TextUtils.isEmpty(output.about.gender))
            if (output.about.gender.equalsIgnoreCase("Male")) {
                getDataBinder().radioButtonMale.setChecked(true);
            } else {
                getDataBinder().radioButtonFemale.setChecked(true);
            }
    }

    @Override
    public void editProfileSuccessful() {
        Intent intent = getIntent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "dd MMM, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        getDataBinder().editTextDateOfBirth.setText(sdf.format(myCalendar.getTime()));
    }
}
