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

package com.mindorks.framework.mvvm.ui.feed.opensource;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.FragmentOpenSourceBinding;
import com.mindorks.framework.mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class OpenSourceFragment extends BaseFragment<FragmentOpenSourceBinding, OpenSourceViewModel>
        implements OpenSourceNavigator, OpenSourceAdapter.OpenSourceAdapterListener {

    FragmentOpenSourceBinding mFragmentOpenSourceBinding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    OpenSourceAdapter mOpenSourceAdapter;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private OpenSourceViewModel mOpenSourceViewModel;

    public static OpenSourceFragment newInstance() {
        Bundle args = new Bundle();
        OpenSourceFragment fragment = new OpenSourceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_open_source;
    }

    @Override
    public OpenSourceViewModel getViewModel() {
        mOpenSourceViewModel = ViewModelProviders.of(this, mViewModelFactory).get(OpenSourceViewModel.class);
        return mOpenSourceViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOpenSourceViewModel.setNavigator(this);
        mOpenSourceAdapter.setListener(this);
    }

    @Override
    public void onRetryClick() {
        mOpenSourceViewModel.fetchRepos();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentOpenSourceBinding = getViewDataBinding();
        setUp();
        subscribeToLiveData();
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentOpenSourceBinding.openSourceRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentOpenSourceBinding.openSourceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentOpenSourceBinding.openSourceRecyclerView.setAdapter(mOpenSourceAdapter);
    }

    private void subscribeToLiveData() {
        mOpenSourceViewModel.getOpenSourceRepos().observe(this,
                openSourceItemViewModels -> mOpenSourceViewModel.addOpenSourceItemsToList(openSourceItemViewModels));
    }
}
