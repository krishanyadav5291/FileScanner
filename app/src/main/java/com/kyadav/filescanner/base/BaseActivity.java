package com.kyadav.filescanner.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kyadav.filescanner.R;
import com.kyadav.filescanner.application.ScannerApplication;
import com.kyadav.filescanner.di.component.ActivityComponent;
import com.kyadav.filescanner.di.component.DaggerActivityComponent;
import com.kyadav.filescanner.di.module.ActivityModule;
import com.kyadav.filescanner.util.CommonUtils;

/**
 * Created by kyadav on 18/04/18.
 */

public class BaseActivity extends AppCompatActivity implements BaseView {

    private ActivityComponent activityComponent;

    private ProgressDialog mProgressDialog;

    TextView toolbarTitleTextView;
    public Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initToolBar();

    }


    protected void onCreate(@Nullable Bundle savedInstanceState, int layoutResId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);
        initToolBar();
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((ScannerApplication) getApplication()).getApplicationComponent())
                .build();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);

    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }

    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);

        } else {
            showSnackBar(getString(R.string.some_error));

        }

    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public void showAlertDialog(String title, String message) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setCancelable(false);
        alert.setMessage(message);
        alert.setPositiveButton(getString(R.string.global_str_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    @Override
    public void showAlertDialog(int title, int message) {
        showAlertDialog(getString(title), getString(message));
    }

    protected void findViewById() {
        // Override this methid for finding Various Views in layout file.
    }

    protected void setOnClickListener() {
        //Over ride this method for making the listeners associated with various views.
    }

    private void initToolBar() {
        try {
            mToolbar = (Toolbar) findViewById(R.id.base_top_toolbar);
            toolbarTitleTextView = (TextView) mToolbar.findViewById(R.id.toolbar_title);


            if (mToolbar != null) {
                setSupportActionBar(mToolbar);

                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // manageBackButtonClicked();
                    }
                });
                //getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);


            }//if (mToolbar != null)
        } catch (Exception e) {
            //Utility.printStackTrace("Exception at initToolbar()\n", e);
        }
    }

    protected void setScreenTitle(String title) {
        if (toolbarTitleTextView != null && getSupportActionBar() != null) {
            toolbarTitleTextView.setVisibility(View.VISIBLE);//This done explicitly for handling the situation when the toolbarTitleTextView is not in view//
            toolbarTitleTextView.setText(title);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}
