package ggn.home.help.features.familyMembers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivitySelectFamilyMemberBinding;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.Constants;
import ggn.home.help.web.response.AllFamilyResponse;
import ggn.home.help.web.response.Gallery;

public class FamilyMembersActivity extends BaseActivity<ActivitySelectFamilyMemberBinding, FamilyMembersPresenter> implements FamilyMembersView, View.OnClickListener {

    private List<AllFamilyResponse.Datum> familyMembers;
    private FamilyMemberAdapter familyMemberAdapter;
    private MenuItem menuItemDone;

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

        getPresenter().getFamily(1);

//        familyMembers = new ArrayList<>();
//        familyMembers.add(new FamilyMembers("qq", false));
//        familyMembers.add(new FamilyMembers("qq", false));
//        familyMembers.add(new FamilyMembers("qq", false));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeLayoutAllFamily:
                if (familyMembers != null) {
                    for (AllFamilyResponse.Datum f : familyMembers) {
                        f.isSelected = true;
                    }
                    familyMemberAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        invalidateOptionsMenu();
        menuItemDone = menu.findItem(R.id.action_done);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_done:
                if(familyMembers!=null) {
                    boolean isFound = false;
                    for (AllFamilyResponse.Datum f : familyMembers) {
                        if (f.isSelected) {
                            isFound = true;
                            break;
                        }
                    }
                    if (isFound) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(Constants.Extras.RETURN_DATA, (Serializable) familyMembers);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                        Toast.makeText(getActivityG(), "Please select atleast one family member.", Toast.LENGTH_LONG).show();
                    }
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

    @Override
    public void showFamily(AllFamilyResponse output) {
        getDataBinder().textViewNoRecords.setVisibility(View.GONE);
        getDataBinder().relativeLayoutAllFamily.setVisibility(View.VISIBLE);
        familyMembers = output.data;
        getDataBinder().recyclerViewFamilyMembers.setHasFixedSize(true);
        getDataBinder().recyclerViewFamilyMembers.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        familyMemberAdapter = new FamilyMemberAdapter(familyMembers, getActivityG());
        familyMemberAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerViewFamilyMembers.setAdapter(familyMemberAdapter);
    }

    @Override
    public void noDataFound() {
        getDataBinder().textViewNoRecords.setVisibility(View.VISIBLE);
        getDataBinder().relativeLayoutAllFamily.setVisibility(View.GONE);
        menuItemDone.setVisible(false);
    }
}
