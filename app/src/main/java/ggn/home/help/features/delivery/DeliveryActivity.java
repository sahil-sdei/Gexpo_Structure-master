package ggn.home.help.features.delivery;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import ggn.home.help.R;
import ggn.home.help.databinding.ActivityDeliveryBinding;
import ggn.home.help.features.dashboard.export.ExportData;
import ggn.home.help.features.internal.base.BaseActivity;
import ggn.home.help.utils.Constants;

public class DeliveryActivity extends BaseActivity<ActivityDeliveryBinding, DeliveryPresenter> implements DeliveryView, View.OnClickListener {

    private ExportData exportDataObj;

    public static void start(Context context, ExportData exportData) {
        Intent starter = new Intent(context, DeliveryActivity.class);
        starter.putExtra(Constants.Extras.DATA, exportData);
        context.startActivity(starter);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_delivery;
    }

    @Override
    protected void onCreateActivityG() {
        injectPresenter(new DeliveryPresenter());
        getPresenter().attachView(this);
    }

    @Override
    public Context getActivityG() {
        return DeliveryActivity.this;
    }

    @Override
    public void initViews() {
        setupToolbar("Delivery");

        exportDataObj = (ExportData) getIntent().getSerializableExtra(Constants.Extras.DATA);

        if (exportDataObj.type == 1) {
            getDataBinder().textViewProduct.setText("Memoreeta high quality colored paper album");
            getDataBinder().textViewPrice.setText("$59");
            getDataBinder().textViewTitle.setText("Full Life Album");
            getDataBinder().textViewPriceQuantity.setText("$59");
            getDataBinder().textViewTotal.setText("$64");
        } else {
            getDataBinder().textViewProduct.setText("Memoreeta high quality colored paper photo");
            getDataBinder().textViewPrice.setText("$10");
            getDataBinder().textViewTitle.setText("One Selected Photo");
            getDataBinder().textViewPriceQuantity.setText("$10 x " + exportDataObj.numberPhotos);
            int totalPrice = (exportDataObj.numberPhotos * 10) + 5;
            getDataBinder().textViewTotal.setText("$" + totalPrice);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
