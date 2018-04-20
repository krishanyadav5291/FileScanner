package com.kyadav.filescanner.base;

import android.support.annotation.StringRes;

/**
 * Created by kyadav on 18/04/18.
 */

public interface BaseView {

    void showLoading();

    void hideLoading();

    void onError(String message);

    void onError(@StringRes int resId);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void showAlertDialog(String title,String message);

    void showAlertDialog(@StringRes int title,@StringRes int message);
}
