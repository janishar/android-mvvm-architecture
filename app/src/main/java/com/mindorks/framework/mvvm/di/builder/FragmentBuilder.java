package com.mindorks.framework.mvvm.di.builder;


import com.mindorks.framework.mvvm.ui.about.AboutFragment;
import com.mindorks.framework.mvvm.ui.about.AboutFragmentModule;
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogFragment;
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogFragmentModule;
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceFragment;
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Project : android-mvvm-architecture-mindorks
 * Created by: Harsh Dalwadi - Senior Software Engineer
 * Created Date: 09-Mar-19
 */
@Module
abstract public class FragmentBuilder {

    @ContributesAndroidInjector(modules = BlogFragmentModule.class)
    abstract BlogFragment bindBlogFragment();

    @ContributesAndroidInjector(modules = OpenSourceFragmentModule.class)
    abstract OpenSourceFragment provideOpenSourceFragmentFactory();

    @ContributesAndroidInjector(modules = AboutFragmentModule.class)
    abstract AboutFragment provideAboutFragmentFactory();
}
