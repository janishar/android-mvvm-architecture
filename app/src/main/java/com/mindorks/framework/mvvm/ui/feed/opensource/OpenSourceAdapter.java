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

import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mindorks.framework.mvvm.databinding.ItemOpenSourceEmptyViewBinding;
import com.mindorks.framework.mvvm.databinding.ItemOpenSourceViewBinding;
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder;
import com.mindorks.framework.mvvm.utils.AppLogger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class OpenSourceAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private final List<OpenSourceItemViewModel> mOpenSourceResponseList;

    private OpenSourceAdapterListener mListener;

    public OpenSourceAdapter() {
        this.mOpenSourceResponseList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        if (!mOpenSourceResponseList.isEmpty()) {
            return mOpenSourceResponseList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!mOpenSourceResponseList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemOpenSourceViewBinding openSourceViewBinding = ItemOpenSourceViewBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new OpenSourceViewHolder(openSourceViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemOpenSourceEmptyViewBinding emptyViewBinding = ItemOpenSourceEmptyViewBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<OpenSourceItemViewModel> repoList) {
        mOpenSourceResponseList.addAll(repoList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mOpenSourceResponseList.clear();
    }

    public void setListener(OpenSourceAdapterListener listener) {
        this.mListener = listener;
    }

    public interface OpenSourceAdapterListener {

        void onRetryClick();
    }

    public class EmptyViewHolder extends BaseViewHolder implements OpenSourceEmptyItemViewModel.OpenSourceEmptyItemViewModelListener {

        private final ItemOpenSourceEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemOpenSourceEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            OpenSourceEmptyItemViewModel emptyItemViewModel = new OpenSourceEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }
    }

    public class OpenSourceViewHolder extends BaseViewHolder implements View.OnClickListener {

        private final ItemOpenSourceViewBinding mBinding;

        public OpenSourceViewHolder(ItemOpenSourceViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final OpenSourceItemViewModel mOpenSourceItemViewModel = mOpenSourceResponseList.get(position);
            mBinding.setViewModel(mOpenSourceItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if (mOpenSourceResponseList.get(0).projectUrl.get() != null) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(mOpenSourceResponseList.get(0).projectUrl.get()));
                    itemView.getContext().startActivity(intent);
                } catch (Exception e) {
                    AppLogger.d("url error");
                }
            }
        }
    }
}