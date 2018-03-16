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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import ggn.home.help.R;
import ggn.home.help.features.dashboard.menu.DrawerAdapter;
import ggn.home.help.features.dashboard.menu.DrawerItem;
import ggn.home.help.features.dashboard.menu.SimpleItem;
import ggn.home.help.features.dashboard.myMemories.MemoriesFragment;

public class DashboardActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_FRIEND_LIST = 1;
    private static final int POS_FAMILY_LIST = 2;
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
                createItemFor(POS_FAMILY_LIST),
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
                if (findViewById(R.id.recyclerViewAccounts).getVisibility() == View.GONE) {
                    findViewById(R.id.listAccounts).setVisibility(View.VISIBLE);
                    findViewById(R.id.list).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.listAccounts).setVisibility(View.GONE);
                    findViewById(R.id.list).setVisibility(View.VISIBLE);
                }
            }
        });

        adapter.setSelected(POS_DASHBOARD);
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
                break;
            default:
                Fragment selectedScreen = CenteredTextFragment.createFor(screenTitles[position]);
                showFragment(selectedScreen);
                break;
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.colorPrimary))
                .withTextTint(color(R.color.colorPrimaryDark))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
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
}
