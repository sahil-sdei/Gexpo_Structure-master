package ggn.home.help.features.dashboard.myMemories;

import java.util.List;

/**
 * Created by sahilsa on 28/3/18.
 */

public class Memory {
    public String userName;
    public String image;
    public List<String> listImages;
    public String likes;
    public String comments;
    public String userPicture;

    public Memory(String userName, String image, String likes, String comments, String userPicture, List<String> listImages) {
        this.userName = userName;
        this.image = image;
        this.image = image;
        this.likes = likes;
        this.comments = comments;
        this.userPicture = userPicture;
        this.listImages = listImages;
    }
}
