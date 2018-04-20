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

package com.football.server.api.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @Expose
    @SerializedName("statusCode")
    private String statusCode;

    @Expose
    @SerializedName("success")
    private boolean success;

    @Expose
    @SerializedName("data")
    private List<User> data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean getMessage() {
        return success;
    }

    public void setMessage(boolean success) {
        this.success = success;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserResponse)) return false;

        UserResponse that = (UserResponse) o;

        if (!statusCode.equals(that.statusCode)) return false;
        if (!success) return false;
        return data != null ? data.equals(that.data) : that.data == null;

    }

    @Override
    public int hashCode() {
        int result = statusCode.hashCode();
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public static class User {

        @Expose
        @SerializedName("id")
        private String id;

        @Expose
        @SerializedName("email")
        private String email;

        @Expose
        @SerializedName("admin")
        private boolean admin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User)) return false;

            User user = (User) o;

            if (!id.equals(user.id)) return false;
            if (!email.equals(user.email)) return false;
            if (admin != user.admin) return false;
            return true;

        }

        @Override
        public int hashCode() {
            int result = id.hashCode();
            result = 31 * result + email.hashCode();
            return result;
        }
    }
}
