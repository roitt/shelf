package com.rbhoompally.shelf.viewmodels;

import android.app.Application;

import com.rbhoompally.api.ApiClient;
import com.rbhoompally.cache.CatalogDataSource;
import com.rbhoompally.data.models.ShelfItem;
import com.rbhoompally.data.results.SearchResult;

import java.util.List;

import androidx.lifecycle.ViewModel;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchFragmentViewModel extends ViewModel {
    private CompositeDisposable bin;
    private PublishSubject<SearchResult> searchSubject;
    private CatalogDataSource catalogDataSource;

    public SearchFragmentViewModel(CatalogDataSource catalogDataSource) {
        bin = new CompositeDisposable();
        searchSubject = PublishSubject.create();
        this.catalogDataSource = catalogDataSource;
    }

    public Flowable<SearchResult> getSearchResultsStream() {
        return searchSubject.toFlowable(BackpressureStrategy.LATEST);
    }

    public void searchFor(CharSequence s) {
        if (s.length() > 2) {
            bin.add(ApiClient.get().search(s.toString())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            searchResult -> {
                                searchSubject.onNext(searchResult);
                            }
                    )
            );
        }
    }

    public Completable insertShelfItem(ShelfItem shelfItem) {
        return catalogDataSource.insertShelfItem(shelfItem);
    }

    public void destroy() {
        bin.dispose();
        this.catalogDataSource = null;
    }
}
