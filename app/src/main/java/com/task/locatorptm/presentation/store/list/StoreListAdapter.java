package com.task.locatorptm.presentation.store.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.task.locatorptm.data.models.store.StoreData;
import com.task.locatorptm.databinding.StoreItemBinding;
import com.task.locatorptm.presentation.store.menu.StoreMenuActivity;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> {

    private final DiffUtil.ItemCallback<StoreData> callback = new DiffUtil.ItemCallback<StoreData>() {
        @Override
        public boolean areItemsTheSame(@NonNull StoreData oldItem, @NonNull StoreData newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull StoreData oldItem, @NonNull StoreData newItem) {
            return oldItem.equals(newItem);
        }
    };

    AsyncListDiffer<StoreData> differ = new AsyncListDiffer<>(this, callback);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StoreItemBinding binding = StoreItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

        private final StoreItemBinding binding;

        public ViewHolder(StoreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(StoreData data) {
            binding.tvStoreName.setText(data.getName());
            binding.getRoot().setOnClickListener(tap -> {
                Intent i = new Intent(binding.getRoot().getContext(), StoreMenuActivity.class);
                i.putExtra(StoreMenuActivity.STORE, data.getName());
                i.putExtra(StoreMenuActivity.STORE_ID, data.getId());
                binding.getRoot().getContext().startActivity(i);
            });
        }
    }
}
