package ggn.home.help.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by G-Expo on 07 Mar 2017.
 */
// HomeHelp
@Retention(RetentionPolicy.SOURCE)
public @interface Constants {
    String STCIKER_LIST = "stickerList.srl";
    String FB_ID = "520728504777049";

    @interface Extras {
        String DATA = "data";
        String WISH_ID = "wishId";
        String TRANSITION_NAME_1 = "name";
        String TRANSITION_NAME_2 = "name2";
        String PAGE_NUMBER = "page_number";
        String TITLE = "title";
        String FILE_PATH_ARG = "file_path_arg";
        String RESPONSE_CODE_ARG = "response_code_arg";
        String PRIVACY = "privacy";
        String IS_MEMORY = "is_memory";
        String SCREEN_NUMBER = "screen_number";
        String SELECTED_MEDIA = "selected_media";
    }

    @interface RequestCode {
        int CAMERA = 11;
        int GALLERY = 22;
        int PLACE_PICKER = 101;
        int COLOR_PICKER = 202;
        int IMAGE_SEARCH = 303;
        int INVITE = 404;
        int IMAGESEARCH = 505;
        int ADD_MEMORY = 506;
        int IMAGE_PREVIEW = 507;
        int MANAGE_ACCOUNTS = 508;
        int PRIVACY = 509;
        int SELECT_FAMILY_MEMBERS = 510;
        int SELECT_IMAGES_VIDEOS = 511;
    }

}
