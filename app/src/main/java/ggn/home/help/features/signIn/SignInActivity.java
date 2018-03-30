package ggn.home.help.features.signIn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import java.util.Arrays;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivitySignInBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.signUp.SignUpActivity;
import ggn.home.help.utils.UtillsG;

public class SignInActivity extends BaseActivity<ActivitySignInBinding, SignInPresenter> implements SignInView {

    private CallbackManager callbackManager;

    public static void start(Context context) {
        Intent starter = new Intent(context, SignInActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
//        getWindow().setBackgroundDrawableResource(R.drawable.background);
        return R.layout.activity_sign_in;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new SignInPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return SignInActivity.this;
    }

    @Override
    public void initViews() {
        getDataBinder().setBinder(getPresenter());
        getDataBinder().editTextPassword.setTransformationMethod(new PasswordTransformationMethod());

        callbackManager = CallbackManager.Factory.create();

        getDataBinder().loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        getDataBinder().loginButton.registerCallback(callbackManager, getPresenter().getFbCallback());
        LoginManager.getInstance().logOut();

        getDataBinder().buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtillsG.isNetworkAvailable(getActivityG()))
                    getDataBinder().loginButton.performClick();
                else
                    Toast.makeText(getActivityG(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            }
        });

        SpannableString signUp = new SpannableString(getString(R.string.don_t_have_an_account_sign_up));
        ClickableSpan clickableSpanSignUp = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        signUp.setSpan(new StyleSpan(Typeface.ITALIC), 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivityG(), R.color.textBlue)), 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUp.setSpan(clickableSpanSignUp, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getDataBinder().textViewSignUp.setText(signUp);
        getDataBinder().textViewSignUp.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void finish(View view) {
        this.finish();
    }

//    @Override
//    public void saveDataLocally(UserModel data) {
//        getLocalData().setUserData(data);
//    }

    @Override
    public void hideKeyboard(View view) {
        UtillsG.hideKeyboard(getActivityG(), view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void signUp(View view) {
        SignUpActivity.start(getActivityG());
        finish();
    }
}
