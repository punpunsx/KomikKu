package com.komikatow.komiku.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.komikatow.komiku.databinding.ItemHomeKomikChinaKoreaBinding;
import com.komikatow.komiku.model.ModelBaseKomik;
import com.komikatow.komiku.ui.activity.DetailActivity;

import java.util.List;

public class AdapterKomik extends RecyclerView.Adapter<AdapterKomik.KomikHolder> {
    private ItemHomeKomikChinaKoreaBinding binding;

    private final Context context;

    private final List <ModelBaseKomik <String> > list;



    public AdapterKomik(Context context, List < ModelBaseKomik <String> > list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public KomikHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ItemHomeKomikChinaKoreaBinding.inflate(LayoutInflater.from(context), parent, false);
        return new KomikHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull KomikHolder holder, int position) {

        Glide.with(context)
                .load(list.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.IMMEDIATE)
                .into(binding.itemThumbnail);

        binding.itemTitle.setText(list.get(position).getTitle());
        holder.itemView.setOnClickListener(V-> {

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("endpoint", list.get(position).getEndPoint());
            intent.putExtra("type", list.get(position).getType());
            context.startActivity(intent);

        });

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class KomikHolder extends RecyclerView.ViewHolder {
        public KomikHolder(@NonNull ItemHomeKomikChinaKoreaBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
