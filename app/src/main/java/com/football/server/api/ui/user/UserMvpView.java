package com.football.server.api.ui.user;

import com.football.server.api.data.network.model.UserResponse;
import com.football.server.api.ui.base.MvpView;

import java.util.List;

public interface UserMvpView extends MvpView {
    void updateRepo(List<UserResponse.User> repoList);
}
