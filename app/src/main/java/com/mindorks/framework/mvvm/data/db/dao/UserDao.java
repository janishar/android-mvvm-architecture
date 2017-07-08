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

package com.mindorks.framework.mvvm.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mindorks.framework.mvvm.data.db.model.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    Flowable<List<User>> loadAll();

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    Flowable<List<User>> loadAllByIds(List<Integer> userIds);

    @Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    Flowable<User> findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

}
