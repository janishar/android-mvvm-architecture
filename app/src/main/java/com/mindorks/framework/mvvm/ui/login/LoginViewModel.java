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

package com.mindorks.framework.mvvm.ui.login;

import android.content.Intent;
import com.mindorks.framework.mvvm.data.UserSessionRepository;
import com.mindorks.framework.mvvm.google.SingleLiveEvent;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

/**
 * Created by amitshekhar on 08/07/17.
 */
public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private UserSessionRepository repository;

    SingleLiveEvent<Intent> launchSignInEvent;

    public LoginViewModel(UserSessionRepository userSessionRepository, SchedulerProvider schedulerProvider) {
        super(userSessionRepository, schedulerProvider);

        repository = userSessionRepository;

        launchSignInEvent = new SingleLiveEvent<>();
    }

    public void buildSignInIntent() {
        launchSignInEvent.setValue(repository.getSignInInent());
    }

    //public void login(String email, String password) {
    //    setIsLoading(true);
    //    getCompositeDisposable().add(getRepository()
    //            .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(email, password))
    //            .doOnSuccess(response -> getRepository()
    //                    .updateUserInfo(
    //                            response.getAccessToken(),
    //                            response.getUserId(),
    //                            UserSessionRepository.LoggedInMode.LOGGED_IN_MODE_FIREBASE_AUTH_UI,
    //                            response.getUserName(),
    //                            response.getUserEmail(),
    //                            response.getGoogleProfilePicUrl()))
    //            .subscribeOn(getSchedulerProvider().io())
    //            .observeOn(getSchedulerProvider().ui())
    //            .subscribe(response -> {
    //                setIsLoading(false);
    //                getNavigator().openMainActivity();
    //            }, throwable -> {
    //                setIsLoading(false);
    //                getNavigator().handleError(throwable);
    //            }));
    //}

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void updateUserSession() {
        repository.updateUserInfo();
        getNavigator().openMainActivity();
    }
}
