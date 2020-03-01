package com.mindorks.framework.mvvm.ui.feed;

import com.mindorks.framework.mvvm.data.UserSessionRepository;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

/**
 * Created by Jyoti on 29/07/17.
 */

public class FeedViewModel extends BaseViewModel {

    public FeedViewModel(UserSessionRepository UserSessionRepository, SchedulerProvider schedulerProvider) {
        super(UserSessionRepository, schedulerProvider);
    }
}
