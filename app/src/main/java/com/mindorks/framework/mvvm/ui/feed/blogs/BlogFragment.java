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

package com.mindorks.framework.mvvm.ui.feed.blogs;

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
import com.mindorks.framework.mvvm.data.model.api.BlogResponse;
import com.mindorks.framework.mvvm.databinding.FragmentBlogBinding;
import com.mindorks.framework.mvvm.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class BlogFragment extends BaseFragment<FragmentBlogBinding, BlogViewModel>
        implements BlogNavigator, BlogAdapter.BlogAdapterListener {

    @Inject
    BlogAdapter mBlogAdapter;
    FragmentBlogBinding mFragmentBlogBinding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private BlogViewModel mBlogViewModel;

    public static BlogFragment newInstance() {
        Bundle args = new Bundle();
        BlogFragment fragment = new BlogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_blog;
    }

    @Override
    public BlogViewModel getViewModel() {
        mBlogViewModel = ViewModelProviders.of(this, mViewModelFactory).get(BlogViewModel.class);
        return mBlogViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBlogViewModel.setNavigator(this);
        mBlogAdapter.setListener(this);
    }

    @Override
    public void onRetryClick() {
        mBlogViewModel.fetchBlogs();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentBlogBinding = getViewDataBinding();
        setUp();
        subscribeToLiveData();
    }

    @Override
    public void updateBlog(List<BlogResponse.Blog> blogList) {
        mBlogAdapter.addItems(blogList);
    }

    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentBlogBinding.blogRecyclerView.setLayoutManager(mLayoutManager);
        mFragmentBlogBinding.blogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFragmentBlogBinding.blogRecyclerView.setAdapter(mBlogAdapter);
    }

    private void subscribeToLiveData() {
        mBlogViewModel.getBlogListLiveData().observe(this, blogs -> mBlogViewModel.addBlogItemsToList(blogs));
    }
}
