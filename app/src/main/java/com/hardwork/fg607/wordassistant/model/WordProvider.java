package com.hardwork.fg607.wordassistant.model;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by fg607 on 16-1-3.
 */
public interface WordProvider {

    @GET("/")
    WordInfo getWord(@Query("word") String wordName);

    @GET("/daywords")
    DayWords getDayWords();
}
