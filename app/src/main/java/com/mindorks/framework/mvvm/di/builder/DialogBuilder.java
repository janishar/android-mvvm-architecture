package com.mindorks.framework.mvvm.di.builder;


import com.mindorks.framework.mvvm.ui.main.rating.RateUsDialog;
import com.mindorks.framework.mvvm.ui.main.rating.RateUsDialogModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Project : android-mvvm-architecture-mindorks
 * Created by: Harsh Dalwadi - Senior Software Engineer
 * Created Date: 09-Mar-19
 */
@Module
public abstract class DialogBuilder {


    @ContributesAndroidInjector(modules = RateUsDialogModule.class)
    abstract RateUsDialog provideRateUsDialogFactory();
}
