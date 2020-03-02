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

package com.mindorks.framework.mvvm.data;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import com.mindorks.framework.mvvm.data.firebase.FirebaseDataHelper;
import com.mindorks.framework.mvvm.data.local.db.DbHelper;
import com.mindorks.framework.mvvm.data.local.prefs.PreferencesHelper;
import com.mindorks.framework.mvvm.data.model.api.BlogResponse;
import com.mindorks.framework.mvvm.data.model.api.LoginRequest;
import com.mindorks.framework.mvvm.data.model.api.LoginResponse;
import com.mindorks.framework.mvvm.data.model.api.LogoutResponse;
import com.mindorks.framework.mvvm.data.model.api.OpenSourceResponse;
import com.mindorks.framework.mvvm.data.model.db.Option;
import com.mindorks.framework.mvvm.data.model.db.Question;
import com.mindorks.framework.mvvm.data.model.db.User;
import com.mindorks.framework.mvvm.data.model.others.QuestionCardData;
import com.mindorks.framework.mvvm.data.remote.ApiHeader;
import com.mindorks.framework.mvvm.data.remote.ApiHelper;
import com.mindorks.framework.mvvm.utils.AppConstants;
import com.mindorks.framework.mvvm.utils.CommonUtils;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;

import static com.mindorks.framework.mvvm.data.firebase.FirebaseDataHelperImpl.USER_KEY_ACCESS_TOKEN;
import static com.mindorks.framework.mvvm.data.firebase.FirebaseDataHelperImpl.USER_KEY_EMAIL;
import static com.mindorks.framework.mvvm.data.firebase.FirebaseDataHelperImpl.USER_KEY_LOGGED_IN_MODE;
import static com.mindorks.framework.mvvm.data.firebase.FirebaseDataHelperImpl.USER_KEY_PROFILE_PIC_PATH;
import static com.mindorks.framework.mvvm.data.firebase.FirebaseDataHelperImpl.USER_KEY_USERNAME;
import static com.mindorks.framework.mvvm.data.firebase.FirebaseDataHelperImpl.USER_KEY_USER_ID;

/**
 * Repository is responsible for the persistence of your architecture and what it contains.
 * Simple now, this will grow to contain checks for where we fetch our data locally and remote.
 * Allow your helpers to construct objects and return the object in a reactive form, we don't care
 * how you got it.
 */
@Singleton public class UserSessionRepositoryImpl implements UserSessionRepository {

    private static final String TAG = "AppDataManager";

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final FirebaseDataHelper mFirebaseHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject public UserSessionRepositoryImpl(Context context, DbHelper dbHelper,
        PreferencesHelper preferencesHelper, ApiHelper apiHelper, FirebaseDataHelper firebaseHelper,
        Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mFirebaseHelper = firebaseHelper;
        mGson = gson;
    }

    @Override public Single<LogoutResponse> doLogoutApiCall() {
        return mApiHelper.doLogoutApiCall();
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override public Observable<List<Question>> getAllQuestions() {
        return mDbHelper.getAllQuestions();
    }

    @Override public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override public Single<BlogResponse> getBlogApiCall() {
        return mApiHelper.getBlogApiCall();
    }

    @Override public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return mApiHelper.getOpenSourceApiCall();
    }

    @Override public Observable<List<Option>> getOptionsForQuestionId(Long questionId) {
        return mDbHelper.getOptionsForQuestionId(questionId);
    }

    @Override public Observable<List<QuestionCardData>> getQuestionCardData() {
        return mDbHelper.getAllQuestions()
            .flatMap(questions -> Observable.fromIterable(questions))
            .flatMap(question -> Observable.zip(mDbHelper.getOptionsForQuestionId(question.id),
                Observable.just(question),
                (options, question1) -> new QuestionCardData(question1, options)))
            .toList()
            .toObservable();
    }

    @Override public Observable<Boolean> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override public Observable<Boolean> isOptionEmpty() {
        return mDbHelper.isOptionEmpty();
    }

    @Override public Observable<Boolean> isQuestionEmpty() {
        return mDbHelper.isQuestionEmpty();
    }

    @Override public Observable<Boolean> saveOption(Option option) {
        return mDbHelper.saveOption(option);
    }

    @Override public Observable<Boolean> saveOptionList(List<Option> optionList) {
        return mDbHelper.saveOptionList(optionList);
    }

    @Override public Observable<Boolean> saveQuestion(Question question) {
        return mDbHelper.saveQuestion(question);
    }

    @Override public Observable<Boolean> saveQuestionList(List<Question> questionList) {
        return mDbHelper.saveQuestionList(questionList);
    }

    @Override public Observable<Boolean> seedDatabaseOptions() {
        return mDbHelper.isOptionEmpty().concatMap(isEmpty -> {
            if (isEmpty) {
                Type type = new TypeToken<List<Option>>() {
                }.getType();
                List<Option> optionList = mGson.fromJson(
                    CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_OPTIONS),
                    type);
                return saveOptionList(optionList);
            }
            return Observable.just(false);
        });
    }

    @Override public Observable<Boolean> seedDatabaseQuestions() {
        return mDbHelper.isQuestionEmpty().concatMap(isEmpty -> {
            if (isEmpty) {
                Type type =
                    $Gson$Types.newParameterizedTypeWithOwner(null, List.class, Question.class);
                List<Question> questionList = mGson.fromJson(
                    CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_QUESTIONS),
                    type);
                return saveQuestionList(questionList);
            }
            return Observable.just(false);
        });
    }

    @Override public void setUserAsLoggedOut() {
        updateUserInfo(null, null, UserSessionRepository.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
            null, null, null);
    }

    @Override public void updateApiHeader(Long userId, String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setUserId(userId);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override public void updateUserInfo() {
        FirebaseUser user = mFirebaseHelper.getCurrentUser();

        if (user != null) {
            user.getIdToken(false)
                .addOnSuccessListener(getTokenResult ->
                    updateUserInfo(
                        getTokenResult.getToken(),
                        getTokenResult.getAuthTimestamp(),
                        LoggedInMode.LOGGED_IN_MODE_FIREBASE_AUTH_UI,
                        user.getDisplayName(),
                        user.getEmail(),
                        String.valueOf(user.getPhotoUrl()))
                );
        }
    }

    @Override public void updateUserInfo(String accessToken, Long userId, LoggedInMode loggedInMode,
        String userName, String email, String profilePicPath) {

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put(USER_KEY_ACCESS_TOKEN, accessToken);
        userMap.put(USER_KEY_USER_ID, userId);
        userMap.put(USER_KEY_LOGGED_IN_MODE, loggedInMode.toString());
        userMap.put(USER_KEY_USERNAME, userName);
        userMap.put(USER_KEY_EMAIL, email);
        userMap.put(USER_KEY_PROFILE_PIC_PATH, profilePicPath);

        setUserInFirestore(userMap)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override public void onSuccess(DocumentReference documentReference) {
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                Map<String, Object> user = task.getResult().getData();

                                setAccessToken((String) user.get((USER_KEY_ACCESS_TOKEN)));
                                setCurrentUserId((Long) user.get(USER_KEY_USER_ID));
                                setCurrentUserLoggedInMode(LoggedInMode.valueOf((String) user.get(USER_KEY_LOGGED_IN_MODE)));
                                setCurrentUserName((String) user.get(USER_KEY_USERNAME));
                                setCurrentUserEmail((String) user.get(USER_KEY_EMAIL);
                                setCurrentUserProfilePicUrl((String) user.get(USER_KEY_PROFILE_PIC_PATH));
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception e) {
                Timber.e(e);

                setAccessToken(accessToken);
                setCurrentUserId(userId);
                setCurrentUserLoggedInMode(loggedInMode);
                setCurrentUserName(userName);
                setCurrentUserEmail(email);
                setCurrentUserProfilePicUrl(profilePicPath);
            }
        });
    }

    @Override public Intent getSignInInent() {
        return mFirebaseHelper.getSignInInent();
    }

    @Nullable @Override public FirebaseUser getCurrentUser() {
        return mFirebaseHelper.getCurrentUser();
    }

    @NotNull @Override
    public Task<DocumentReference> setUserInFirestore(@NotNull Map<String, ?> userMap) {
        return mFirebaseHelper.setUserInFirestore(userMap);
    }
}
