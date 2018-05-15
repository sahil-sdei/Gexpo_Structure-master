package ggn.home.help.features.dashboard.createChildProfile;

import android.databinding.ObservableField;
import android.view.View;

public interface CreateChildProfileBinder {

    ObservableField<String> getName();
    ObservableField<String> getNickName();
    ObservableField<String> getDOB();
    ObservableField<String> getGender();
    ObservableField<String> getAddress();
    ObservableField<String> getLifeLesson();

    void saveClicked(View view);
}
