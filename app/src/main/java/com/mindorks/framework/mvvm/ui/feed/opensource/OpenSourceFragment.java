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


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.data.model.api.OpenSourceResponse;
import com.mindorks.framework.mvvm.databinding.FragmentOpenSourceBinding;
import com.mindorks.framework.mvvm.di.component.ActivityComponent;
import com.mindorks.framework.mvvm.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class OpenSourceFragment extends BaseFragment<FragmentOpenSourceBinding, OpenSourceViewModel> implements OpenSourceNavigator, OpenSourceAdapter.OpenSourceAdapterListener {

    @Inject
    OpenSourceViewModel mOpenSourceViewModel;

    @Inject
    OpenSourceAdapter mOpenSourceAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;
    FragmentOpenSourceBinding mFragmentOpenSourceBinding;

    public static OpenSourceFragment newInstance() {
        Bundle args = new Bundle();
        OpenSourceFragment fragment = new OpenSourceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDependencyInjection();
        mOpenSourceViewModel.setNavigator(this);
        mOpenSourceAdapter.setListener(this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentOpenSourceBinding = getViewDataBinding();
        setUp();
    }

    @Override
    public OpenSourceViewModel getViewModel() {
        return mOpenSourceViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_open_source;
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentOpenSourceBinding.openSourceRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentOpenSourceBinding.openSourceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentOpenSourceBinding.openSourceRecyclerView.setAdapter(mOpenSourceAdapter);

        mOpenSourceViewModel.fetchRepos();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void updateRepo(List<OpenSourceResponse.Repo> repoList) {
        mOpenSourceViewModel.populateViewModel(repoList);
        mOpenSourceAdapter.addItems(mOpenSourceViewModel.openSourceItemViewModels);
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onRetryClick() {
        mOpenSourceViewModel.fetchRepos();
    }

    private void performDependencyInjection() {
        ActivityComponent component = getActivityComponent();
        if (getActivityComponent() != null) {
            component.inject(this);
        }
    }
}
