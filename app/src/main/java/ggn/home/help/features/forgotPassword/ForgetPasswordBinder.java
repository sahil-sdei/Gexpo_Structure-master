package ggn.home.help.features.forgotPassword;

import android.databinding.ObservableField;
import android.view.View;

public interface ForgetPasswordBinder {
    /**
     * @return email string bind with email edit text.
     */
    ObservableField<String> getEmail();

    /**
     * login clicked
     *
     * @param view
     */
    void requestPassword(View view);
}
