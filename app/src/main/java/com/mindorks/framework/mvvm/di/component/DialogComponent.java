package com.mindorks.framework.mvvm.di.component;

import com.mindorks.framework.mvvm.di.module.DialogModule;
import com.mindorks.framework.mvvm.di.scope.DialogScope;
import com.mindorks.framework.mvvm.ui.main.rating.RateUsDialog;

import dagger.Component;

/*
 * Author: rotbolt
 */

@DialogScope
@Component(modules = DialogModule.class, dependencies = AppComponent.class)
public interface DialogComponent {

    void inject(RateUsDialog dialog);

}
