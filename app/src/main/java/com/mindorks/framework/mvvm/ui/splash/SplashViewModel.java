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

package com.mindorks.framework.mvvm.ui.splash;

import com.mindorks.framework.mvvm.data.UserSessionRepository;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public SplashViewModel(UserSessionRepository UserSessionRepository, SchedulerProvider schedulerProvider) {
        super(UserSessionRepository, schedulerProvider);
    }

    public void startSeeding() {
        getCompositeDisposable().add(getRepository()
                .seedDatabaseQuestions()
                .flatMap(aBoolean -> getRepository().seedDatabaseOptions())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                    decideNextActivity();
                }, throwable -> {
                    decideNextActivity();
                }));
    }

    private void decideNextActivity() {
        if (getRepository().getCurrentUserLoggedInMode() == UserSessionRepository.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()) {
            getNavigator().openLoginActivity();
        } else {
            getNavigator().openMainActivity();
        }
    }
}
