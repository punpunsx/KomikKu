package com.komikatow.komiku.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.komikatow.komiku.R;
import com.komikatow.komiku.databinding.ItemBacaBinding;
import com.komikatow.komiku.model.ModelChapterDetail;

import java.util.List;

public class AdapterChapterDetail extends RecyclerView.Adapter<AdapterChapterDetail.ChapterDetailHolder> {

    private ItemBacaBinding binding;
    private final List <ModelChapterDetail <String> > list;
    private final Context context;

    public AdapterChapterDetail( List<ModelChapterDetail<String>> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ChapterDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ItemBacaBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ChapterDetailHolder(binding);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ChapterDetailHolder holder, int position) {

        Glide.with(context)
                .load(list.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .priority(Priority.IMMEDIATE)
                .timeout(15000)
                .placeholder(R.color.PlaceHolderImage)
                .error(R.color.ErrorLoadImage)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        binding.itemProgress.setVisibility(View.GONE);
                        Toast.makeText(context, "Gambar ini gagal untuk ditampilkan silahkan muat ulang halaman ini.", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.itemProgress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(binding.itemImages);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ChapterDetailHolder extends RecyclerView.ViewHolder {
        public ChapterDetailHolder(@NonNull ItemBacaBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
