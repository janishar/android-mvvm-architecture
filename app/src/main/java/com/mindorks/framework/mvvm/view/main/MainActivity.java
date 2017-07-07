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

package com.mindorks.framework.mvvm.view.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mindorks.framework.mvvm.MvvmApp;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityMainBinding;
import com.mindorks.framework.mvvm.di.component.ActivityComponent;
import com.mindorks.framework.mvvm.di.component.DaggerActivityComponent;
import com.mindorks.framework.mvvm.di.module.ActivityModule;
import com.mindorks.framework.mvvm.viewmodel.main.MainViewModel;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Inject
    public MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((MvvmApp) getApplication()).getComponent())
                .build();

        mActivityComponent.inject(this);

        mainBinding.setViewmodel(mainViewModel);
    }

    @Override
    protected void onResume() {
        mainViewModel.setText("Amit Shekhar");
        super.onResume();
    }
}
