package com.rbhoompally.shelf.viewmodels;

import com.rbhoompally.cache.CatalogDataSource;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final CatalogDataSource dataSource;

    public ViewModelFactory(CatalogDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchFragmentViewModel.class)) {
            return (T) new SearchFragmentViewModel(dataSource);
        } else if (modelClass.isAssignableFrom(HomeFragmentViewModel.class)) {
            return (T) new HomeFragmentViewModel(dataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}