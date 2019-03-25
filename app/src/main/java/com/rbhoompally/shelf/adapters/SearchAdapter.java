package com.rbhoompally.shelf.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rbhoompally.data.models.ShelfItem;
import com.rbhoompally.shelf.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface AddFabClickListener {
        void onSearchAddClick(ShelfItem shelfItem);
    }

    private List<ShelfItem> searchItems;
    private AddFabClickListener searchAddFabClickListener;

    public SearchAdapter(AddFabClickListener addFabClickListener) {
        searchAddFabClickListener = addFabClickListener;
        searchItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new SearchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchItemViewHolder searchItemViewHolder = (SearchItemViewHolder) holder;
        ShelfItem item = searchItems.get(position);
        searchItemViewHolder.updateContent(item);
    }

    @Override
    public int getItemCount() {
        return searchItems.size();
    }

    public void updateSearchItems(List<ShelfItem> searchItems) {
        this.searchItems = searchItems;
    }

    class SearchItemViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;
        TextView year;
        TextView type;
        FloatingActionButton searchAddFab;

        SearchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        void initViews(View parent) {
            poster = parent.findViewById(R.id.poster);
            title = parent.findViewById(R.id.title);
            year = parent.findViewById(R.id.year);
            type = parent.findViewById(R.id.type);
            searchAddFab = parent.findViewById(R.id.search_add_fab);

            searchAddFab.setOnClickListener(searchAddClickListener);
        }

        void updateContent(ShelfItem shelfItem) {
            title.setText(shelfItem.title);
            year.setText(shelfItem.year);
            String typeS = String.valueOf(shelfItem.type);
            typeS = typeS.substring(0,1).toUpperCase() + typeS.substring(1);
            type.setText(typeS);
            Picasso.get()
                    .load(shelfItem.posterUrl)
                    .placeholder(R.drawable.default_movie_poster)
                    .fit()
                    .centerInside()
                    .into(poster);
        }

        private View.OnClickListener searchAddClickListener = v -> {
            searchAddFabClickListener.onSearchAddClick(searchItems.get(getAdapterPosition()));
        };
    }
}
