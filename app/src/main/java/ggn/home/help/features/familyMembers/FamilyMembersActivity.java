package ggn.home.help.features.familyMembers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivitySelectFamilyMemberBinding;
import ggn.home.help.features.internal.base.BaseActivity;

public class FamilyMembersActivity extends BaseActivity<ActivitySelectFamilyMemberBinding, FamilyMembersPresenter> implements FamilyMembersView, View.OnClickListener {

    private List<FamilyMembers> familyMembers;
    private FamilyMemberAdapter familyMemberAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, FamilyMembersActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_select_family_member;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new FamilyMembersPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return FamilyMembersActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar(getString(R.string.select_family_members));
        getDataBinder().relativeLayoutAllFamily.setOnClickListener(this);

        familyMembers = new ArrayList<>();
        familyMembers.add(new FamilyMembers("qq", false));
        familyMembers.add(new FamilyMembers("qq", false));
        familyMembers.add(new FamilyMembers("qq", false));


        getDataBinder().recyclerViewFamilyMembers.setHasFixedSize(true);
        getDataBinder().recyclerViewFamilyMembers.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        familyMemberAdapter = new FamilyMemberAdapter(familyMembers, getActivityG());
        familyMemberAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewFamilyMembers.setAdapter(familyMemberAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeLayoutAllFamily:
                if (familyMembers != null) {
                    for (FamilyMembers f : familyMembers) {
                        f.isSelected = true;
                    }
                }
                familyMemberAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_done:
                boolean isFound = false;
                for (FamilyMembers f : familyMembers) {
                    if (f.isSelected) {
                        isFound = true;
                        break;
                    }
                }
                if (isFound) {
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(getActivityG(), "Please select atleast one family member.", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(RESULT_CANCELED, resultIntent);
        finish();
        super.onBackPressed();
    }
}
