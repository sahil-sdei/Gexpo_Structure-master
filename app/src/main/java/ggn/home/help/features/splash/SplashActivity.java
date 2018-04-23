package ggn.home.help.features.splash;

import android.content.Context;
import android.content.Intent;

import ggn.home.help.R;
import ggn.home.help.features.dashboard.DashboardActivity;
import ggn.home.help.features.internal.base.BaseActivityNoBinding;
import ggn.home.help.features.signIn.SignInActivity;
import ggn.home.help.utils.NetworkReceiver;
import ggn.home.help.utils.UtillsG;

public class SplashActivity extends BaseActivityNoBinding<SplashPresenter> implements SplashView {

    public static final int SPLASH_TIMER = 3000;

    public static void start(Context context) {
        Intent starter = new Intent(context, SplashActivity.class);
        context.startActivity(starter);
        UtillsG.finishAll(context);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new SplashPresenter());
        getPresenter().attachView(this);

        getPresenter().splashCheck();
    }

    @Override
    public Context getActivityG() {
        return SplashActivity.this;
    }

    @Override
    public void initViews() {
        //no initialization required for this activity ,as no view used..!

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i("IntroActivity", "printHashKey() Hash Key: " + hashKey);
//            }
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("IntroActivity", "printHashKey()", e);
//        } catch (Exception e) {
//            Log.e("IntroActivity", "printHashKey()", e);
//        }

    }

    @Override
    public long splashTimer() {
        return SPLASH_TIMER;
    }

    @Override
    public void showOptions() {
        if (getLocalData().getRememberMe())
            DashboardActivity.start(getActivityG());
        else
            SignInActivity.start(getActivityG());
        finish();
    }

    @Override
    public void openDashBoard() {
        if (getLocalData().getRememberMe())
            DashboardActivity.start(getActivityG());
        else
            SignInActivity.start(getActivityG());
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
