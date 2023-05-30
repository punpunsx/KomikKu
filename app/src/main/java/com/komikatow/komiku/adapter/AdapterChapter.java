package com.komikatow.komiku.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.komikatow.komiku.R;
import com.komikatow.komiku.databinding.ItemChapterBinding;
import com.komikatow.komiku.room.enity.ModelChapter;
import com.komikatow.komiku.utils.ItemRecyclerClick;

import java.util.List;

public class AdapterChapter extends RecyclerView.Adapter<AdapterChapter.ChapterHolder> {
    private ItemChapterBinding binding;
    private final Context context;
    private final Activity activity;
    private final ItemRecyclerClick listener;
    private final List <ModelChapter > list;


    public AdapterChapter(Context context,Activity activity, List<ModelChapter> list, ItemRecyclerClick listener) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ItemChapterBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ChapterHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ChapterHolder holder, int position) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean modeCh = sharedPreferences.getBoolean("modeCh", true);
        final int uiConfigureMode = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;


        if (uiConfigureMode == Configuration.UI_MODE_NIGHT_YES){
            binding.itemChapterName.setText(list.get(position).getNemeCh().replace("\n", " "));
            binding.itemChapterName.setTextColor(activity.getResources().getColor(R.color.white));

        } else if (uiConfigureMode == Configuration.UI_MODE_NIGHT_NO) {
            binding.itemChapterName.setText(list.get(position).getNemeCh().replace("\n", " "));
            binding.itemChapterName.setTextColor(activity.getResources().getColor(R.color.black));
        }


        if (!modeCh){

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) binding.parent.getLayoutParams();
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;

        }

        binding.itemChapterName.setOnClickListener(v-> listener.onClickListener(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ChapterHolder extends RecyclerView.ViewHolder {
        public ChapterHolder(@NonNull ItemChapterBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
