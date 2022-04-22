package com.task.locatorptm.presentation.history;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.locatorptm.data.models.activity.ActivityData;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.LogItemBinding;

import java.util.Locale;

public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.ViewHolder> {

    private final DiffUtil.ItemCallback<ActivityData> callback = new DiffUtil.ItemCallback<ActivityData>() {
        @Override
        public boolean areItemsTheSame(@NonNull ActivityData oldItem, @NonNull ActivityData newItem) {
            return oldItem.getActivity().equals(newItem.getActivity());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull ActivityData oldItem, @NonNull ActivityData newItem) {
            return oldItem.equals(newItem);
        }
    };

    AsyncListDiffer<ActivityData> differ = new AsyncListDiffer<>(this, callback);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogItemBinding view = LogItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final LogItemBinding binding;

        public ViewHolder(LogItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(ActivityData data) {
            binding.tvLatLng.setText("Coordinate : ");
            binding.tvAddress.setText(data.getActivity());
            binding.tvLogDate.setText(AppUtil.convertDateTime(data.getCreatedAt()));
            String uri = String.format(Locale.getDefault(), "geo:%f,%f",
                    Double.parseDouble("7"), Double.parseDouble("1"));
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            binding.getRoot().setOnClickListener(item ->
                    binding.getRoot().getContext().startActivity(i));
        }
    }
}
