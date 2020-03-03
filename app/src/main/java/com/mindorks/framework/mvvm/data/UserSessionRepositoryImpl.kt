package com.mindorks.framework.mvvm.data

import android.content.Context
import android.content.Intent
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import com.google.gson.reflect.TypeToken
import com.mindorks.framework.mvvm.data.UserSessionRepository.LoggedInMode
import com.mindorks.framework.mvvm.data.firebase.FirebaseDataHelper
import com.mindorks.framework.mvvm.data.firebase.FirebaseDataHelperImpl
import com.mindorks.framework.mvvm.data.local.db.DbHelper
import com.mindorks.framework.mvvm.data.local.prefs.PreferencesHelper
import com.mindorks.framework.mvvm.data.model.api.BlogResponse
import com.mindorks.framework.mvvm.data.model.api.LoginRequest.ServerLoginRequest
import com.mindorks.framework.mvvm.data.model.api.LoginResponse
import com.mindorks.framework.mvvm.data.model.api.LogoutResponse
import com.mindorks.framework.mvvm.data.model.api.OpenSourceResponse
import com.mindorks.framework.mvvm.data.model.db.Option
import com.mindorks.framework.mvvm.data.model.db.Question
import com.mindorks.framework.mvvm.data.model.db.User
import com.mindorks.framework.mvvm.data.model.others.QuestionCardData
import com.mindorks.framework.mvvm.data.remote.ApiHeader
import com.mindorks.framework.mvvm.data.remote.ApiHelper
import com.mindorks.framework.mvvm.utils.AppConstants
import com.mindorks.framework.mvvm.utils.CommonUtils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import timber.log.Timber
import java.lang.reflect.Type
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository is responsible for the persistence of your architecture and what it contains.
 * Simple now, this will grow to contain checks for where we fetch our data locally and remote.
 * Allow your helpers to construct objects and return the object in a reactive form, we don't care
 * how you got it.
 */
@Singleton
class UserSessionRepositoryImpl @Inject constructor(private val mContext: Context,
                                                    private val mDbHelper: DbHelper,
                                                    private val mPreferencesHelper: PreferencesHelper,
                                                    private val mApiHelper: ApiHelper,
                                                    private val mFirebaseHelper: FirebaseDataHelper,
                                                    private val mGson: Gson) : UserSessionRepository {
    override fun doLogoutApiCall(): Single<LogoutResponse> {
        return mApiHelper.doLogoutApiCall()
    }

    override fun doServerLoginApiCall(request: ServerLoginRequest): Single<LoginResponse> {
        return mApiHelper.doServerLoginApiCall(request)
    }

    override fun getAccessToken(): String {
        return mPreferencesHelper.accessToken
    }

    override fun setAccessToken(accessToken: String) {
        mPreferencesHelper.accessToken = accessToken
        mApiHelper.apiHeader.protectedApiHeader.accessToken = accessToken
    }

    override fun getAllQuestions(): Observable<List<Question>> {
        return mDbHelper.allQuestions
    }

    override fun getAllUsers(): Observable<List<User>> {
        return mDbHelper.allUsers
    }

    override fun getApiHeader(): ApiHeader {
        return mApiHelper.apiHeader
    }

    override fun getBlogApiCall(): Single<BlogResponse> {
        return mApiHelper.blogApiCall
    }

    override fun getCurrentUserEmail(): String? {
        return mPreferencesHelper.currentUserEmail
    }

    override fun setCurrentUserEmail(email: String) {
        mPreferencesHelper.currentUserEmail = email
    }

    override fun getCurrentUserId(): Long {
        return mPreferencesHelper.currentUserId
    }

    override fun setCurrentUserId(userId: Long) {
        mPreferencesHelper.currentUserId = userId
    }

    override fun getCurrentUserLoggedInMode(): Int {
        return mPreferencesHelper.currentUserLoggedInMode
    }

    override fun setCurrentUserLoggedInMode(mode: LoggedInMode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode)
    }

    override fun getCurrentUserName(): String? {
        return mPreferencesHelper.currentUserName
    }

    override fun setCurrentUserName(userName: String) {
        mPreferencesHelper.currentUserName = userName
    }

    override fun getCurrentUserProfilePicUrl(): String? {
        return mPreferencesHelper.currentUserProfilePicUrl
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String) {
        mPreferencesHelper.currentUserProfilePicUrl = profilePicUrl
    }

    override fun getOpenSourceApiCall(): Single<OpenSourceResponse> {
        return mApiHelper.openSourceApiCall
    }

    override fun getOptionsForQuestionId(questionId: Long): Observable<List<Option>> {
        return mDbHelper.getOptionsForQuestionId(questionId)
    }

    override fun getQuestionCardData(): Observable<List<QuestionCardData>> {
        return mDbHelper.allQuestions
            .flatMap { questions: List<Question>? -> Observable.fromIterable(questions) }
            .flatMap { question: Question ->
                Observable.zip(mDbHelper.getOptionsForQuestionId(question.id),
                    Observable.just(question),
                    BiFunction { options: List<Option?>?, question1: Question? -> QuestionCardData(question1, options) })
            }
            .toList()
            .toObservable()
    }

    override fun insertUser(user: User): Observable<Boolean> {
        return mDbHelper.insertUser(user)
    }

    override fun isOptionEmpty(): Observable<Boolean> {
        return mDbHelper.isOptionEmpty
    }

    override fun isQuestionEmpty(): Observable<Boolean> {
        return mDbHelper.isQuestionEmpty
    }

    override fun saveOption(option: Option): Observable<Boolean> {
        return mDbHelper.saveOption(option)
    }

    override fun saveOptionList(optionList: List<Option>): Observable<Boolean> {
        return mDbHelper.saveOptionList(optionList)
    }

    override fun saveQuestion(question: Question): Observable<Boolean> {
        return mDbHelper.saveQuestion(question)
    }

    override fun saveQuestionList(questionList: List<Question>): Observable<Boolean> {
        return mDbHelper.saveQuestionList(questionList)
    }

    override fun seedDatabaseOptions(): Observable<Boolean> {
        return mDbHelper.isOptionEmpty.concatMap { isEmpty: Boolean ->
            if (isEmpty) {
                val type = object : TypeToken<List<Option?>?>() {}.type
                val optionList = mGson.fromJson<List<Option>>(
                    CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_OPTIONS),
                    type)
                return@concatMap saveOptionList(optionList)
            }
            Observable.just(false)
        }
    }

    override fun seedDatabaseQuestions(): Observable<Boolean> {
        return mDbHelper.isQuestionEmpty.concatMap { isEmpty: Boolean ->
            if (isEmpty) {
                val type: Type = `$Gson$Types`.newParameterizedTypeWithOwner(null, MutableList::class.java, Question::class.java)
                val questionList = mGson.fromJson<List<Question>>(
                    CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_QUESTIONS),
                    type)
                return@concatMap saveQuestionList(questionList)
            }
            Observable.just(false)
        }
    }

    override fun setUserAsLoggedOut() {
        updateUserInfo(accessToken, currentUserId, LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
            currentUserName ?: "", currentUserEmail ?: "", currentUserProfilePicUrl ?: "")
    }

    override fun updateUserInfo() {
        val user = mFirebaseHelper.getCurrentUser()
        user?.getIdToken(false)?.addOnSuccessListener { getTokenResult: GetTokenResult ->
            updateUserInfo(
                getTokenResult.token ?: "",
                user.metadata?.creationTimestamp ?: 0,
                LoggedInMode.LOGGED_IN_MODE_FIREBASE_AUTH_UI,
                user.displayName ?: "",
                user.email ?: "",
                user.photoUrl.toString()
            )
        }
    }

    override fun updateUserInfo(accessToken: String, userId: Long, loggedInMode: LoggedInMode,
                                userName: String, email: String, profilePicPath: String) {

        val userMap = HashMap<String, Any>()
        userMap[FirebaseDataHelperImpl.USER_KEY_ACCESS_TOKEN] = accessToken
        userMap[FirebaseDataHelperImpl.USER_KEY_USER_ID] = userId
        userMap[FirebaseDataHelperImpl.USER_KEY_LOGGED_IN_MODE] = loggedInMode.toString()
        userMap[FirebaseDataHelperImpl.USER_KEY_USERNAME] = userName
        userMap[FirebaseDataHelperImpl.USER_KEY_EMAIL] = email
        userMap[FirebaseDataHelperImpl.USER_KEY_PROFILE_PIC_PATH] = profilePicPath

        setUserInFirestore(userMap).addOnFailureListener { e -> Timber.e(e) }

        setAccessToken(accessToken)
        setCurrentUserId(userId)
        setCurrentUserLoggedInMode(loggedInMode)
        setCurrentUserName(userName)
        setCurrentUserEmail(email)
        setCurrentUserProfilePicUrl(profilePicPath)
    }

    override fun getSignInInent(): Intent {
        return mFirebaseHelper.getSignInInent()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return mFirebaseHelper.getCurrentUser()
    }

    override fun setUserInFirestore(userMap: Map<String, Any>): Task<Void> {
        return mFirebaseHelper.setUserInFirestore(userMap)
    }

    companion object {
        private const val TAG = "AppDataManager"
    }

}