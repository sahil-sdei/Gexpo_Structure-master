package ggn.home.help.features.notifications;

/**
 * Created by sahilsa on 3/4/18.
 */

public class Notifications {
    public String username;
    public String text;
    public String time;
    public String userPicture;

    public Notifications(String username, String text, String time, String userPicture) {
        this.username = username;
        this.text = text;
        this.time = time;
        this.userPicture = userPicture;
    }
}
