package com.task.locatorptm.presentation.schedule.create;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.locatorptm.R;
import com.task.locatorptm.databinding.CreateScheduleItemBinding;

import java.util.ArrayList;

public class CreateScheduleAdapter extends RecyclerView.Adapter<CreateScheduleAdapter.ViewHolder>{

    public interface OnItemClickListener {
        void onItemClick(String day, CreateScheduleItemBinding binding);
    }

    private final OnItemClickListener listener;
    private final ArrayList<String> currentDays;

    public CreateScheduleAdapter(OnItemClickListener listener, ArrayList<String> currentDays) {
        this.listener = listener;
        this.currentDays = currentDays;
    }

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
        CreateScheduleItemBinding binding = CreateScheduleItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position), listener, currentDays);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CreateScheduleItemBinding binding;

        public ViewHolder(CreateScheduleItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(String item, final OnItemClickListener listener, final ArrayList<String> currentDays) {
            if (currentDays.isEmpty()) {
                binding.tvDay.setText(item);
                binding.getRoot().setOnClickListener(view -> listener.onItemClick(item, binding));
            } else {
                for (String currentDay: currentDays) {
                    if (item.equals(currentDay)) {
                        binding.tvDay.setText(currentDay);
                        binding.getRoot().setOnClickListener(view -> listener.onItemClick(item, binding));
                        binding.cvDay.setCardBackgroundColor(binding.getRoot()
                                .getContext()
                                .getResources()
                                .getColor(R.color.purple_700));
                        break;
                    } else {
                        binding.tvDay.setText(item);
                        binding.getRoot().setOnClickListener(view -> listener.onItemClick(item, binding));
                        binding.cvDay.setCardBackgroundColor(binding.getRoot()
                                .getContext()
                                .getResources()
                                .getColor(R.color.limeDark));
                    }
                }
            }
        }
    }
}
