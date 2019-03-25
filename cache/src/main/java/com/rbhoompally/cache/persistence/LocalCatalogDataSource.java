package com.rbhoompally.cache.persistence;

import com.rbhoompally.cache.CatalogDataSource;
import com.rbhoompally.cache.daos.CatalogDao;
import com.rbhoompally.data.models.ShelfItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalCatalogDataSource implements CatalogDataSource {
    private final CatalogDao catalogDao;

    public LocalCatalogDataSource(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    @Override
    public Flowable<List<ShelfItem>> getAllShelfItems() {
        return catalogDao.getShelfItems();
    }

    @Override
    public Completable insertShelfItem(ShelfItem shelfItem) {
        return catalogDao.insertShelfItem(shelfItem);
    }

    @Override
    public void deleteAllUsers() {
        catalogDao.deleteAllShelfItems();
    }
}
