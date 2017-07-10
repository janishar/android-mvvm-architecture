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

package com.mindorks.framework.mvvm.ui.feed;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;

import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityFeedBinding;
import com.mindorks.framework.mvvm.ui.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class FeedActivity extends BaseActivity {

    @Inject
    FeedPagerAdapter mPagerAdapter;

    private ActivityFeedBinding mBinding;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, FeedActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_feed);

        getActivityComponent().inject(this);

        setUp();
    }

    private void setUp() {

        setSupportActionBar(mBinding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPagerAdapter.setCount(2);

        mBinding.feedViewPager.setAdapter(mPagerAdapter);

        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(getString(R.string.blog)));
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText(getString(R.string.open_source)));

        mBinding.feedViewPager.setOffscreenPageLimit(mBinding.tabLayout.getTabCount());

        mBinding.feedViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tabLayout));

        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.feedViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
