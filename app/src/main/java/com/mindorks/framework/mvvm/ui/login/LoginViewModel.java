

package com.mindorks.framework.mvvm.ui.login;

import android.content.Intent;
import com.mindorks.framework.mvvm.data.UserSessionRepository;
import com.mindorks.framework.mvvm.google.SingleLiveEvent;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    private UserSessionRepository repository;

    SingleLiveEvent<Intent> launchSignInEvent;

    public LoginViewModel(UserSessionRepository userSessionRepository, SchedulerProvider schedulerProvider) {
        super(userSessionRepository, schedulerProvider);

        repository = userSessionRepository;

        launchSignInEvent = new SingleLiveEvent<>();
    }

    public void buildSignInIntent() {
        launchSignInEvent.setValue(repository.getSignInInent());
    }

    public void updateUserSession() {
        repository.updateUserInfo();
        getNavigator().openMainActivity();
    }
}
