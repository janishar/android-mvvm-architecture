package com.mindorks.framework.mvvm;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.mindorks.framework.mvvm.data.UserSessionRepository;
import com.mindorks.framework.mvvm.ui.about.AboutViewModel;
import com.mindorks.framework.mvvm.ui.feed.FeedViewModel;
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogViewModel;
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceViewModel;
import com.mindorks.framework.mvvm.ui.login.LoginViewModel;
import com.mindorks.framework.mvvm.ui.main.MainViewModel;
import com.mindorks.framework.mvvm.ui.main.rating.RateUsViewModel;
import com.mindorks.framework.mvvm.ui.splash.SplashViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jyotidubey on 22/02/19.
 */
@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

  private final UserSessionRepository UserSessionRepository;
  private final SchedulerProvider schedulerProvider;

  @Inject
  public ViewModelProviderFactory(UserSessionRepository UserSessionRepository,
      SchedulerProvider schedulerProvider) {
    this.UserSessionRepository = UserSessionRepository;
    this.schedulerProvider = schedulerProvider;
  }


  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    if (modelClass.isAssignableFrom(AboutViewModel.class)) {
      //noinspection unchecked
      return (T) new AboutViewModel(UserSessionRepository,schedulerProvider);
    } else if (modelClass.isAssignableFrom(FeedViewModel.class)) {
      //noinspection unchecked
      return (T) new FeedViewModel(UserSessionRepository,schedulerProvider);
    } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
      //noinspection unchecked
      return (T) new LoginViewModel(UserSessionRepository,schedulerProvider);
    } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
      //noinspection unchecked
      return (T) new MainViewModel(UserSessionRepository,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(BlogViewModel.class)) {
      //noinspection unchecked
      return (T) new BlogViewModel(UserSessionRepository,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(RateUsViewModel.class)) {
      //noinspection unchecked
      return (T) new RateUsViewModel(UserSessionRepository,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(OpenSourceViewModel.class)) {
      //noinspection unchecked
      return (T) new OpenSourceViewModel(UserSessionRepository,schedulerProvider);
    }
    else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
      //noinspection unchecked
      return (T) new SplashViewModel(UserSessionRepository,schedulerProvider);
    }
    throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
  }
}