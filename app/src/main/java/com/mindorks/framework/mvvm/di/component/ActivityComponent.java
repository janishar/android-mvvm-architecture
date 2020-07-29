package com.mindorks.framework.mvvm.di.component;

import com.mindorks.framework.mvvm.di.module.ActivityModule;
import com.mindorks.framework.mvvm.di.scope.ActivityScope;
import com.mindorks.framework.mvvm.ui.feed.FeedActivity;
import com.mindorks.framework.mvvm.ui.login.LoginActivity;
import com.mindorks.framework.mvvm.ui.main.MainActivity;
import com.mindorks.framework.mvvm.ui.splash.SplashActivity;

import dagger.Component;

/*
 * Author: rotbolt
 */

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(FeedActivity activity);

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(SplashActivity splashActivity);

}
