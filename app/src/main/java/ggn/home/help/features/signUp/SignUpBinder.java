package ggn.home.help.features.signUp;

import android.databinding.ObservableField;
import android.view.View;

public interface SignUpBinder {
    /**
     * @return email string bind with email edit text.
     */
    ObservableField<String> getEmail();

    /**
     * @return password string bind with password edit text.
     */
    ObservableField<String> getPassword();

    /**
     * @return password string bind with password edit text.
     */
    ObservableField<String> getFullName();

    /**
     * login clicked
     *
     * @param view
     */
    void signUpClicked(View view);
}
