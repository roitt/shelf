package com.rbhoompally.data.factories;

public class ShelfItemFactory {
    ShelfItemFactory shelfItemFactory;

    private ShelfItemFactory() {}

    public ShelfItemFactory create() {
        if (shelfItemFactory == null) {
            shelfItemFactory = new ShelfItemFactory();
        }

        return shelfItemFactory;
    }
}
