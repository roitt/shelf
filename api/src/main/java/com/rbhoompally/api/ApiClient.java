package com.rbhoompally.api;

import com.rbhoompally.api.converters.SearchResponseConverter;
import com.rbhoompally.data.models.ShelfItem;
import com.rbhoompally.data.results.SearchResult;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiClient {
    private static ApiClient apiClient;
    private ApiService apiService;

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.OMDB_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static ApiClient get() {
        if (apiClient == null) {
            apiClient = new ApiClient();
        }

        return apiClient;
    }

    public Single<SearchResult> search(String query) {
        return apiService.search(query, ApiConstants.OMDB_API_KEY)
                .map((ResponseBody response) -> SearchResponseConverter.convert(response.string()));
    }
}
