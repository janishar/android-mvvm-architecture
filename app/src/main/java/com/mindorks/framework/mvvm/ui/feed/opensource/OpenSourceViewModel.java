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

package com.mindorks.framework.mvvm.ui.feed.opensource;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.OpenSourceResponse;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class OpenSourceViewModel extends BaseViewModel<OpenSourceNavigator> {

    private final ObservableArrayList<OpenSourceItemViewModel> openSourceItemViewModels = new ObservableArrayList<>();
    private final MutableLiveData<List<OpenSourceItemViewModel>> openSourceItemsLiveData;


    public OpenSourceViewModel(DataManager dataManager,
                               SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        openSourceItemsLiveData = new MutableLiveData<>();
        fetchRepos();
    }

    public void fetchRepos() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getOpenSourceApiCall()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<OpenSourceResponse>() {
                    @Override
                    public void accept(@NonNull OpenSourceResponse openSourceResponse)
                            throws Exception {
                        if (openSourceResponse != null && openSourceResponse.getData() != null) {
                            openSourceItemsLiveData.setValue(getViewModelList(openSourceResponse.getData()));
                        }
                        setIsLoading(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                    }
                }));
    }


    public List<OpenSourceItemViewModel> getViewModelList(List<OpenSourceResponse.Repo> repoList) {
        ArrayList<OpenSourceItemViewModel> openSourceItemViewModels = new ArrayList<>();
        for (int i = 0; i < repoList.size(); i++) {
            openSourceItemViewModels.add(new OpenSourceItemViewModel(
                    repoList.get(i).getCoverImgUrl(), repoList.get(i).getTitle(),
                    repoList.get(i).getDescription(), repoList.get(i).getProjectUrl()));
        }
        return openSourceItemViewModels;
    }

    public MutableLiveData<List<OpenSourceItemViewModel>> getOpenSourceRepos() {
        return openSourceItemsLiveData;
    }

    public void addOpenSourceItemsToList(List<OpenSourceItemViewModel> openSourceItems) {
        openSourceItemViewModels.clear();
        openSourceItemViewModels.addAll(openSourceItems);
    }

    public ObservableArrayList<OpenSourceItemViewModel> getOpenSourceItemViewModels() {
        return openSourceItemViewModels;
    }
}
