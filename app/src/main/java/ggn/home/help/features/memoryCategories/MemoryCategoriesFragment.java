package ggn.home.help.features.memoryCategories;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

import ggn.home.help.R;
import ggn.home.help.databinding.FragmentRecyclerViewBinding;
import ggn.home.help.features.addMemories.AddMemoryActivity;
import ggn.home.help.features.internal.base.BaseFragment;
import ggn.home.help.utils.Constants;


public class MemoryCategoriesFragment extends BaseFragment<FragmentRecyclerViewBinding, MemoryCategoriesPresenter> implements MemoryCategoriesView {

    private List<Categories> list;
    private MemoryCategoriesAdapter memoryCategoriesAdapter;

    public static MemoryCategoriesFragment newInstance() {
        MemoryCategoriesFragment activityFragment = new MemoryCategoriesFragment();
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
        preBirth.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        preBirth.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        preBirth.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> birth = new ArrayList<>();
        birth.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        birth.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        birth.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> infancy = new ArrayList<>();
        infancy.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        infancy.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        infancy.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> earlyChildhood = new ArrayList<>();
        earlyChildhood.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        earlyChildhood.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        earlyChildhood.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> middleChildhood = new ArrayList<>();
        middleChildhood.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        middleChildhood.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        middleChildhood.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> lateChildhood = new ArrayList<>();
        lateChildhood.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        lateChildhood.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        lateChildhood.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> adolescence = new ArrayList<>();
        adolescence.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        adolescence.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        adolescence.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> earlyAdulthood = new ArrayList<>();
        earlyAdulthood.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        earlyAdulthood.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        earlyAdulthood.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> midlife = new ArrayList<>();
        midlife.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        midlife.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        midlife.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> matureAdulthood = new ArrayList<>();
        matureAdulthood.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        matureAdulthood.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        matureAdulthood.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> lateAdulthood = new ArrayList<>();
        lateAdulthood.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        lateAdulthood.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        lateAdulthood.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));

        List<SubCategories> fullLifeAlbum = new ArrayList<>();
        fullLifeAlbum.add(new SubCategories("Pregnancy Moments", "demo_image_wall", "demo_add_mem"));
        fullLifeAlbum.add(new SubCategories("Baby Inside Me", "demo_image_wall", "demo_add_mem"));
        fullLifeAlbum.add(new SubCategories("Miscellaneous", "demo_image_wall", "demo_add_mem"));


        list.add(new Categories("Pre Birth", "Potientiality", "layer_2", null, preBirth));
        list.add(new Categories("Birth", "Hope", "layer_2", null, birth));
        list.add(new Categories("Infancy", "Vitality", "layer_2", "0-3", infancy));
        list.add(new Categories("Early Childhood", "Playfulness", "layer_2", "3-6", earlyChildhood));
        list.add(new Categories("Middle Childhood", "Imagination", "layer_2", "6-8", middleChildhood));
        list.add(new Categories("Late Childhood", "Ingenuity", "layer_2", "9-11", lateChildhood));
        list.add(new Categories("Adolescence", "Passion", "layer_2", "12-20", adolescence));
        list.add(new Categories("Early Adulthood", "Enterprise", "layer_2", "20-35", earlyAdulthood));
        list.add(new Categories("Midlife", "Contemplation", "layer_2", "35-50", midlife));
        list.add(new Categories("Mature Adulthood", "Benevolence", "layer_2", "50-80", matureAdulthood));
        list.add(new Categories("Late Adulthood", "Wisdom", "layer_2", "80+", lateAdulthood));
        list.add(new Categories("Full Life Album", null, "layer_2", null, fullLifeAlbum));

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
        getActivity().startActivityForResult(intent, Constants.RequestCode.ADD_MEMORY);
    }
}
