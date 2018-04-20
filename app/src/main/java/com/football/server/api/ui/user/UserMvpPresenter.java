package com.football.server.api.ui.user;

import com.football.server.api.ui.base.MvpPresenter;
import com.football.server.api.ui.base.MvpView;


public interface UserMvpPresenter<V extends MvpView> extends MvpPresenter<V> {
    void onViewPrepared();
}
