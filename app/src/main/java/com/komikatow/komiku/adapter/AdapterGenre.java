package com.komikatow.komiku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.komikatow.komiku.databinding.ItemGenreBinding;
import com.komikatow.komiku.model.ModelGenre;
import com.komikatow.komiku.utils.ItemRecyclerClick;

import java.util.List;

public class AdapterGenre extends RecyclerView.Adapter<AdapterGenre.GenreHolder> {

    private ItemGenreBinding binding;
    private final Context context;
    private final List<ModelGenre <String> > list;
    private ItemRecyclerClick listener;

    public AdapterGenre(Context context, List<ModelGenre<String>> list, ItemRecyclerClick listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;

    }

    public AdapterGenre(Context context, List<ModelGenre <String>> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GenreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ItemGenreBinding.inflate(LayoutInflater.from(context), parent, false);
        return new GenreHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder holder, int position) {
        binding.itemGenreName.setText(list.get(position).getGenre());

        if (listener != null){
            binding.itemGenreName.setOnClickListener(v-> listener.onClickListener(position));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    static class GenreHolder extends RecyclerView.ViewHolder {
        public GenreHolder(@NonNull ItemGenreBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
