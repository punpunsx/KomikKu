package com.komikatow.komiku.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.komikatow.komiku.R;
import com.komikatow.komiku.databinding.ItemHistoryBinding;
import com.komikatow.komiku.room.enity.ModelChapter;
import com.komikatow.komiku.utils.ItemRecyclerClick;

import java.util.Collections;
import java.util.List;

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.RiwayatHolder> {

    private ItemHistoryBinding binding;
    private final Context context;
    private final List<ModelChapter> list;
    private final ItemRecyclerClick listener;

    public AdapterRiwayat(Context context, List<ModelChapter> list, ItemRecyclerClick listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        Collections.reverse(list);
    }

    @NonNull
    @Override
    public RiwayatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ItemHistoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new RiwayatHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RiwayatHolder holder, int position) {

        Glide.with(context)
                .load(list.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.color.ErrorLoadImage)
                .placeholder(R.color.PlaceHolderImage)
                .override(Target.SIZE_ORIGINAL)
                .into(binding.itemThumbnail);

        binding.itemTitle.setText(list.get(position).getNameKomik());
        binding.itemChapter.setText(list.get(position).getNemeCh());
        binding.itemDate.setText("Waktu : "+list.get(position).getDate());

        holder.itemView.setOnClickListener(v -> listener.onClickListener(position));
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

    static class RiwayatHolder extends RecyclerView.ViewHolder {
        public RiwayatHolder(@NonNull ItemHistoryBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
