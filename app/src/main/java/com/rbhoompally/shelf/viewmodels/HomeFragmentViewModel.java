package com.rbhoompally.shelf.viewmodels;

import com.rbhoompally.cache.CatalogDataSource;
import com.rbhoompally.data.models.ShelfItem;

import java.util.List;

import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

public class HomeFragmentViewModel extends ViewModel {
    private CompositeDisposable bin;
    private CatalogDataSource catalogDataSource;

    HomeFragmentViewModel(CatalogDataSource catalogDataSource) {
        bin = new CompositeDisposable();
        this.catalogDataSource = catalogDataSource;
    }

    public Flowable<List<ShelfItem>> getAllShelfItems() {
        return catalogDataSource.getAllShelfItems();
    }
}
