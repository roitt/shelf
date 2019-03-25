package com.rbhoompally.cache;

import com.rbhoompally.data.models.ShelfItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface CatalogDataSource {
    Flowable<List<ShelfItem>> getAllShelfItems();
    Completable insertShelfItem(ShelfItem shelfItem);
    void deleteAllUsers();
}
