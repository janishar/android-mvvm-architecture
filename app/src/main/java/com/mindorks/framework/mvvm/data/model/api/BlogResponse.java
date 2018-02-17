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

package com.mindorks.framework.mvvm.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by amitshekhar on 07/07/17.
 */

public class BlogResponse {

    @Expose
    @SerializedName("data")
    private List<Blog> data;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogResponse)) {
            return false;
        }

        BlogResponse that = (BlogResponse) o;

        if (!statusCode.equals(that.statusCode)) {
            return false;
        }
        if (!message.equals(that.message)) {
            return false;
        }
        return data.equals(that.data);

    }

    @Override
    public int hashCode() {
        int result = statusCode.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    public List<Blog> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static class Blog {

        @Expose
        @SerializedName("author")
        private String author;

        @Expose
        @SerializedName("blog_url")
        private String blogUrl;

        @Expose
        @SerializedName("img_url")
        private String coverImgUrl;

        @Expose
        @SerializedName("published_at")
        private String date;

        @Expose
        @SerializedName("description")
        private String description;

        @Expose
        @SerializedName("title")
        private String title;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Blog)) {
                return false;
            }

            Blog blog = (Blog) o;

            if (!blogUrl.equals(blog.blogUrl)) {
                return false;
            }
            if (!coverImgUrl.equals(blog.coverImgUrl)) {
                return false;
            }
            if (!title.equals(blog.title)) {
                return false;
            }
            if (!description.equals(blog.description)) {
                return false;
            }
            if (!author.equals(blog.author)) {
                return false;
            }
            return date.equals(blog.date);

        }

        @Override
        public int hashCode() {
            int result = blogUrl.hashCode();
            result = 31 * result + coverImgUrl.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + description.hashCode();
            result = 31 * result + author.hashCode();
            result = 31 * result + date.hashCode();
            return result;
        }

        public String getAuthor() {
            return author;
        }

        public String getBlogUrl() {
            return blogUrl;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public String getDate() {
            return date;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }
    }
}