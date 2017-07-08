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

import android.databinding.ObservableField;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by amitshekhar on 07/07/17.
 */

public class MainViewModel extends BaseViewModel {

    public final ObservableField<String> value = new ObservableField<>();

    public MainViewModel(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    public void setText(String text) {
        this.value.set(text);
    }

}
