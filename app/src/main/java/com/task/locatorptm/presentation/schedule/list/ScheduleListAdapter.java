package com.task.locatorptm.presentation.schedule.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.locatorptm.databinding.ScheduleItemBinding;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>{

    private final DiffUtil.ItemCallback<String> callback = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    };

    public AsyncListDiffer<String> differ = new AsyncListDiffer<>(this, callback);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ScheduleItemBinding binding = ScheduleItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
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
        private final ScheduleItemBinding binding;

        public ViewHolder(ScheduleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(String item) {
            binding.tvDay.setText(item);
        }
    }
}
