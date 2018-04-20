package com.football.server.api.ui.user;

import com.androidnetworking.error.ANError;
import com.football.server.api.data.DataManager;
import com.football.server.api.data.network.model.OpenSourceResponse;
import com.football.server.api.data.network.model.UserResponse;
import com.football.server.api.ui.base.BasePresenter;
import com.football.server.api.ui.base.MvpView;
import com.football.server.api.utils.rx.SchedulerProvider;
import javax.inject.Inject;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class UserPresenter<V extends UserMvpView> extends BasePresenter<V> implements UserMvpPresenter<V> {

    private static final String TAG = "FeedPresenter";

    @Inject
    public UserPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getUserApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<UserResponse>() {
                    @Override
                    public void accept(@NonNull UserResponse userResponse)
                            throws Exception {
                        if (userResponse != null && userResponse.getData() != null) {
                            getMvpView().updateRepo(userResponse.getData());
                        }
                        getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }
}
