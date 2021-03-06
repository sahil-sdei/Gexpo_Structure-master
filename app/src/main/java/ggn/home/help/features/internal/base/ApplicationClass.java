package ggn.home.help.features.internal.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.fabric.sdk.android.Fabric;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ggn.home.help.utils.TypefaceUtil;
import ggn.home.help.web.Web;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by singh.gagandeep on 12/09/17.
 */

public class ApplicationClass extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {
    private static boolean isInterestingActivityVisible;
    private static Retrofit retrofit;
    private static String userId;
    private static String authToken;

    public static Retrofit getRetrofit(Context context, int type) {
        initRetrofitModule(context, type);
        return retrofit;
    }

    public static void resetRetrofit() {
        retrofit = null;
    }

    public static void setUserId(String userId) {
        ApplicationClass.userId = userId;
    }

    public static void setAuthToken(String authToken) {
        ApplicationClass.authToken = authToken;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getAuthToken() {
        return authToken;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        try {
            MultiDex.install(this);
        } catch (RuntimeException multiDexException) {
            multiDexException.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sInstance = this;

        // Register to be notified of activity state changes
//        registerActivityLifecycleCallbacks(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Light.ttf");
        //for camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        initRetrofitModule(getApplicationContext(), 1);
    }

    private static void initRetrofitModule(Context context, int type) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Web.Path.BASE_URL)
                .callbackExecutor(Executors.newFixedThreadPool(5))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okClient(context))
                .build();
    }

    private static OkHttpClient okClient(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(1, TimeUnit.MINUTES);
        httpClient.readTimeout(1, TimeUnit.MINUTES);
        httpClient.addInterceptor(interceptor);

        return httpClient.build();
    }

    private static ApplicationClass sInstance = null;

    // Getter to access Singleton instance
    public static ApplicationClass getInstance() {
        return sInstance;
    }

    public static boolean isIsInterestingActivityVisible() {
        return isInterestingActivityVisible;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
