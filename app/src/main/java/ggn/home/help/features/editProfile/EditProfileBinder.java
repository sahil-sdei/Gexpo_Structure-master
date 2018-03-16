package ggn.home.help.features.editProfile;

import android.databinding.ObservableField;
import android.view.View;

public interface EditProfileBinder {
    ObservableField<String> getName();

    ObservableField<String> getGender();

    ObservableField<String> getDOB();

    ObservableField<String> getAddress();

    ObservableField<String> getLifeLesson();

    /**
     * update button clicked
     *
     * @param view
     */
    void updateClicked(View view);
}
