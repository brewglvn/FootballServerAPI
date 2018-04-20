package com.football.server.api.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.football.server.api.R;
import com.football.server.api.data.network.model.OpenSourceResponse;
import com.football.server.api.data.network.model.UserResponse;
import com.football.server.api.di.component.ActivityComponent;
import com.football.server.api.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserFragment extends BaseFragment implements UserMvpView, UserAdapter.Callback {

        public static final String TAG = "UserFragment";

        @Inject
        UserMvpPresenter<UserMvpView> mPresenter;

        @Inject
        UserAdapter mUserAdapter;

        @Inject
        LinearLayoutManager mLayoutManager;

        @BindView(R.id.user_recycler_view)
        RecyclerView mRecyclerView;

        public static UserFragment newInstance() {
            Bundle args = new Bundle();
            UserFragment fragment = new UserFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_user, container, false);

            ActivityComponent component = getActivityComponent();
            if (component != null) {
                component.inject(this);
                setUnBinder(ButterKnife.bind(this, view));
                mPresenter.onAttach(this);
                mUserAdapter.setCallback(this);
            }
            return view;
        }

        @Override
        protected void setUp(View view) {
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mUserAdapter);

            mPresenter.onViewPrepared();
        }

        @Override
        public void onRepoEmptyViewRetryClick() {

        }

        @Override
        public void updateRepo(List<UserResponse.User> userList) {
            mUserAdapter.addItems(userList);
        }

        @OnClick(R.id.nav_back_btn)
        void onNavBackClick() {
            getBaseActivity().onFragmentDetached(TAG);
        }

        @Override
        public void onDestroyView() {
            mPresenter.onDetach();
            super.onDestroyView();
        }
    }
