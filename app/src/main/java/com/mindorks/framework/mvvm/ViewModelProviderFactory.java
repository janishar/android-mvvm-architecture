package com.mindorks.framework.mvvm;

import androidx.annotation.NonNull;
import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

/**
 * Authors : jyotidubey, rotbolt
 */

@Singleton
public class ViewModelProviderFactory<T extends  ViewModel> extends ViewModelProvider.NewInstanceFactory {

    private final Class<T> viewModelClass;
    private final Supplier<T> viewModelSupplier;

    public ViewModelProviderFactory(Class<T> viewModelClass, Supplier<T> viewModelSupplier){
        this.viewModelClass = viewModelClass;
        this.viewModelSupplier = viewModelSupplier;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(viewModelClass)){
            return (T) viewModelSupplier.get();
        }else {
            throw new IllegalArgumentException("Unknown Class name "+viewModelClass.getName());
        }
    }
}