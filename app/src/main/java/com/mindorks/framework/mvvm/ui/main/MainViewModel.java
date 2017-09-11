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

package com.mindorks.framework.mvvm.ui.main;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.LogoutResponse;
import com.mindorks.framework.mvvm.data.model.others.QuestionCardData;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by amitshekhar on 07/07/17.
 */

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public final ObservableField<String> appVersion = new ObservableField<>();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> userEmail = new ObservableField<>();
    public final ObservableField<String> userProfilePicUrl = new ObservableField<>();
    public ObservableArrayList<QuestionCardData> questionDataList = new ObservableArrayList<>();

    public int mAction = NO_ACTION;
    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;

    public MainViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }

    public void onNavMenuCreated() {

        final String currentUserName = getDataManager().getCurrentUserName();
        if (currentUserName != null && !currentUserName.isEmpty()) {
            userName.set(currentUserName);
        }

        final String currentUserEmail = getDataManager().getCurrentUserEmail();
        if (currentUserEmail != null && !currentUserEmail.isEmpty()) {
            userEmail.set(currentUserEmail);
        }

        final String profilePicUrl = getDataManager().getCurrentUserProfilePicUrl();
        if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
            userProfilePicUrl.set(profilePicUrl);
        }
    }

    public void onViewInitialized() {
        if (mAction == NO_ACTION) {
            getCompositeDisposable().add(getDataManager()
                    .getQuestionCardData()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<List<QuestionCardData>>() {
                        @Override
                        public void accept(List<QuestionCardData> questionList) throws Exception {
                            if (questionList != null) {
                                mAction = ACTION_ADD_ALL;
                                questionDataList.addAll(questionList);
                            }
                        }
                    }));
        } else {
            ArrayList<QuestionCardData> arrayList = (ArrayList<QuestionCardData>) questionDataList.clone();
            questionDataList.clear();
            mAction = ACTION_ADD_ALL;
            questionDataList.addAll(arrayList);
        }
    }

    public void onCardExhausted() {
        getCompositeDisposable().add(getDataManager()
                .getQuestionCardData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<QuestionCardData>>() {
                    @Override
                    public void accept(List<QuestionCardData> questionList) throws Exception {
                        if (questionList != null) {
                            mAction = ACTION_ADD_ALL;
                            questionDataList.clear();
                            questionDataList.addAll(questionList);
                        }
                    }
                }));
    }

    public void logout() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().doLogoutApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LogoutResponse>() {
                    @Override
                    public void accept(LogoutResponse response) throws Exception {
                        getDataManager().setUserAsLoggedOut();
                        setIsLoading(false);
                        getNavigator().openLoginActivity();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                    }
                }));
    }

    public void removeQuestionCard() {
        mAction = ACTION_DELETE_SINGLE;
        questionDataList.remove(0);
    }
}
