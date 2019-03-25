package com.rbhoompally.shelf.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rbhoompally.data.models.ShelfItem;
import com.rbhoompally.shelf.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter {
    private List<ShelfItem> shelfItems;

    public HomeAdapter() {
        shelfItems = new ArrayList<>();
    }

    public void updateCatalogItems(List<ShelfItem> catalogItems) {
        shelfItems = catalogItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
        return new HomeItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((HomeItemViewHolder) holder).updateContent(shelfItems.get(position));
    }

    @Override
    public int getItemCount() {
        return shelfItems.size();
    }

    class HomeItemViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title;
        TextView year;
        TextView type;

        HomeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        void initViews(View parent) {
            poster = parent.findViewById(R.id.poster);
            title = parent.findViewById(R.id.title);
            year = parent.findViewById(R.id.year);
            type = parent.findViewById(R.id.type);
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
    }
}
