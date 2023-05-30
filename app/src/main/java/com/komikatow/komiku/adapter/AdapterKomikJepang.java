package com.komikatow.komiku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.github.islamkhsh.CardSliderAdapter;
import com.komikatow.komiku.R;
import com.komikatow.komiku.databinding.ItemHomeKomikJepangBinding;
import com.komikatow.komiku.model.ModelBaseKomik;
import com.komikatow.komiku.room.dbApp.AdvanceDbApp;
import com.komikatow.komiku.room.enity.AdvanceSizeEnity;
import com.komikatow.komiku.utils.ItemRecyclerClick;

import java.util.List;

public class AdapterKomikJepang extends CardSliderAdapter<AdapterKomikJepang.KomikJepangHolder> {
    private ItemHomeKomikJepangBinding binding;
    private final Context context;
    private final List <ModelBaseKomik <String> > list;
    private final ItemRecyclerClick listener;

    public AdapterKomikJepang(Context context, List <ModelBaseKomik <String> > list, ItemRecyclerClick listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;

    }

    @Override
    public void bindVH(@NonNull KomikJepangHolder komikJepangHolder, int i) {

        Glide.with(context)
                .load(list.get(i).getThumbnail())
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL)
                .placeholder(R.color.PlaceHolderImage)
                .error(R.color.ErrorLoadImage)
                .into(binding.itemThumbnail);

        binding.itemTitle.setText(list.get(i).getTitle());
        komikJepangHolder.itemView.setOnClickListener(v-> listener.onClickListener(i));

    }

    @NonNull
    @Override
    public KomikJepangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemHomeKomikJepangBinding.inflate(LayoutInflater.from(context), parent, false);


        new Thread(() -> {
            AdvanceDbApp database = AdvanceDbApp.getInstance(context);
            List<AdvanceSizeEnity> getALl = database.dao().getAll();

            if (!getALl.isEmpty()){
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.cardLayout.getLayoutParams();
                params.height = Integer.parseInt(getALl.get(0).getSlider());
            }

        }).start();

        return new KomikJepangHolder(binding);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class KomikJepangHolder extends RecyclerView.ViewHolder {
        public KomikJepangHolder(@NonNull ItemHomeKomikJepangBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
