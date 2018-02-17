/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by amitshekhar on 07/07/17.
 */

public final class LoginResponse {

    @Expose
    @SerializedName("access_token")
    private String accessToken;

    @Expose
    @SerializedName("fb_profile_pic_url")
    private String fbProfilePicUrl;

    @Expose
    @SerializedName("google_profile_pic_url")
    private String googleProfilePicUrl;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("server_profile_pic_url")
    private String serverProfilePicUrl;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    @Expose
    @SerializedName("email")
    private String userEmail;

    @Expose
    @SerializedName("user_id")
    private Long userId;

    @Expose
    @SerializedName("user_name")
    private String userName;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        LoginResponse that = (LoginResponse) object;

        if (statusCode != null ? !statusCode.equals(that.statusCode) : that.statusCode != null) {
            return false;
        }
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (accessToken != null ? !accessToken.equals(that.accessToken) : that.accessToken != null) {
            return false;
        }
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
            return false;
        }
        if (userEmail != null ? !userEmail.equals(that.userEmail) : that.userEmail != null) {
            return false;
        }
        if (serverProfilePicUrl != null ? !serverProfilePicUrl.equals(that.serverProfilePicUrl)
                : that.serverProfilePicUrl != null) {
            return false;
        }
        if (fbProfilePicUrl != null ? !fbProfilePicUrl.equals(that.fbProfilePicUrl)
                : that.fbProfilePicUrl != null) {
            return false;
        }
        if (googleProfilePicUrl != null ? !googleProfilePicUrl.equals(that.googleProfilePicUrl)
                : that.googleProfilePicUrl != null) {
            return false;
        }
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = statusCode != null ? statusCode.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userEmail != null ? userEmail.hashCode() : 0);
        result = 31 * result + (serverProfilePicUrl != null ? serverProfilePicUrl.hashCode() : 0);
        result = 31 * result + (fbProfilePicUrl != null ? fbProfilePicUrl.hashCode() : 0);
        result = 31 * result + (googleProfilePicUrl != null ? googleProfilePicUrl.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getFbProfilePicUrl() {
        return fbProfilePicUrl;
    }

    public String getGoogleProfilePicUrl() {
        return googleProfilePicUrl;
    }

    public String getMessage() {
        return message;
    }

    public String getServerProfilePicUrl() {
        return serverProfilePicUrl;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
