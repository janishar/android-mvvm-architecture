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

package com.mindorks.framework.mvvm.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.api.BlogResponse;
import com.mindorks.framework.mvvm.data.model.api.OpenSourceResponse;
import com.mindorks.framework.mvvm.di.ActivityContext;
import com.mindorks.framework.mvvm.di.PerActivity;
import com.mindorks.framework.mvvm.ui.about.AboutViewModel;
import com.mindorks.framework.mvvm.ui.feed.FeedPagerAdapter;
import com.mindorks.framework.mvvm.ui.feed.FeedViewModel;
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogAdapter;
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogViewModel;
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceAdapter;
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceViewModel;
import com.mindorks.framework.mvvm.ui.login.LoginViewModel;
import com.mindorks.framework.mvvm.ui.main.MainViewModel;
import com.mindorks.framework.mvvm.ui.main.rating.RateUsViewModel;
import com.mindorks.framework.mvvm.ui.splash.SplashViewModel;
import com.mindorks.framework.mvvm.utils.rx.AppSchedulerProvider;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by amitshekhar on 07/07/17.
 */
@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashViewModel provideSplashViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider,
                                           CompositeDisposable compositeDisposable) {
        return new SplashViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Provides
    @PerActivity
    LoginViewModel provideLoginViewModel(DataManager dataManager,
                                         SchedulerProvider schedulerProvider,
                                         CompositeDisposable compositeDisposable) {
        return new LoginViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Provides
    @PerActivity
    MainViewModel provideMainViewModel(DataManager dataManager,
                                       SchedulerProvider schedulerProvider,
                                       CompositeDisposable compositeDisposable) {
        return new MainViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Provides
    AboutViewModel provideAboutViewModel(DataManager dataManager,
                                         SchedulerProvider schedulerProvider,
                                         CompositeDisposable compositeDisposable) {
        return new AboutViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Provides
    RateUsViewModel provideRateUsViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider,
                                           CompositeDisposable compositeDisposable) {
        return new RateUsViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Provides
    BlogViewModel provideBlogViewModel(DataManager dataManager,
                                       SchedulerProvider schedulerProvider,
                                       CompositeDisposable compositeDisposable) {
        return new BlogViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Provides
    OpenSourceViewModel provideOpenSourceViewModel(DataManager dataManager,
                                                   SchedulerProvider schedulerProvider,
                                                   CompositeDisposable compositeDisposable) {
        return new OpenSourceViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

    @Provides
    FeedPagerAdapter provideFeedPagerAdapter(AppCompatActivity activity) {
        return new FeedPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    BlogAdapter provideBlogAdapter() {
        return new BlogAdapter(new ArrayList<BlogResponse.Blog>());
    }

    @Provides
    OpenSourceAdapter provideOpenSourceAdapter() {
        return new OpenSourceAdapter();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }
    @Provides
    @PerActivity
    FeedViewModel provideFeedViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider,
                                           CompositeDisposable compositeDisposable) {
        return new FeedViewModel(dataManager, schedulerProvider, compositeDisposable);
    }

}
