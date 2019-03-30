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
        String BASE_URL = "http://13.59.58.80/memoreeta/webservices/";
                //"http://18.216.102.186/memoreeta/webservices/";
        String SIGN_IN = "login";
        String SIGN_UP = "signup";
        String FORGOT_PASSWORD = "forgot_password";
        String CHANGE_PASSWORD = "change_password";
        String CATEGORIES = "categories";
        String SOCIAL_LOGIN = "social_login";
        String ADD_SUGGESTION = "add_suggestion";
        String ADD_CHILD = "add_child";
        String ADD_MEMORY = "add_memory";
        String FULL_LIFE_ALBUM = "full_life_album";
        String CHILD_ACCOUNTS = "child_accounts";
        String PROFILE = "profile";
        String EDIT_PROFILE = "profile_edit";
        String POSTS = "posts";
        String ADD_POST = "add_post";
        String COMMENT_LIST = "comment_list";
        String LIKE_POST = "like";
        String COMMENT = "comment";
        String SEARCH_USERS = "search_users";
        String ADD_FRIEND = "add_friend";
        String RELATIONS = "relations";
        String FRIEND_REQUEST = "friend_request";
        String ACCEPT_REQUEST = "accept_request";
        String FRIENDS_LIST = "friends_list";
        String FAMILY_LIST = "family_list";
        String UN_FRIEND = "unfriend";
        String ACCOUNT_TRANSFER = "account_transfer";
        String DELETE_ACCOUNT = "delete_account";
        String USER_PRIVACY = "user_privacy";
        String PRIVACY_SETTING = "privacy_setting";
        String DELETE_COMMENT = "delete_comment";

    }
}
