package ggn.home.help.web;

public @interface Web {

    @interface Keys {
        String PASSWORD = "password";
        String LOGIN = "login";
        String EMAIL = "email";
        String USERNAME = "username";
    }

    @interface Path {
        String BASE_URL = "https://api.8stem.com:443/v1/";
        String SIGN_IN = "auth/sign_in";
        String SIGN_UP = "auth/sign_up";
        String FB_SIGN_IN = "auth/sign_in_with_facebook";
    }
}
