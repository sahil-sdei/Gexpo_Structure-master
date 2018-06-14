package ggn.home.help.features.userProfile;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityUserProfileBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.features.profile.ProfileAboutFragment;
import ggn.home.help.features.userProfile.fragments.UserFriendsFragment;
import ggn.home.help.features.userProfile.fragments.UserGalleryFragment;
import ggn.home.help.features.userProfile.post.UserProfilePostsFragment;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.PagerAdapter;
import ggn.home.help.utils.RelationSelectedListener;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.utils.widgets.RelationsAdapter;
import ggn.home.help.web.request.AddFriendRequest;
import ggn.home.help.web.request.ProfileRequest;
import ggn.home.help.web.response.BasicResponse;
import ggn.home.help.web.response.PostsResponse;
import ggn.home.help.web.response.ProfileResponse;
import ggn.home.help.web.response.RelationsResponse;

public class UserProfileActivity extends BaseActivity<ActivityUserProfileBinding, UserProfilePresenter> implements UserProfileView, RelationSelectedListener {

    private PagerAdapter adapter;
    private AlertDialog dialogRelations;
    private RelationsResponse.Datum relationsObj;
    private RelationsResponse relationsResponse;
    private ProfileResponse profileResponse;
    private boolean tab1Call;
    private boolean tab2Call;
    private boolean tab3Call;
    private boolean tab4Call;

    public static void start(Context context, String userId) {
        Intent starter = new Intent(context, UserProfileActivity.class);
        starter.putExtra(Constants.Extras.USER_ID, userId);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_user_profile;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new UserProfilePresenter());
        getPresenter().attachView(this);

    }

    @Override
    public Context getActivityG() {
        return UserProfileActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("");
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        if (getIntent().getStringExtra(Constants.Extras.USER_ID).equalsIgnoreCase(getLocalData().getUserId())) {
            getDataBinder().textViewAddFriend.setVisibility(View.GONE);
        }

        setupViewPager(getDataBinder().viewPager);
        getDataBinder().tabs.setupWithViewPager(getDataBinder().viewPager);
        getDataBinder().viewPager.setOffscreenPageLimit(4);

        getDataBinder().textViewAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getActivityG(), view);
                popup.getMenuInflater().inflate(R.menu.menu_add_friend, popup.getMenu());

                MenuItem menuItemFriend = popup.getMenu().findItem(R.id.action_friend);
                MenuItem menuItemFamily = popup.getMenu().findItem(R.id.action_family_member);
                MenuItem menuItemUnfriend = popup.getMenu().findItem(R.id.action_un_friend);

                if (profileResponse.about.isFriend == 0) {
                    menuItemFriend.setVisible(true);
                    menuItemFamily.setVisible(true);
                    menuItemUnfriend.setVisible(false);
                } else {
                    menuItemFriend.setVisible(false);
                    menuItemFamily.setVisible(false);
                    menuItemUnfriend.setVisible(true);
                }

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_friend) {
                            sendRequest(true, null);
                        } else if (item.getItemId() == R.id.action_family_member) {
                            showRelationsDialog(relationsResponse.data);
                        } else {
                            getPresenter().unFriend(getIntent().getStringExtra(Constants.Extras.USER_ID));
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        getDataBinder().viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0){
                    if (tab1Call)
                        return;
                    getProfileData(1);
                }
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        if (tab1Call)
                            return;
                        break;
                    case 1:
                        if (tab2Call)
                            return;
                        break;
                    case 2:
                        if (tab3Call)
                            return;
                        break;
                    case 3:
                        if (tab4Call)
                            return;
                        break;
                }
                getProfileData(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getProfileData(int tab) {
        ProfileRequest profileRequest = new ProfileRequest();
        profileRequest.token = getLocalData().getAuthToken();
        profileRequest.userId = Integer.parseInt(getIntent().getStringExtra(Constants.Extras.USER_ID));
        profileRequest.loggedUserId = getLocalData().getUserId();
        profileRequest.tab = tab;
        profileRequest.page = 1;

        getPresenter().getProfile(profileRequest);
        Log.d("Tab", ""+tab);

        switch (tab) {
            case 1:
                tab1Call = true;
                break;
            case 2:
                tab2Call = true;
                break;
            case 3:
                tab3Call = true;
                break;
            case 4:
                tab4Call = true;
                break;
        }
    }

    private void sendRequest(boolean isFriend, String relationId) {
        AddFriendRequest addFriendRequest = new AddFriendRequest();
        if (isFriend)
            addFriendRequest.type = "1";
        else {
            addFriendRequest.type = "2";
            addFriendRequest.relationId = relationId;
        }
        addFriendRequest.token = getLocalData().getAuthToken();
        addFriendRequest.senderId = Integer.parseInt(getLocalData().getUserId());
        addFriendRequest.receiverId = getIntent().getStringExtra(Constants.Extras.USER_ID);
        getPresenter().sendRequest(addFriendRequest);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFrag(UserProfilePostsFragment.newInstance(), getString(R.string.posts));
        adapter.addFrag(ProfileAboutFragment.newInstance(), getString(R.string.about));
        adapter.addFrag(UserGalleryFragment.newInstance(), getString(R.string.gallery));
        adapter.addFrag(UserFriendsFragment.newInstance(), getString(R.string.friends));
        viewPager.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void showProfileData(ProfileResponse output) {
        profileResponse = output;
        getDataBinder().textViewUserName.setText(output.about.name);
        ImageLoader.loadFullWidthImage(getDataBinder().imageViewProfilePic, output.about.profileImage);


        if (output.about.isFriend == 0) {
            getDataBinder().textViewAddFriend.setText("Add Friend");
            getDataBinder().textViewAddFriend.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_tag_people, 0, 0);
        } else if (output.about.isFriend == 2) {
            getDataBinder().textViewAddFriend.setText("Pending");
            getDataBinder().textViewAddFriend.setTextColor(ContextCompat.getColor(getActivityG(), R.color.textRed));
            getDataBinder().textViewAddFriend.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_tag_people, 0, 0);
            getDataBinder().textViewAddFriend.setEnabled(false);
        } else {
            getDataBinder().textViewAddFriend.setText("Unfriend");
            getDataBinder().textViewAddFriend.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_tag_people_blue, 0, 0);
            getDataBinder().textViewAddFriend.setEnabled(false);
        }

        int currentTab = getDataBinder().viewPager.getCurrentItem();
        if (currentTab == 0) {
            ((UserProfilePostsFragment) adapter.getItem(0)).setPostsData(output.posts);
        } else if (currentTab == 1) {
            ((ProfileAboutFragment) adapter.getItem(1)).showProfileData(output);
        } else if (currentTab == 2) {
            ((UserGalleryFragment) adapter.getItem(2)).showProfileData(output);
        } else if (currentTab == 3) {
            ((UserFriendsFragment) adapter.getItem(3)).showProfileData(output);
        }
    }

    @Override
    public void showRelations(RelationsResponse output) {
        this.relationsResponse = output;
    }

    @Override
    public void requestSentSuccessfully(BasicResponse output) {
        getDataBinder().textViewAddFriend.setText("Pending");
        getDataBinder().textViewAddFriend.setTextColor(ContextCompat.getColor(getActivityG(), R.color.textRed));
        getDataBinder().textViewAddFriend.setEnabled(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void unFriendSuccessfully(BasicResponse output) {
        profileResponse.about.isFriend = 0;
        getDataBinder().textViewAddFriend.setText("Add Friend");
        getDataBinder().textViewAddFriend.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_tag_people, 0, 0);
    }

    @Override
    public void onMemoryLiked(PostsResponse.Datum memory) {
    }

    private void showRelationsDialog(List<RelationsResponse.Datum> listRelations) {
        if (relationsObj != null) {
            for (RelationsResponse.Datum data : relationsResponse.data) {
                if (data.relation.id.equalsIgnoreCase(relationsObj.relation.id)) {
                    data.relation.isSelected = true;
                } else
                    data.relation.isSelected = false;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivityG(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Select Relation");
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_list_category_sub_category, null);
        RelationsAdapter relationsAdapter = new RelationsAdapter(getActivityG(), listRelations);
        relationsAdapter.setRelationSelectedListener(UserProfileActivity.this);
        ListView listView = dialogLayout.findViewById(R.id.listViewCategorySubCategory);
        listView.setAdapter(relationsAdapter);
        builder.setView(dialogLayout);

        dialogRelations = builder.create();
        dialogRelations.show();
    }

    @Override
    public void onRelationSelected(RelationsResponse.Datum datum) {
        this.relationsObj = datum;
        if (dialogRelations.isShowing()) {
            dialogRelations.dismiss();
        }

        sendRequest(false, relationsObj.relation.id);
    }
}
