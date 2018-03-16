package ggn.home.help.features.changePassword;

import android.databinding.ObservableField;
import android.view.View;

public interface ChangePasswordBinder {
    /**
     * @return old password string bind with old password edit text.
     */
    ObservableField<String> getOldPassword();

    /**
     * @return new password string bind with new password edit text.
     */
    ObservableField<String> getNewPassword();

    /**
     * @return new password string bind with new password edit text.
     */
    ObservableField<String> getConfirmPassword();

    /**
     * save button clicked
     *
     * @param view
     */
    void saveClicked(View view);
}
