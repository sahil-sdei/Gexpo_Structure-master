package ggn.home.help.features.internal.base;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ggn.home.help.R;
import ggn.home.help.features.internal.base.contract.Presentable;
import ggn.home.help.features.internal.base.contract.Viewable;
import ggn.home.help.utils.SharedPrefHelper;


public abstract class BaseActivity<B extends ViewDataBinding, T extends Presentable> extends AppCompatActivity implements Viewable<T> {
    protected T presenter;
    protected B binding;

    private ProgressDialog progressDialog;
    private CharSequence progressTitle = "Loading";
    private CharSequence progressMessage = "";
    public TextView toolbarText;

    public Toolbar toolbar;
    public Bundle savedInstanceState;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onResume();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPause() {
        getPresenter().onPause();
        super.onPause();
    }

    /**
     * @param title set title at the center of toolbar using a Text View.
     *              and also show back button.
     */
    public void setupToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

        toolbarText = toolbar.findViewById(R.id.toolbar_title);
        if (toolbarText != null) {
            toolbarText.setText(title);
        }
    }

    public TextView getTootlbarTextView() {
        if (toolbarText != null)
            return toolbarText;
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(setLayoutId());
        this.savedInstanceState = savedInstanceState;
        binding = DataBindingUtil.setContentView(this, setLayoutId());

        onCreateActivityG();
        getPresenter().onViewCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStop() {
        getPresenter().onStop();
        super.onStop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        getPresenter().detachView();
        presenter = null;
        super.onDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayError(String message) {
        if (getParentView() != null) {
            Snackbar snackbar = Snackbar.make(getParentView(), message, Snackbar.LENGTH_LONG);
            TextView tv = (TextView) (snackbar.getView()).findViewById(android.support.design.R.id.snackbar_text);
//            Typeface font = Typeface.createFromAsset(getActivityG().getAssets(), "fonts/vegur_medium.otf");
//            tv.setTypeface(font);
            tv.setMaxLines(4);
            snackbar.show();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showLoading() {
        // no-op by default
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivityG());
            progressDialog.setTitle(progressTitle);
            progressDialog.setMessage(progressMessage);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    /**
     * show loading with a custom message.
     *
     * @param title
     * @param message
     */
    public void showLoading(String title, String message) {
        progressTitle = title;
        progressMessage = message;
        showLoading();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getPresenter() {
        return presenter;
    }


    /**
     * @return data binding class according to the layout attached.
     */
    public B getDataBinder() {
        return binding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void injectPresenter(T presenter) {
        this.presenter = presenter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SharedPrefHelper getLocalData() {
        return new SharedPrefHelper(getApplicationContext());
    }

    /**
     * Abstract method to get the xml layout for the activity,
     * this is used in {@link android.app.Activity#setContentView(int)} method.
     *
     * @return layout id -int.
     */
    protected abstract int setLayoutId();

    /**
     * View returned by this method is used to display SnackBar.
     *
     * @return parent view of the activity.
     */
    protected View getParentView() {
        return findViewById(android.R.id.content);
    }

    /**
     * injectPresenter( @link Presentable);
     * attachView(this);
     * both of the above methods should be inside this method.
     * After that,put all the stuff you need in your activity on create.
     */
    protected abstract void onCreateActivityG();
}
