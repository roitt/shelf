package com.rbhoompally.api;


import org.json.JSONStringer;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * One stop for api requests
 */
public interface ApiService {
    @GET("/")
    Single<ResponseBody> search(@Query("s") String query, @Query("apikey") String key);

    Single<JSONStringer> getDetail(String id);
}
