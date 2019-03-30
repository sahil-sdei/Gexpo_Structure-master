package ggn.home.help.features.dashboard.createChildProfile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.DatePicker;

import com.mvc.imagepicker.ImagePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentCreateChildProfileBinding;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.utils.UtillsG;

public class CreateChildProfileFragment extends BaseFragment<FragmentCreateChildProfileBinding, ChildProfilePresenter> implements ChildProfileView {

    Calendar myCalendar = Calendar.getInstance();

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
        injectPresenter(new ChildProfilePresenter());
        getPresenter().attachView(this);

        ImagePicker.setMinQuality(600, 600);
    }

    @Override
    public void initViews() {
        getDataBinder().setBinder(getPresenter());
        getDataBinder().setHandler(getPresenter());
        getDataBinder().imageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.pickImage(CreateChildProfileFragment.this, "Select your image");
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(getActivityG(), requestCode, resultCode, data);
        String imagePath = ImagePicker.getImagePathFromResult(getActivityG(), requestCode, resultCode, data);
        getDataBinder().imageViewProfilePic.setImageBitmap(bitmap);
        getPresenter().setImagePath(imagePath);
    }

    @Override
    public void hideKeyboard(View view) {
        UtillsG.hideKeyboard(getActivityG(), view);
    }

    @Override
    public void onCreatedSuccessfully() {
        getDataBinder().imageViewProfilePic.setImageResource(0);
        ((DashboardActivity)getActivity()).setUpAccounts(getLocalData().getChildAccounts().data.childs);
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
