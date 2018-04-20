package com.kyadav.filescanner.base;

/**
 * Created by kyadav on 18/04/18.
 */

public interface MvpPresenter<V extends BaseView> {

    void onViewAttach(V view);

    void onViewDetach();
}
