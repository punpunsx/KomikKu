package com.komikatow.komiku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.komikatow.komiku.R;
import com.komikatow.komiku.databinding.ItemHomeKomikChinaKoreaBinding;
import com.komikatow.komiku.room.enity.FavoriteEnity;
import com.komikatow.komiku.utils.ItemRecyclerClick;

import java.util.List;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.FavoriteHolder> {

    private ItemHomeKomikChinaKoreaBinding binding;
    private final List <FavoriteEnity> list;
    private final Context context;
    private final ItemRecyclerClick listener;

    public AdapterFavorite(List<FavoriteEnity> list, Context context, ItemRecyclerClick listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ItemHomeKomikChinaKoreaBinding.inflate(LayoutInflater.from(context), parent, false);
        return new FavoriteHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {

        Glide.with(context)
                .load(list.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.color.ErrorLoadImage)
                .placeholder(R.color.PlaceHolderImage)
                .into(binding.itemThumbnail);

        binding.itemTitle.setText(list.get(position).getTitle());
        holder.itemView.setOnClickListener(v-> listener.onClickListener(position));
        holder.itemView.setOnLongClickListener(v-> listener.onLongClickListener(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class FavoriteHolder extends RecyclerView.ViewHolder {
        public FavoriteHolder(@NonNull ItemHomeKomikChinaKoreaBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
