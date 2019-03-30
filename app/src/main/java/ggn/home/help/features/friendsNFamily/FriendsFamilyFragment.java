package ggn.home.help.features.friendsNFamily;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentFriendsFamilyBinding;
import ggn.home.help.features.familyListing.FamilyActivity;
import ggn.home.help.features.friendsListing.FriendsActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.searchUser.SearchUserActivity;
import ggn.home.help.web.response.FriendRequestsResponse;


public class FriendsFamilyFragment extends BaseFragment<FragmentFriendsFamilyBinding, FriendsFamilyPresenter> implements FriendsFamilyView {

    private RequestsAdapter requestsAdapter;
    private List<FriendRequestsResponse.Datum> listRequests;
    private CallbackManager callbackManager;

    public static FriendsFamilyFragment newInstance() {
        FriendsFamilyFragment friendsFamilyFragment = new FriendsFamilyFragment();
        return friendsFamilyFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friends_family;
    }

    @Override
    protected void onCreateFragmentG() {
        setHasOptionsMenu(true);
        injectPresenter(new FriendsFamilyPresenter());
        getPresenter().attachView(this);


        callbackManager = CallbackManager.Factory.create();

        AppInviteDialog mInvititeDialog = new AppInviteDialog(this);
        mInvititeDialog.registerCallback(callbackManager,
                new FacebookCallback<AppInviteDialog.Result>() {

                    @Override
                    public void onSuccess(AppInviteDialog.Result result) {

                    }

                    @Override
                    public void onCancel() {
                        Log.d("Result", "Cancelled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("Result", "Error " + exception.getMessage());
                    }
                });

    }

    @Override
    public void initViews() {
        getDataBinder().textViewSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchUserActivity.start(getActivityG());
            }
        });

        getDataBinder().relativeLayoutInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFacebookInviteDialog();
            }
        });

        getDataBinder().relativeLayoutFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendsActivity.start(getActivityG(), "Friends");
            }
        });

        getDataBinder().relativeLayoutFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FamilyActivity.start(getActivityG(), "Family");
            }
        });

        getPresenter().getRequests(1);
    }

    private void showFacebookInviteDialog() {
        String appLinkUrl, previewImageUrl;

        appLinkUrl = "https://www.mydomain.com/myapplink";
        previewImageUrl = "https://www.mydomain.com/my_invite_image.jpg";

        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(this, content);
        }
    }

    @Override
    public void showRequests(FriendRequestsResponse output) {
        listRequests = output.data;
        getDataBinder().recyclerViewRequests.setHasFixedSize(true);
        getDataBinder().recyclerViewRequests.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        requestsAdapter = new RequestsAdapter(listRequests, getActivityG(), getPresenter());
        requestsAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewRequests.setAdapter(requestsAdapter);
        getDataBinder().recyclerViewRequests.setNestedScrollingEnabled(false);
    }

    @Override
    public void noRequestsFound() {
        getDataBinder().recyclerViewRequests.setVisibility(View.GONE);
        getDataBinder().textViewNoRequests.setVisibility(View.GONE);
        getDataBinder().textViewRequests.setVisibility(View.GONE);
    }

    @Override
    public void requestAcceptedRejectedSuccessFully(FriendRequestsResponse.Datum datum) {
        listRequests.remove(datum);
        requestsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
