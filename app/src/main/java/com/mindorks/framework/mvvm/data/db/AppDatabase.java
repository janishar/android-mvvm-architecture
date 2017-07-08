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

package com.mindorks.framework.mvvm.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mindorks.framework.mvvm.data.db.dao.OptionDao;
import com.mindorks.framework.mvvm.data.db.dao.QuestionDao;
import com.mindorks.framework.mvvm.data.db.dao.UserDao;
import com.mindorks.framework.mvvm.data.db.model.Option;
import com.mindorks.framework.mvvm.data.db.model.Question;
import com.mindorks.framework.mvvm.data.db.model.User;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Database(entities = {User.class, Question.class, Option.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract QuestionDao questionDao();

    public abstract OptionDao optionDao();

}
