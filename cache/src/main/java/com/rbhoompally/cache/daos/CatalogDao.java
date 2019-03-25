package com.rbhoompally.cache.daos;

import com.rbhoompally.data.models.ShelfItem;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface CatalogDao {

    @Query("SELECT * FROM catalog")
    Flowable<List<ShelfItem>> getShelfItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertShelfItem(ShelfItem shelfItem);

    @Query("DELETE FROM catalog")
    void deleteAllShelfItems();
}
