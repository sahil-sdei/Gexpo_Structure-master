package ggn.home.help.features.memoryCategories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.features.notifications.NotificationActivity;
import ggn.home.help.utils.Constants;


public class MemoryCategoriesFragment extends BaseFragment<FragmentRecyclerViewBinding, MemoryCategoriesPresenter> implements MemoryCategoriesView {

    private List<Categories> list;
    private MemoryCategoriesAdapter memoryCategoriesAdapter;

    public static MemoryCategoriesFragment newInstance(boolean isMemory) {
        MemoryCategoriesFragment activityFragment = new MemoryCategoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.Extras.IS_MEMORY, isMemory);
        activityFragment.setArguments(bundle);
        return activityFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void onCreateFragmentG() {
        setHasOptionsMenu(true);
        injectPresenter(new MemoryCategoriesPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public void initViews() {
        list = new ArrayList<>();

        List<SubCategories> preBirth = new ArrayList<>();
        preBirth.add(new SubCategories("Pregnancy Moments", "ic_delivery_moments", "demo_add_mem"));
        preBirth.add(new SubCategories("Baby Inside Me", "ic_miscellaneous", "demo_add_mem"));
        preBirth.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "demo_add_mem"));


        List<SubCategories> birth = new ArrayList<>();
        birth.add(new SubCategories("Delivery moments", "ic_miscellaneous", "a11112"));
        birth.add(new SubCategories("Just born", "ic_miscellaneous", "a11112"));
        birth.add(new SubCategories("First baby cry", "ic_miscellaneous", "a11112"));
        birth.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "a11112"));


        List<SubCategories> infancy = new ArrayList<>();
        infancy.add(new SubCategories("Firsts", "ic_miscellaneous", "a11133"));
        infancy.add(new SubCategories("First Word", "ic_miscellaneous", "a11133"));
        infancy.add(new SubCategories("First Walk", "ic_miscellaneous", "a11133"));
        infancy.add(new SubCategories("First Birthday", "ic_miscellaneous", "a11133"));
        infancy.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "a11133"));


        List<SubCategories> earlyChildhood = new ArrayList<>();
        earlyChildhood.add(new SubCategories("First Swim", "ic_miscellaneous", "a111114"));
        earlyChildhood.add(new SubCategories("First Bike Ride", "ic_miscellaneous", "a111114"));
        earlyChildhood.add(new SubCategories("First KG Day", "ic_miscellaneous", "a111114"));
        earlyChildhood.add(new SubCategories("First Hobby", "ic_miscellaneous", "a111114"));
        earlyChildhood.add(new SubCategories("First Friend", "ic_miscellaneous", "a111114"));
        earlyChildhood.add(new SubCategories("First Handwritings", "ic_miscellaneous", "a111114"));
        earlyChildhood.add(new SubCategories("First drawings", "ic_miscellaneous", "a111114"));
        earlyChildhood.add(new SubCategories("KG Moments", "ic_miscellaneous", "a111114"));
        earlyChildhood.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "a111114"));


        List<SubCategories> middleChildhood = new ArrayList<>();
        middleChildhood.add(new SubCategories("New Hobby", "ic_miscellaneous", "a111116"));
        middleChildhood.add(new SubCategories("New Sport", "ic_miscellaneous", "a111116"));
        middleChildhood.add(new SubCategories("New Language", "ic_miscellaneous", "a111116"));
        middleChildhood.add(new SubCategories("New Bicycle", "ic_miscellaneous", "a111116"));
        middleChildhood.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "a111116"));

        List<SubCategories> lateChildhood = new ArrayList<>();
        lateChildhood.add(new SubCategories("New Hobby", "ic_miscellaneous", "a11115"));
        lateChildhood.add(new SubCategories("New Sport", "ic_miscellaneous", "a11115"));
        lateChildhood.add(new SubCategories("New Language", "ic_miscellaneous", "a11115"));
        lateChildhood.add(new SubCategories("New Bicycle", "ic_miscellaneous", "a11115"));
        lateChildhood.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "a11115"));

        List<SubCategories> adolescence = new ArrayList<>();
        adolescence.add(new SubCategories("New Hobby", "ic_miscellaneous", "a17"));
        adolescence.add(new SubCategories("New Sport", "ic_miscellaneous", "a17"));
        adolescence.add(new SubCategories("New Language", "ic_miscellaneous", "a17"));
        adolescence.add(new SubCategories("New Bicycle", "ic_miscellaneous", "a17"));
        adolescence.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "a17"));

        List<SubCategories> earlyAdulthood = new ArrayList<>();
        earlyAdulthood.add(new SubCategories("New Hobby", "ic_miscellaneous", "a18"));
        earlyAdulthood.add(new SubCategories("New Sport", "ic_miscellaneous", "a18"));
        earlyAdulthood.add(new SubCategories("New Language", "ic_miscellaneous", "a18"));
        earlyAdulthood.add(new SubCategories("New Bicycle", "ic_miscellaneous", "a18"));
        earlyAdulthood.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "a18"));

        List<SubCategories> midlife = new ArrayList<>();
        midlife.add(new SubCategories("New Hobby", "ic_miscellaneous", "a19"));
        midlife.add(new SubCategories("New Sport", "ic_miscellaneous", "a19"));
        midlife.add(new SubCategories("New Language", "ic_miscellaneous", "a19"));
        midlife.add(new SubCategories("New Bicycle", "ic_miscellaneous", "a19"));
        midlife.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "a19"));

        List<SubCategories> matureAdulthood = new ArrayList<>();
        matureAdulthood.add(new SubCategories("New Hobby", "ic_miscellaneous", "a110"));
        matureAdulthood.add(new SubCategories("New Sport", "ic_miscellaneous", "a110"));
        matureAdulthood.add(new SubCategories("New Language", "ic_miscellaneous", "a110"));
        matureAdulthood.add(new SubCategories("New Bicycle", "ic_miscellaneous", "a110"));
        matureAdulthood.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "a110"));

        List<SubCategories> lateAdulthood = new ArrayList<>();
        lateAdulthood.add(new SubCategories("New Clothes", "ic_miscellaneous", "a111"));
        lateAdulthood.add(new SubCategories("New Friend", "ic_miscellaneous", "a111"));
        lateAdulthood.add(new SubCategories("New Company", "ic_miscellaneous", "a111"));
        lateAdulthood.add(new SubCategories("Miscellaneous", "ic_miscellaneous", "a111"));

        List<SubCategories> fullLifeAlbum = new ArrayList<>();
        fullLifeAlbum.add(new SubCategories("Navigate", "ic_miscellaneous", "a112"));
        fullLifeAlbum.add(new SubCategories("Transfer your album to one of your family", "ic_miscellaneous", "a112"));


        list.add(new Categories("Pre Birth", "Potentiality", "layer_2", null, preBirth));
        list.add(new Categories("Birth", "Hope", "a2", null, birth));
        list.add(new Categories("Infancy", "Vitality", "a3", "0-3", infancy));
        list.add(new Categories("Early Childhood", "Playfulness", "a4", "3-6", earlyChildhood));
        list.add(new Categories("Middle Childhood", "Imagination", "a5", "6-8", middleChildhood));
        list.add(new Categories("Late Childhood", "Ingenuity", "a6", "9-11", lateChildhood));
        list.add(new Categories("Adolescence", "Passion", "a7", "12-20", adolescence));
        list.add(new Categories("Early Adulthood", "Enterprise", "a8", "20-35", earlyAdulthood));
        list.add(new Categories("Midlife", "Contemplation", "a9", "35-50", midlife));
        list.add(new Categories("Mature Adulthood", "Benevolence", "a10", "50-80", matureAdulthood));
        list.add(new Categories("Late Adulthood", "Wisdom", "a11", "80+", lateAdulthood));
        list.add(new Categories("Full Life Album", null, "a12", null, fullLifeAlbum));

        getDataBinder().recyclerView.setHasFixedSize(true);
        getDataBinder().recyclerView.setLayoutManager(new LinearLayoutManager(getActivityG(), LinearLayoutManager.VERTICAL, false));
        memoryCategoriesAdapter = new MemoryCategoriesAdapter(list, getActivityG(), getPresenter());
        memoryCategoriesAdapter.setShouldLoadMore(false);
        getDataBinder().recyclerView.setAdapter(memoryCategoriesAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_notifications, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void goToAddMemories(Categories categories) {
        Intent intent = new Intent(getActivityG(), AddMemoryActivity.class);
        intent.putExtra(Constants.Extras.DATA, categories);
        intent.putExtra(Constants.Extras.IS_MEMORY, getArguments().getBoolean(Constants.Extras.IS_MEMORY));
        getActivity().startActivityForResult(intent, Constants.RequestCode.ADD_MEMORY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notifications:
                NotificationActivity.start(getActivityG());
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
