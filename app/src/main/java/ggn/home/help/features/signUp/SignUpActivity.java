package ggn.home.help.features.signUp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import java.util.Arrays;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivitySignUpBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.signIn.SignInActivity;
import ggn.home.help.utils.UtillsG;

public class SignUpActivity extends BaseActivity<ActivitySignUpBinding, SignUpPresenter> implements SignUpView {

    private CallbackManager callbackManager;

    public static void start(Context context) {
        Intent starter = new Intent(context, SignUpActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
//        getWindow().setBackgroundDrawableResource(R.drawable.splash_background);
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new SignUpPresenter());
        getPresenter().attachView(this);

    }

    @Override
    public Context getActivityG() {
        return SignUpActivity.this;
    }

    @Override
    public void initViews() {
        getDataBinder().setBinder(getPresenter());

        callbackManager = CallbackManager.Factory.create();

        getDataBinder().loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));
        getDataBinder().loginButton.registerCallback(callbackManager, getPresenter().getFbCallback());
        LoginManager.getInstance().logOut();

        getDataBinder().buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataBinder().loginButton.performClick();
            }
        });

        SpannableString signIn = new SpannableString(getString(R.string.have_an_account_sign_in));
        ClickableSpan clickableSpanSignIn = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        signIn.setSpan(new StyleSpan(Typeface.ITALIC), 16, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signIn.setSpan(new ForegroundColorSpan(Color.BLUE), 16, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signIn.setSpan(clickableSpanSignIn, 16, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getDataBinder().textViewSignIn.setText(signIn);
        getDataBinder().textViewSignIn.setMovementMethod(LinkMovementMethod.getInstance());
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

    public void signIn(View view) {
        SignInActivity.start(getActivityG());
        finish();
    }
}
