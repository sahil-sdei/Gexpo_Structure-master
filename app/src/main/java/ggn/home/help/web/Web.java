package ggn.home.help.web;

public @interface Web {

    @interface Keys {
        String PASSWORD = "password";
        String LOGIN = "login";
        String LOGIN_DETAILS = "login_details";
        String USER_DETAILS = "user_details";
        String DETAILS = "details";

    }

    @interface Path {
        String BASE_URL = "http://18.216.102.186/memoreeta/webservices/";
        String SIGN_IN = "login";
        String SIGN_UP = "signup";
        String FORGOT_PASSWORD = "forgot_password";
        String CHANGE_PASSWORD = "change_password";
        String CATEGORIES = "categories";

    }
}
