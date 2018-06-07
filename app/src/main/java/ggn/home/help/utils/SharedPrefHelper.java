package ggn.home.help.utils;

import android.content.Context;
import android.content.SharedPreferences;

import ggn.home.help.features.internal.base.ApplicationClass;
import ggn.home.help.web.response.LoginResponse;

public class SharedPrefHelper {
    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;

    public SharedPrefHelper(Context context) {
        preferences = context.getSharedPreferences("Memoreeta", Context.MODE_PRIVATE);
    }

    public void saveUser(LoginResponse loginResponse){
        if(loginResponse==null)
            return;

        setAuthToken(loginResponse.token);
        setUserId(loginResponse.userId);
        setUserName(loginResponse.name);

        ApplicationClass.setUserId(loginResponse.userId);
        ApplicationClass.setAuthToken(loginResponse.token);
        ApplicationClass.resetRetrofit();
    }

    private void setUserName(String userName) {
        edit = preferences.edit();
        edit.putString("userName", userName);
        edit.apply();
    }

    public String getUserName() {
        return preferences.getString("userName", "");
    }

    public void setProfileImage(String profileImage) {
        edit = preferences.edit();
        edit.putString("profileImage", profileImage);
        edit.apply();
    }

    public String getProfileImage() {
        return preferences.getString("profileImage", "");
    }

    private void setEmail(String userName) {
        edit = preferences.edit();
        edit.putString("email", userName);
        edit.apply();
    }

    public String getEmail() {
        return preferences.getString("email", "");
    }

    private void setUserId(String userName) {
        edit = preferences.edit();
        edit.putString("userId", userName);
        edit.apply();
    }

    public String getUserId() {
        return preferences.getString("userId", "");
    }

    private void setAuthToken(String token) {
        edit = preferences.edit();
        edit.putString("authToken", token);
        edit.apply();
    }

    public void setSetRememberMe(boolean isRemember) {
        edit = preferences.edit();
        edit.putBoolean("rememberMe", isRemember);
        edit.apply();
    }

    public boolean getRememberMe(){
        return preferences.getBoolean("rememberMe", false);
    }

    public String getAuthToken() {
        return preferences.getString("authToken", "");
    }

    public void logout() {
        edit = preferences.edit();
        edit.clear();
        edit.apply();
    }
}
