package com.mindorks.framework.mvvm.di.component;

import com.mindorks.framework.mvvm.di.module.FragmentModule;
import com.mindorks.framework.mvvm.di.scope.FragmentScope;
import com.mindorks.framework.mvvm.ui.about.AboutFragment;
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogFragment;
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceFragment;

import dagger.Component;

/*
 * Author: rotbolt
 */

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(BlogFragment fragment);

    void inject(OpenSourceFragment fragment);

    void inject(AboutFragment fragment);
}
