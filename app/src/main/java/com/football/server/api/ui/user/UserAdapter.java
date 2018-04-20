/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.football.server.api.ui.user;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.football.server.api.R;
import com.football.server.api.data.network.model.OpenSourceResponse;
import com.football.server.api.data.network.model.UserResponse;
import com.football.server.api.ui.base.BaseViewHolder;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<UserResponse.User> mUserResponseList;

    public UserAdapter(List<UserResponse.User> userResponseList) {
        mUserResponseList = userResponseList;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_view, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mUserResponseList != null && mUserResponseList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mUserResponseList != null && mUserResponseList.size() > 0) {
            return mUserResponseList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<UserResponse.User> userList) {
        mUserResponseList.addAll(userList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onRepoEmptyViewRetryClick();
    }

    public class ViewHolder extends BaseViewHolder {

        @BindView(R.id.cover_image_view)
        ImageView coverImageView;

        @BindView(R.id.ids)
        TextView idsTextView;

        @BindView(R.id.email)
        TextView emailTextView;

        @BindView(R.id.password)
        TextView passwordTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            idsTextView.setText("");
            emailTextView.setText("");
            passwordTextView.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final UserResponse.User user = mUserResponseList.get(position);

            /*if (repo.getCoverImgUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(repo.getCoverImgUrl())
                        .asBitmap()
                        .centerCrop()
                        .into(coverImageView);
            }*/

            coverImageView.setBackgroundResource(R.drawable.ic_rabbit);

            if (user.getId() != null) {
                idsTextView.setText(user.getId());
            }

            if (user.getEmail() != null) {
                emailTextView.setText(user.getEmail() + " is " + (user.isAdmin()? "admin" : "member"));
                passwordTextView.setText(user.getEmail());
            }

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (repo.getProjectUrl() != null) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse(repo.getProjectUrl()));
                        itemView.getContext().startActivity(intent);
                    }
                }
            });*/
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.btn_retry)
        Button retryButton;

        @BindView(R.id.tv_message)
        TextView messageTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

        @OnClick(R.id.btn_retry)
        void onRetryClick() {
            if (mCallback != null)
                mCallback.onRepoEmptyViewRetryClick();
        }
    }
}
