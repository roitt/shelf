package com.rbhoompally.shelf.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.rbhoompally.shelf.viewmodels.Injection;
import com.rbhoompally.data.models.ShelfItem;
import com.rbhoompally.shelf.R;
import com.rbhoompally.shelf.adapters.SearchAdapter;
import com.rbhoompally.shelf.viewmodels.SearchFragmentViewModel;/**/
import com.rbhoompally.shelf.viewmodels.ViewModelFactory;

import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends Fragment implements SearchAdapter.AddFabClickListener {
    private static final String TAG = "SearchFragment";
    private SearchAdapter searchAdapter;

    private ViewModelFactory viewModelFactory;
    private SearchFragmentViewModel searchFragmentViewModel;
    private CompositeDisposable bin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bin = new CompositeDisposable();
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModelFactory = Injection.provideViewModelFactory(view.getContext());
        searchFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchFragmentViewModel.class);

        composeBack(view);
        composeSearchBar(view);
        composeRecyclerView(view);

        bindStreams();
    }

    private void bindStreams() {
        bin.add(searchFragmentViewModel.getSearchResultsStream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        searchResult -> {
                            if (searchResult.searchItems == null || searchResult.searchItems.isEmpty()) {
                                return;
                            }

                            Log.d("SearchFragment","Search result: " + searchResult.searchItems.size());
                            // Edit adapter with result
                            searchAdapter.updateSearchItems(searchResult.searchItems);
                            searchAdapter.notifyDataSetChanged();
                        },
                        error -> Log.d("SearchFragment", "Error while fetch search results: " + error.getMessage())
                ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchFragmentViewModel.destroy();
    }

    @Override
    public void onSearchAddClick(ShelfItem shelfItem) {
        bin.add(searchFragmentViewModel.insertShelfItem(shelfItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.d(TAG, "Item successfully added to DB");
                        },
                        error -> Log.d(TAG, "Error while adding search item to the DB")
                )
        );
        searchFragmentViewModel.insertShelfItem(shelfItem);
    }

    private void composeBack(View view) {
        ImageButton back = view.findViewById(R.id.back);
        back.setOnClickListener(backClickListener);
    }

    private void composeSearchBar(View view) {
        AppCompatEditText searchBar = view.findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("SearchFragment","Search initiated for: " + s.toString());
                searchFragmentViewModel.searchFor(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void composeRecyclerView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        // Init recycler view
        RecyclerView searchRecyclerView = view.findViewById(R.id.search_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(searchRecyclerView.getContext(),
                layoutManager.getOrientation());
        searchRecyclerView.addItemDecoration(dividerItemDecoration);

        // use a linear layout manager
        searchRecyclerView.setLayoutManager(layoutManager);

        searchAdapter = new SearchAdapter(this);
        searchRecyclerView.setAdapter(searchAdapter);
    }

    private View.OnClickListener backClickListener = v -> {
        goBack();
    };

    private void goBack() {
        assert getFragmentManager() != null;
        getFragmentManager().popBackStackImmediate();
    }
}
