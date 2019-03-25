package com.rbhoompally.data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rbhoompally.data.converters.ShelfTypeConverter;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "catalog")
public class ShelfItem {
    public enum Type {
        movie, series, episode, game
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "imdbId")
    @JsonProperty("imdbID")
    public String imdbId;

    @ColumnInfo(name = "title")
    @JsonProperty("Title")
    public String title;

    @ColumnInfo(name = "year")
    @JsonProperty("Year")
    public String year;

    @ColumnInfo(name = "poster")
    @JsonProperty("Poster")
    public String posterUrl;

    @ColumnInfo(name = "type")
    @TypeConverters(ShelfTypeConverter.class)
    @JsonProperty("Type")
    public Type type;
}
