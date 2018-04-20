package com.football.server.api.ui.splash;

import com.auth0.android.jwt.JWT;
import com.football.server.api.R;
import com.football.server.api.data.DataManager;
import com.football.server.api.ui.base.BasePresenter;
import com.football.server.api.utils.AppLogger;
import com.football.server.api.utils.rx.SchedulerProvider;

import java.util.Date;

import javax.inject.Inject;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

        getMvpView().startSyncService();

        getCompositeDisposable().add(getDataManager()
                .seedDatabaseQuestions()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .concatMap(new Function<Boolean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(Boolean aBoolean) throws Exception {
                        return getDataManager().seedDatabaseOptions();
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }
                        decideNextActivity();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }
                        getMvpView().onError(R.string.some_error);
                        decideNextActivity();
                    }
                }));


    }


    private void decideNextActivity() {
        String accessToken = getDataManager().getAccessToken();
        AppLogger.d("accessToken : " + accessToken);
        if(accessToken != null && accessToken != "") {
            accessToken = accessToken.replace("Bearer ", "");
            JWT jwt = new JWT(accessToken);
            boolean isExpired = jwt.isExpired(3600); // 10 seconds leeway
            AppLogger.d("isExpired : " + isExpired);
            if (isExpired) {
                getDataManager().setUserAsLoggedOut();
            }
        }
        else
            getDataManager().setUserAsLoggedOut();

        if (getDataManager().getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()) {
            getMvpView().openLoginActivity();
        } else {
            getMvpView().openMainActivity();
        }
    }
}
