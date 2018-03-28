package ggn.home.help.features.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
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
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
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
import ggn.home.help.features.dashboard.familyTree.FamilyTreeFragment;
import ggn.home.help.features.dashboard.menu.AccountsAdapter;
import ggn.home.help.features.dashboard.menu.DrawerAdapter;
import ggn.home.help.features.dashboard.menu.DrawerItem;
import ggn.home.help.features.dashboard.menu.SimpleItem;
import ggn.home.help.features.dashboard.myMemories.MemoriesFragment;
import ggn.home.help.features.friendsNFamily.FriendsFamilyFragment;
import ggn.home.help.features.profile.ProfileActivity;

import static ggn.home.help.utils.Constants.RequestCode.ADD_MEMORY;

public class DashboardActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_FRIEND_LIST = 1;
    private static final int POS_FAMILY_TREE = 2;
    private static final int POS_EXPORT = 3;
    private static final int POS_CREATE_CHILD_PROFILE = 4;
    private static final int POS_CHANGE_PASSWORD = 5;
    private static final int POS_CONTACT_US = 6;
    private static final int POS_LOGOUT = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    public static void start(Context context) {
        Intent starter = new Intent(context, DashboardActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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
                createItemFor(POS_FRIEND_LIST),
                createItemFor(POS_FAMILY_TREE),
                createItemFor(POS_EXPORT),
                createItemFor(POS_CREATE_CHILD_PROFILE),
                createItemFor(POS_CHANGE_PASSWORD),
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
                } else {
                    findViewById(R.id.listAccounts).setVisibility(View.GONE);
                    findViewById(R.id.list).setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.relativeLayoutManageAccounts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNav.closeMenu();
                Intent intent = new Intent(DashboardActivity.this, ManageAccountsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.relativeLayoutProfilePic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingRootNav.closeMenu();
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        adapter.setSelected(POS_DASHBOARD);

        setUpAccounts();
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_LOGOUT) {
            finish();
        }
        slidingRootNav.closeMenu();

        switch (position) {
            case POS_DASHBOARD:
                showFragment(MemoriesFragment.newInstance());
                setupToolbar("Dashboard", false);
                break;
            case POS_FRIEND_LIST:
                showFragment(FriendsFamilyFragment.newInstance());
                setupToolbar(getString(R.string.friends_n_family), false);
                break;
            case POS_FAMILY_TREE:
                showFragment(FamilyTreeFragment.newInstance());
                setupToolbar(getString(R.string.family_tree), false);
                break;
            case POS_EXPORT:
                showFragment(ExportFragment.newInstance());
                setupToolbar(getString(R.string.export), false);
                break;
            case POS_CREATE_CHILD_PROFILE:
                showFragment(CreateChildProfileFragment.newInstance());
                setupToolbar(getString(R.string.create_child_profile), false);
                break;
            case POS_CHANGE_PASSWORD:
                showFragment(ChangePasswordFragment.newInstance());
                setupToolbar(getString(R.string.change_password), false);
                break;
            default:
                Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
                showFragment(selectedScreen);
                setupToolbar(screenTitles[position], false);
                break;
        }
    }

    private void showFragment(Fragment fragment) {
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
    }

    public void setupToolbar(String title, boolean isBackVisible) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(isBackVisible);

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
                else
                    slidingRootNav.openMenu();
            }
        });

        if (isBackVisible)
            imageViewMenu.setVisibility(View.GONE);
        else
            imageViewMenu.setVisibility(View.VISIBLE);
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

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    private void setUpAccounts() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");

        RecyclerView recyclerView = findViewById(R.id.listAccounts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DashboardActivity.this, LinearLayoutManager.VERTICAL, false));
        AccountsAdapter accountsAdapter = new AccountsAdapter(list, DashboardActivity.this);
        accountsAdapter.setShouldLoadMore(false);
        recyclerView.setAdapter(accountsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_MEMORY) {
            if (resultCode == RESULT_OK) {
                FragmentManager fm = getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                showFragment(MemoriesFragment.newInstance());
            }
        }
    }
}
