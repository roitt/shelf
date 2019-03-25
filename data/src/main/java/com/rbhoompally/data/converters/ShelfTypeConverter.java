package com.rbhoompally.data.converters;

import com.rbhoompally.data.models.ShelfItem;

import androidx.room.TypeConverter;

public class ShelfTypeConverter {
    @TypeConverter
    public static ShelfItem.Type toType(String type) {
        if (type.equals(ShelfItem.Type.movie.name())) {
            return ShelfItem.Type.movie;
        } else if (type.equals(ShelfItem.Type.series.name())) {
            return ShelfItem.Type.series;
        } else if (type.equals(ShelfItem.Type.game.name())) {
            return ShelfItem.Type.game;
        } else if(type.equals(ShelfItem.Type.episode.name())) {
            return ShelfItem.Type.episode;
        } else {
            throw new IllegalArgumentException("Could not recognize shelf item type");
        }
    }

    @TypeConverter
    public static String toString(ShelfItem.Type type) {
        return type.name();
    }
}
