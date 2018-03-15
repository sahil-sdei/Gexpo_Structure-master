package ggn.home.help.features.signIn;

import android.databinding.ObservableField;
import android.view.View;

public interface SignInBinder {
    /**
     * @return email string bind with email edit text.
     */
    ObservableField<String> getEmail();

    /**
     * @return password string bind with password edit text.
     */
    ObservableField<String> getPassword();

    /**
     * login clicked
     *
     * @param view
     */
    void signInClicked(View view);
}
