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

package com.mindorks.framework.mvvm.ui.main.rating;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.DialogRateUsBinding;
import com.mindorks.framework.mvvm.di.component.ActivityComponent;
import com.mindorks.framework.mvvm.ui.base.BaseDialog;

import javax.inject.Inject;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class RateUsDialog extends BaseDialog implements RateUsCallback {

    private static final String TAG = "RateUsDialog";

    @Inject
    RateUsViewModel mRateUsViewModel;

    public static RateUsDialog newInstance() {
        RateUsDialog fragment = new RateUsDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DialogRateUsBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.dialog_rate_us, container, false);
        View view = binding.getRoot();

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
        }

        binding.setViewModel(mRateUsViewModel);

        mRateUsViewModel.setNavigator(this);

        return view;
    }


    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }
}
