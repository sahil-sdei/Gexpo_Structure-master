package ggn.home.help.features.dashboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.features.accounts.ManageAccountsActivity;
import ggn.home.help.features.changePassword.ChangePasswordFragment;
import ggn.home.help.features.dashboard.createChildProfile.CreateChildProfileFragment;
import ggn.home.help.features.dashboard.export.ExportFragment;
import ggn.home.help.features.dashboard.familyTree.FamilyTreeActivity;
import ggn.home.help.features.dashboard.menu.AccountsAdapter;
import ggn.home.help.features.dashboard.menu.AccountsAdapterBinder;
import ggn.home.help.features.dashboard.menu.DrawerAdapter;
import ggn.home.help.features.dashboard.menu.DrawerItem;
import ggn.home.help.features.dashboard.menu.SimpleItem;
import ggn.home.help.features.dashboard.myMemories.MemoriesFragment;
import ggn.home.help.features.friendsNFamily.FriendsFamilyFragment;
import ggn.home.help.features.fullLifeAlbum.FullLifeAlbumActivity;
import ggn.home.help.features.memoryCategories.MemoryCategoriesFragment;
import ggn.home.help.features.profile.ProfileActivity;
import ggn.home.help.features.services.UploadPostService;
import ggn.home.help.features.services.UploadService;
import ggn.home.help.features.settings.SettingsActivity;
import ggn.home.help.features.signIn.SignInActivity;
import ggn.home.help.utils.CallBackG;
import ggn.home.help.utils.Constants;
import ggn.home.help.utils.DialogHelper;
import ggn.home.help.utils.SharedPrefHelper;
import ggn.home.help.utils.bitmapUtils.ImageLoader;
import ggn.home.help.web.response.Child;

import static ggn.home.help.features.services.UploadPostService.RESULT_TYPE;
import static ggn.home.help.utils.Constants.RequestCode.ADD_MEMORY;
import static ggn.home.help.utils.Constants.RequestCode.MANAGE_ACCOUNTS;

public class DashboardActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener, AccountsAdapterBinder {

    private boolean doubleBackToExitPressedOnce = false;
    private static final int POS_DASHBOARD = 0;
    private static final int POS_ADD_NEW_MEMORY = 1;
    private static final int POS_FULL_LIFE_ALBUM = 2;
    private static final int POS_FRIEND_LIST = 3;
    private static final int POS_FAMILY_TREE = 4;
    private static final int POS_EXPORT = 5;
    private static final int POS_CREATE_CHILD_PROFILE = 6;
    private static final int POS_CHANGE_PASSWORD = 7;
    private static final int POS_SETTINGS = 8;
    private static final int POS_CONTACT_US = 9;
    private static final int POS_LOGOUT = 10;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    private SharedPrefHelper sharedPrefHelper;

    private boolean childSelected;

    private Child childObj;

    private List<Child> listChild;

    public static void start(Context context) {
        Intent starter = new Intent(context, DashboardActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, boolean isMemory) {
        Intent starter = new Intent(context, DashboardActivity.class);
        starter.putExtra(Constants.Extras.IS_MEMORY, isMemory);
        context.startActivity(starter);
    }

    public static void start(Context context, int screen) {
        Intent starter = new Intent(context, DashboardActivity.class);
        starter.putExtra(Constants.Extras.SCREEN_NUMBER, screen);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPrefHelper = new SharedPrefHelper(DashboardActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ADD_NEW_MEMORY),
                createItemFor(POS_FULL_LIFE_ALBUM),
                createItemFor(POS_FRIEND_LIST),
                createItemFor(POS_FAMILY_TREE),
                createItemFor(POS_EXPORT),
                createItemFor(POS_CREATE_CHILD_PROFILE),
                createItemFor(POS_CHANGE_PASSWORD),
                createItemFor(POS_SETTINGS),
                createItemFor(POS_CONTACT_US),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        findViewById(R.id.textViewUserName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findViewById(R.id.listAccounts).getVisibility() == View.GONE) {
                    findViewById(R.id.listAccounts).setVisibility(View.VISIBLE);
                    findViewById(R.id.list).setVisibility(View.GONE);
                    findViewById(R.id.textViewChild).setVisibility(View.VISIBLE);

                    if (!TextUtils.isEmpty(sharedPrefHelper.getChildId())) {
                        findViewById(R.id.textViewParent).setVisibility(View.VISIBLE);
                        findViewById(R.id.relativeLayoutParentProfile).setVisibility(View.VISIBLE);
                    }

                    if (listChild != null)
                        if (listChild.size() == 0) {
                            findViewById(R.id.textViewChild).setVisibility(View.GONE);
                        }
                } else {
                    findViewById(R.id.listAccounts).setVisibility(View.GONE);
                    findViewById(R.id.list).setVisibility(View.VISIBLE);
                    findViewById(R.id.textViewChild).setVisibility(View.GONE);

                    findViewById(R.id.relativeLayoutParentProfile).setVisibility(View.GONE);
                    findViewById(R.id.textViewParent).setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.relativeLayoutManageAccounts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNav.closeMenu();
                Intent intent = new Intent(DashboardActivity.this, ManageAccountsActivity.class);
                startActivityForResult(intent, Constants.RequestCode.MANAGE_ACCOUNTS);
            }
        });

        findViewById(R.id.relativeLayoutProfilePic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNav.closeMenu();
                findViewById(R.id.listAccounts).setVisibility(View.GONE);
                findViewById(R.id.list).setVisibility(View.VISIBLE);
                findViewById(R.id.textViewChild).setVisibility(View.GONE);
                findViewById(R.id.relativeLayoutParentProfile).setVisibility(View.GONE);
                findViewById(R.id.textViewParent).setVisibility(View.GONE);
                if (!childSelected) {
                    Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                    intent.putExtra(Constants.Extras.USER_ID, sharedPrefHelper.getUserId());
                    startActivity(intent);
                } else {
                    if (childObj != null) {
                        Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                        intent.putExtra(Constants.Extras.USER_ID, childObj.id);
                        startActivity(intent);
                    }
                }
            }
        });

        findViewById(R.id.relativeLayoutParentProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUserNameOnNav();
                slidingRootNav.closeMenu();
                showFragment(MemoriesFragment.newInstance());
                setupToolbar("", true, false);
                setUpParentSideMenu();
            }
        });

        adapter.setSelected(POS_DASHBOARD);

        if (getIntent().getBooleanExtra(Constants.Extras.IS_MEMORY, false)) {
            showFragment(MemoryCategoriesFragment.newInstance(true));
            setupToolbar(getString(R.string.add_new_memory), false, false);
        }

        if (getIntent().getIntExtra(Constants.Extras.SCREEN_NUMBER, 0) == 1) {
            FullLifeAlbumActivity.start(DashboardActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(sharedPrefHelper.getChildId())) {
            Child childObj = null;
            if (listChild != null) {
                for (Child cObj : listChild) {
                    if (cObj.id.equalsIgnoreCase(sharedPrefHelper.getChildId())) {
                        childObj = cObj;
                        break;
                    }
                }
                showUserNameOnNav(childObj.profileImage, childObj.userProfile.name);
                setUpChildSideMenu();
            }
        } else {
            showUserNameOnNav();
            setUpParentSideMenu();
        }

        if (sharedPrefHelper.getChildAccounts() != null) {
            setUpAccounts(sharedPrefHelper.getChildAccounts().data.childs);
        } else {
            List<Child> list = new ArrayList<>();
            setUpAccounts(list);
        }

        registerReceiver(receiverPost, new IntentFilter(
                UploadPostService.NOTIFICATION));
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            slidingRootNav.closeMenu();
            DialogHelper.getInstance().showWithAction(DashboardActivity.this, "Are you sure you want to logout from the app?", new CallBackG<String>() {
                @Override
                public void callBack(String output) {
                    sharedPrefHelper.logout();
                    SignInActivity.start(DashboardActivity.this);
                    finish();
                }
            });
            return;
        }
        slidingRootNav.closeMenu();

        switch (position) {
            case POS_DASHBOARD:
                showFragment(MemoriesFragment.newInstance());
                setupToolbar("", true, false);
                break;
            case POS_ADD_NEW_MEMORY:
                showFragment(MemoryCategoriesFragment.newInstance(true));
                setupToolbar(getString(R.string.add_new_memory), false, false);
                break;
            case POS_FRIEND_LIST:
                showFragment(FriendsFamilyFragment.newInstance());
                setupToolbar(getString(R.string.friends_n_family), false, false);
                break;
            case POS_FAMILY_TREE:
                FamilyTreeActivity.start(DashboardActivity.this);
                break;
            case POS_FULL_LIFE_ALBUM:
                FullLifeAlbumActivity.start(DashboardActivity.this);
                break;
            case POS_EXPORT:
                showFragment(ExportFragment.newInstance());
                setupToolbar(getString(R.string.export), false, false);
                break;
            case POS_CREATE_CHILD_PROFILE:
                showFragment(CreateChildProfileFragment.newInstance());
                setupToolbar(getString(R.string.create_child_profile), false, false);
                break;
            case POS_SETTINGS:
                SettingsActivity.start(DashboardActivity.this);
                break;
            case POS_CHANGE_PASSWORD:
                showFragment(ChangePasswordFragment.newInstance());
                setupToolbar(getString(R.string.change_password), false, false);
                break;
            default:
                Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
                showFragment(selectedScreen);
                setupToolbar(screenTitles[position], false, false);
                break;
        }
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        invalidateOptionsMenu();
    }

    public void showFragmentWithBackStack(Fragment fragment, String title) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
        setupToolbar(title, false, false);
    }

    public void setupToolbar(String title, boolean isLogoVisible, boolean isTransparent) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(isTransparent);

        TextView toolbarText = toolbar.findViewById(R.id.toolbar_title);
        if (toolbarText != null) {
            toolbarText.setText(title);
        }

        ImageView imageViewMenu = toolbar.findViewById(R.id.imageViewMenu);
        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingRootNav.isMenuOpened())
                    slidingRootNav.closeMenu();
                else {
                    slidingRootNav.openMenu();
                    findViewById(R.id.listAccounts).setVisibility(View.GONE);
                    findViewById(R.id.list).setVisibility(View.VISIBLE);
                    findViewById(R.id.textViewChild).setVisibility(View.GONE);
                    findViewById(R.id.relativeLayoutParentProfile).setVisibility(View.GONE);
                    findViewById(R.id.textViewParent).setVisibility(View.GONE);
                }
            }
        });

        if (isTransparent) {
            toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            imageViewMenu.setVisibility(View.GONE);
        } else {
            imageViewMenu.setVisibility(View.VISIBLE);
        }

        ImageView imageViewLogo = toolbar.findViewById(R.id.imageViewTopLogo);
        imageViewLogo.setVisibility(isLogoVisible ? View.VISIBLE : View.GONE);
        imageViewLogo.setImageResource(isTransparent ? R.drawable.family_tree : R.drawable.memoreeta);
        toolbar.setBackgroundColor(isTransparent ? ContextCompat.getColor(DashboardActivity.this, R.color.transparent) : ContextCompat.getColor(DashboardActivity.this, R.color.white));

    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withTextTint(color(R.color.greyDark))
                .withSelectedTextTint(color(R.color.black));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    private String[] loadScreenTitlesChild() {
        return getResources().getStringArray(R.array.ld_activityScreenTitlesChild);
    }

    private Drawable[] loadScreenIconsChild() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIconsChild);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    private void showUserNameOnNav() {
        ((TextView) findViewById(R.id.textViewUserName)).setText(sharedPrefHelper.getUserName());
        ((TextView) findViewById(R.id.textViewParentUserName)).setText(sharedPrefHelper.getUserName());
        if (!TextUtils.isEmpty(sharedPrefHelper.getProfileImage())) {
            ImageLoader.loadImageSmall((findViewById(R.id.imageViewProfilePic)), sharedPrefHelper.getProfileImage());
            ImageLoader.loadImageSmall((findViewById(R.id.imageViewParentProfilePic)), sharedPrefHelper.getProfileImage());
        } else {
            ((ImageView) findViewById(R.id.imageViewProfilePic)).setImageResource(R.drawable.ic_user_placeholder);
            ((ImageView) findViewById(R.id.imageViewParentProfilePic)).setImageResource(R.drawable.ic_user_placeholder);
        }
        findViewById(R.id.relativeLayoutParentProfile).setVisibility(View.GONE);
        findViewById(R.id.textViewParent).setVisibility(View.GONE);
        childSelected = false;

        if (sharedPrefHelper != null)
            sharedPrefHelper.clearChild();
    }

    private void showUserNameOnNav(String profilePic, String username) {
        ((TextView) findViewById(R.id.textViewUserName)).setText(username);
        ((TextView) findViewById(R.id.textViewParentUserName)).setText(sharedPrefHelper.getUserName());
        if (!TextUtils.isEmpty(profilePic)) {
            ImageLoader.loadImageSmall((findViewById(R.id.imageViewProfilePic)), "http://18.216.102.186/memoreeta/files/profileimage/" + profilePic);
        } else {
            ((ImageView) findViewById(R.id.imageViewProfilePic)).setImageResource(R.drawable.ic_user_placeholder);
        }

        if (!TextUtils.isEmpty(sharedPrefHelper.getProfileImage())) {
            ImageLoader.loadImageSmall((findViewById(R.id.imageViewParentProfilePic)), sharedPrefHelper.getProfileImage());

        } else {
            ((ImageView) findViewById(R.id.imageViewParentProfilePic)).setImageResource(R.drawable.ic_user_placeholder);
        }

        findViewById(R.id.relativeLayoutParentProfile).setVisibility(View.VISIBLE);
        findViewById(R.id.textViewParent).setVisibility(View.VISIBLE);
    }

    public void setUpAccounts(List<Child> child) {
        listChild = child;
        RecyclerView recyclerView = findViewById(R.id.listAccounts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.VERTICAL, false));
        AccountsAdapter accountsAdapter = new AccountsAdapter(child, DashboardActivity.this);
        accountsAdapter.setShouldLoadMore(false);
        accountsAdapter.setAccountsAdapterBinder(this);
        recyclerView.setAdapter(accountsAdapter);

        if (!TextUtils.isEmpty(sharedPrefHelper.getChildId())) {
            Child childObj = null;
            for (Child cObj : child) {
                if (cObj.id.equalsIgnoreCase(sharedPrefHelper.getChildId())) {
                    childObj = cObj;
                    break;
                }
            }
            if (childObj != null) {
                showUserNameOnNav(childObj.profileImage, childObj.userProfile.name);
                setUpChildSideMenu();
            }else{
                showUserNameOnNav();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_MEMORY:
                if (resultCode == RESULT_OK) {
                    FragmentManager fm = getSupportFragmentManager();
                    for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }
                    showFragment(MemoriesFragment.newInstance());
                    setupToolbar("", true, false);
                }
                break;

            case MANAGE_ACCOUNTS:
                if (resultCode == RESULT_OK) {
                    showFragment(CreateChildProfileFragment.newInstance());
                    setupToolbar(getString(R.string.create_child_profile), false, false);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Snackbar.make(findViewById(android.R.id.content), getString(R.string.press_again_to_exit_app), Snackbar.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
    }

    @Override
    public void onAccountClicked(Child child) {
        childObj = child;
        childSelected = true;
        sharedPrefHelper.setChild(child.id);
        showUserNameOnNav(child.profileImage, child.userProfile.name);
        slidingRootNav.closeMenu();
        showFragment(MemoriesFragment.newInstance());
        setupToolbar("", true, false);

        setUpChildSideMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiverPost);
    }

    private BroadcastReceiver receiverPost = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int resultCode = bundle.getInt(UploadPostService.RESULT_POST);
                if (resultCode == RESULT_OK) {
                    findViewById(R.id.linearLayoutProgress).setVisibility(View.GONE);
                    if (intent.getIntExtra(RESULT_TYPE, 0) == 1) {
                        showFragment(MemoriesFragment.newInstance());
                        setupToolbar("", true, false);
                    }
                } else if (resultCode == RESULT_FIRST_USER) {
                    findViewById(R.id.linearLayoutProgress).setVisibility(View.VISIBLE);
                    ((ProgressBar) findViewById(R.id.progress)).setProgress(bundle.getInt(UploadService.PROGRESS));
                    if (intent.getIntExtra(RESULT_TYPE, 0) == 1) {
                        ((TextView) findViewById(R.id.textViewPostMessage)).setText("Posting your memories");
                    } else {
                        ((TextView) findViewById(R.id.textViewPostMessage)).setText("Uploading your memories");
                    }

                } else {
                    findViewById(R.id.linearLayoutProgress).setVisibility(View.GONE);
                }
            }
        }
    };

    private void setUpChildSideMenu() {
        screenIcons = loadScreenIconsChild();
        screenTitles = loadScreenTitlesChild();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ADD_NEW_MEMORY),
                createItemFor(POS_FULL_LIFE_ALBUM)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }

    private void setUpParentSideMenu() {
        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ADD_NEW_MEMORY),
                createItemFor(POS_FULL_LIFE_ALBUM),
                createItemFor(POS_FRIEND_LIST),
                createItemFor(POS_FAMILY_TREE),
                createItemFor(POS_EXPORT),
                createItemFor(POS_CREATE_CHILD_PROFILE),
                createItemFor(POS_CHANGE_PASSWORD),
                createItemFor(POS_SETTINGS),
                createItemFor(POS_CONTACT_US),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }
}
