package com.rbhoompally.shelf.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rbhoompally.shelf.R;
import com.rbhoompally.shelf.adapters.HomeAdapter;
import com.rbhoompally.shelf.viewmodels.HomeFragmentViewModel;
import com.rbhoompally.shelf.viewmodels.Injection;
import com.rbhoompally.shelf.viewmodels.ViewModelFactory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private HomeFragmentViewModel homeFragmentViewModel;
    private HomeAdapter homeAdapter;
    private CompositeDisposable bin;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        bin = new CompositeDisposable();
        return inflater.inflate(R.layout.home_fragment, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // Create ViewModel
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(view.getContext());
        homeFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeFragmentViewModel.class);

        composeRecyclerView(view);
        composeAddButton(view);

        // Update catalog items
        updateCatalogItems();
    }

    private void composeRecyclerView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        RecyclerView homeRecyclerView = view.findViewById(R.id.home_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(homeRecyclerView.getContext(),
                layoutManager.getOrientation());
        homeRecyclerView.addItemDecoration(dividerItemDecoration);

        homeRecyclerView.setLayoutManager(layoutManager);
        homeAdapter = new HomeAdapter();
        homeRecyclerView.setAdapter(homeAdapter);
    }

    private void composeAddButton(View view) {
        FloatingActionButton fab = view.findViewById(R.id.add_fab);
        fab.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_searchFragment));
    }

    private void updateCatalogItems() {
        bin.add(homeFragmentViewModel.getAllShelfItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            Log.d(TAG, "Catalog items returned " + items.size());
                            homeAdapter.updateCatalogItems(items);
                            homeAdapter.notifyDataSetChanged();
                        },
                        error -> Log.d(TAG, "Error fetching catalog")
                ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bin.clear();
    }
}
