package com.komikatow.komiku.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.komikatow.komiku.databinding.ItemHomeKomikChinaKoreaBinding;
import com.komikatow.komiku.model.ModelBaseKomik;
import com.komikatow.komiku.ui.activityes.DetailActivity;
import com.komikatow.komiku.ui.activityes.backup.DetailBackupActivity;

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

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            boolean transitionStatus = sharedPreferences.getBoolean("animasiTransisi", false);
            String defValue = sharedPreferences.getString("listServer", "Default Server");

            if (!defValue.equals("Default Server")){
                Intent intent = new Intent(context, DetailBackupActivity.class);
                intent.putExtra("endpoint", list.get(position).getEndPoint());
                intent.putExtra("type", list.get(position).getType());

                if (transitionStatus){
                    context.startActivity(intent);
                    Animatoo.INSTANCE.animateZoom(context);

                }else {
                    context.startActivity(intent);
                }

            }else {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("endpoint", list.get(position).getEndPoint());
                intent.putExtra("type", list.get(position).getType());


                if (transitionStatus){
                    context.startActivity(intent);
                    Animatoo.INSTANCE.animateZoom(context);

                }else {
                    context.startActivity(intent);
                }

            }
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
